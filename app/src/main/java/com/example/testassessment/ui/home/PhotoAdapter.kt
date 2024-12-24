package com.example.testassessment.ui.home


import com.example.testassessment.R
import com.example.testassessment.common.BaseListItemCallback
import com.example.testassessment.common.MyBaseAdapter


class PhotoAdapter(
    cb: BaseListItemCallback<CombinedPhotoData>
) : MyBaseAdapter<CombinedPhotoData>(cb) {

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.item_view
    }
}
