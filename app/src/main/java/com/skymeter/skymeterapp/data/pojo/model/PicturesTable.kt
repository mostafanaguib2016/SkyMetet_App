package com.skymeter.skymeterapp.data.pojo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PicturesTable")
data class PicturesTable(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "pictureName") val pictureName: String,
    @ColumnInfo(name = "picturePath") val picturePath: String
)
