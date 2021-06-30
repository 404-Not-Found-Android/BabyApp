package com.example.ui.fragment.page

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.R
import com.example.view.ninegridlayout.NineGridTestLayout
import com.example.view.ninegridlayout.OnItemPictureClickListener

/**
 * Author : ljt
 * Description :
 * CreateTime  : 2021/6/23
 */
class PageRecyclerAdapter(val context: Context) :
    RecyclerView.Adapter<PageRecyclerAdapter.ViewHolder>() {
    private var listener: OnItemPictureClickListener? = null
    private val data: MutableList<Page> = mutableListOf()
    fun submit(data: MutableList<Page>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun setOnItemPictureClickListener(onItemPictureClickListener: OnItemPictureClickListener){
        this.listener = onItemPictureClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_wechat_moment, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            Glide.with(context).load(data[position].icon).centerCrop().into(holder.userIcon)
            holder.userName.text = data[position].userName
            holder.nineGridLayout.setListener(listener)
            holder.nineGridLayout.setItemPosition(position)
            holder.nineGridLayout.setSpacing(15f)
            holder.nineGridLayout.setUrlList(data[position].images)
        } catch (e: Exception) {
            Log.d("TAG", e.message)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userIcon: ImageView = itemView.findViewById(R.id.user_icon)
        val userName: TextView = itemView.findViewById(R.id.user_name)
        val nineGridLayout: NineGridTestLayout = itemView.findViewById(R.id.nineTestlayout)
    }
}