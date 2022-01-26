package com.skymeter.skymeterapp.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson

object AssetsConverter {

    @TypeConverter
    fun fromList(list: List<String>?): String? {
        if (list == null) {
            return null
        }
        val gson = Gson()
        return gson.toJson(list, List::class.java)
    }

    @TypeConverter
    fun toList(josnString: String?): List<String>? {
        if (josnString == null) {
            return null
        }
        val gson = Gson()
        return gson.fromJson<List<String>>(josnString, List::class.java)
    }

}