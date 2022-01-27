package com.skymeter.skymeterapp.utils

import android.net.Uri

interface RecyclerViewOnItemClickListener
{
    fun onImageDelete(image: String, position: Int, id: Int){}
}