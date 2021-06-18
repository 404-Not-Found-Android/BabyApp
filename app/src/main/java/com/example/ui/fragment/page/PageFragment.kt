package com.example.ui.fragment.page

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import com.example.R
import com.example.databinding.FragmentPageBinding
import com.example.net.ApiRetrofit
import com.example.util.imageloader.GlideImageLoader
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.bean.ImageItem
import com.lzy.imagepicker.ui.ImageGridActivity
import com.lzy.imagepicker.view.CropImageView
import com.permissionx.guolindev.PermissionX
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.ArrayList


/**
 * Author : ljt
 * Description :
 * CreateTime  : 2021/5/18
 */
class PageFragment : Fragment() {

    private lateinit var dataBinding: FragmentPageBinding
    private val images = ArrayList<ImageItem>()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dataBinding = FragmentPageBinding.inflate(layoutInflater)
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dataBinding.ivAdd.setOnClickListener {
            PermissionX.init(this).permissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).request { allGranted, _, _ ->
                if (allGranted) {
                    initImagePicker()
                }
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
                val imageItem = images[0]
                GlobalScope.launch {
                    val file = File(imageItem.path)
                    val requestBody = RequestBody.create("image/jpg".toMediaTypeOrNull(), file)
                    val body = MultipartBody.Builder().setType(MultipartBody.FORM)
                            .addFormDataPart("file", file.name, requestBody)
                            .addFormDataPart("fileName", file.name)
                            .addFormDataPart("createTime", System.currentTimeMillis().toString()).build()
                    try {
                        val response = ApiRetrofit.uploadFile(body)
                        if (response.status == 200) {
                            Log.d("TAG", "success:$response")
                        }
                        Log.d("TAG", "$response")
                    } catch (e: Exception) {
                        Log.d("TAG", "${e.message}")
                    }

                }
            }
        }

    }


}