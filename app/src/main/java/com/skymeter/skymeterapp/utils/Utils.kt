package com.skymeter.skymeterapp.utils

import android.R.attr
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Base64
import android.util.Log
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator
import java.io.ByteArrayOutputStream
import android.R.attr.bitmap
import kotlin.math.abs


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

fun getNormalGrayFromImage(bitmap: Bitmap): Float{

    val height: Int = bitmap.height
    val width: Int = bitmap.width

    val size = width * height
    val pixels = IntArray(size)

    val bitmap2: Bitmap = bitmap.copy(Bitmap.Config.ARGB_4444, false)

    bitmap2.getPixels(pixels, 0, width, 0, 0, width, height);
    val colorMap: MutableList<HashMap<Int, Int>> = ArrayList()
    colorMap.add(HashMap())
    colorMap.add(HashMap())
    colorMap.add(HashMap())

    var color = 0
    var r = 0
    var g = 0
    var b = 0
    var rC: Int?
    var gC: Int?
    var bC: Int?
    for (element in pixels) {
        color = element
        r = Color.red(color)
        g = Color.green(color)
        b = Color.blue(color)
        rC = colorMap[0][r]
        if (rC == null) rC = 0
        colorMap[0][r] = ++rC
        gC = colorMap[1][g]
        if (gC == null) gC = 0
        colorMap[1][g] = ++gC
        bC = colorMap[2][b]
        if (bC == null) bC = 0
        colorMap[2][b] = ++bC
    }

    val rgb = IntArray(3)
    for (i in 0..2) {
        var max = 0
        var `val` = 0
        for ((key, value) in colorMap[i]) {
            if (value > max) {
                max = value
                `val` = key
            }
        }
        rgb[i] = `val`
    }

//    normalGray = (0.299 * r) + (0.587 * g) + (0.114 * b)
    // gray = (r+g+b) / 3

    return (((0.299 * r) + (0.587 * g) + (0.114 * b)) - ((r+g+b) / 3.0F)).toFloat()

}

fun getAirPollution(bitmap: Bitmap): Float{

     // airPollution = abs(normalGray - gray)
    return abs(getNormalGrayFromImage(bitmap))

}