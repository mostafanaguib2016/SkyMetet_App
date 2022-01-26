package com.skymeter.skymeterapp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import java.io.ByteArrayOutputStream

private const val TAG = "Utils"

fun getBitMap(picturePath: String): Bitmap {

    val decodedString: ByteArray = Base64.decode(picturePath, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

}


fun getEncoded64ImageStringFromBitmap(bitmap: Bitmap): String? {
    val stream = ByteArrayOutputStream()
    
//    val scaledBitmap = Bitmap.createScaledBitmap(bitmap,400,400,false)
    
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

    val byteFormat: ByteArray = stream.toByteArray()
    Log.e(TAG, "getEncoded64ImageStringFromBitmap: ${byteFormat.size / (1024 * 1024)}" )
    // get the base 64 string
    return Base64.encodeToString(byteFormat, Base64.NO_WRAP)
}