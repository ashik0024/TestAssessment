package com.example.testassessment.ui.home

import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.example.testassessment.R
import com.example.testassessment.common.BaseListItemCallback
import com.example.testassessment.common.MyBaseAdapter
import com.example.testassessment.common.MyViewHolder
import com.example.testassessment.roomdb.PhotosEntity
import com.example.testassessment.utils.BindingAdapters


class PhotoAdapter(
    cb: BaseListItemCallback<PhotosEntity>
) : MyBaseAdapter<PhotosEntity>(cb) {

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.item_view
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val obj = getItem(position)
        obj.let {

            val tittle = holder.itemView.findViewById<TextView>(R.id.tittle_photo_tv)
            val albumName = holder.itemView.findViewById<TextView>(R.id.album_name_tv)
            val userName = holder.itemView.findViewById<TextView>(R.id.username_tv)
            val img = holder.itemView.findViewById<ImageView>(R.id.thumbnailImage)


            tittle.text=obj.title
            img.load(obj.thumbnailUrl) {
                placeholder(R.drawable.placeholder)
                error(R.drawable.errorimg)
            }

        }

    }
}
