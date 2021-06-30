package com.example.ui.fragment.page

import android.Manifest
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bm.library.PhotoView
import com.example.databinding.FragmentPageBinding
import com.example.net.ApiRetrofit
import com.example.request.UploadRequest
import com.example.ui.activity.ImagePreviewActivity
import com.example.ui.activity.P
import com.example.util.imageloader.GlideImageLoader
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.bean.ImageItem
import com.lzy.imagepicker.ui.ImageGridActivity
import com.lzy.imagepicker.view.CropImageView
import com.mylhyl.circledialog.BaseCircleDialog
import com.mylhyl.circledialog.CircleDialog
import com.mylhyl.circledialog.params.ProgressParams
import com.permissionx.guolindev.PermissionX
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*


/**
 * Author : ljt
 * Description :
 * CreateTime  : 2021/5/18
 */
class PageFragment : Fragment() {

    private lateinit var dataBinding: FragmentPageBinding
    private lateinit var dialogFragment: BaseCircleDialog
    private val images = ArrayList<ImageItem>()
    private val data = mutableListOf<Page>()

    private lateinit var adapter: PageRecyclerAdapter
    private lateinit var job: Job

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dataBinding = FragmentPageBinding.inflate(layoutInflater)
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        adapter = PageRecyclerAdapter(requireContext())
        dataBinding.recyclerView.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        dataBinding.recyclerView.layoutManager = linearLayoutManager

        GlobalScope.launch {
            try {
                val loadPage = ApiRetrofit.loadPage()
                loadPage.data.photos.forEach {
                    val images = ArrayList<String>()
                    images.addAll(it.images)
                    data.add(Page(it.userName, it.icon, images))
                }
                withContext(Dispatchers.Main) {
                    adapter.submit(data)
                }
            } catch (e: Exception) {
                Log.d("TAG", e.message)
            }
            dataBinding.ivAdd.setOnClickListener {
                PermissionX.init(this@PageFragment).permissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).request { allGranted, _, _ ->
                    if (allGranted) {
                        initImagePicker()
                    }
                }
            }
            dataBinding.toolBar.setNavigationOnClickListener {
                findNavController().apply {
                    popBackStack(graph.startDestination, false)
                }
            }
            adapter.setOnItemPictureClickListener { itemPostion, position, url, urlList, imageView ->

                data[itemPostion]
                val intent = Intent(requireContext(), ImagePreviewActivity::class.java)
                intent.putStringArrayListExtra("imageList", urlList as ArrayList<String?>)
                intent.putExtra(P.START_ITEM_POSITION, itemPostion)
                intent.putExtra(P.START_IAMGE_POSITION, position)
                val compat =
                    ActivityOptions.makeSceneTransitionAnimation(requireActivity(), imageView, imageView.transitionName)
                startActivity(intent, compat.toBundle())
            }
        }
    }

    private fun initImagePicker() {
        val imagePicker = ImagePicker.getInstance()
        imagePicker.imageLoader = GlideImageLoader()
        imagePicker.isShowCamera = true;  //显示拍照按钮
        //        imagePicker.isCrop = true;        //允许裁剪（单选才有效）
        imagePicker.isSaveRectangle = true; //是否按矩形区域保存
        imagePicker.selectLimit = 9;    //选中数量限制
        imagePicker.style = CropImageView.Style.RECTANGLE;  //裁剪框的形状
        imagePicker.focusWidth = 800;   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.focusHeight = 800;  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.outPutX = 1000; //保存文件的宽度。单位像素
        imagePicker.outPutY = 1000; //保存文件的高度。单位像素
        val intent = Intent(requireContext(), ImageGridActivity::class.java)
        intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, images)
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                images.addAll(data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) as ArrayList<ImageItem>)
                val sbFileName = StringBuffer()
                job = GlobalScope.launch {
                    try {
                        withContext(Dispatchers.Main) {
                            dialogFragment =
                                CircleDialog.Builder().setCanceledOnTouchOutside(false).setCancelable(true).setWidth(0.6f).setProgressText("上传中...").configProgress {
                                    it.indeterminateColor = Color.parseColor("#E9AD44")
                                }.setProgressStyle(ProgressParams.STYLE_SPINNER).setOnCancelListener {
                                    job.cancel()
                                }.show(fragmentManager)
                        }
                        for (i in 0 until images.size) {
                            sbFileName.append(images[i].name)
                            if (i < images.size - 1) {
                                sbFileName.append(",")
                            }
                            val file = File(images[i].path)
                            val requestBody =
                                RequestBody.create("image/jpg".toMediaTypeOrNull(), file)
                            val body =
                                MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", file.name, requestBody).addFormDataPart("fileName", file.name).build()

                            withContext(Dispatchers.IO) {
                                val response = ApiRetrofit.uploadFile(body)
                                if (response.status == 200) {
                                    Log.d("TAG", "file upload success")
                                }
                            }

                        }
                        val fileName = sbFileName.toString()
                        withContext(Dispatchers.IO) {
                            val request = UploadRequest("admin", fileName, "")
                            val response = ApiRetrofit.upload(request)
                            if (response.status == 200) {
                                Log.d("TAG", "upload json success")
                            }
                        }

                    } catch (e: Exception) {
                        Log.d("TAG", e.message)
                    }

                    withContext(Dispatchers.Main) {
                        dialogFragment.dismiss()
                    }

                }
            }
        }

    }


}