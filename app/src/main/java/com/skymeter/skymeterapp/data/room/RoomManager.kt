package com.skymeter.skymeterapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.skymeter.skymeterapp.data.pojo.model.PicturesTable

@Database(
    version = 11,
    entities = [PicturesTable::class],
    exportSchema = false
)

abstract class RoomManager : RoomDatabase() {

    abstract fun getPicturesDao(): PicturesDAO

}