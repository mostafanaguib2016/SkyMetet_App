package com.skymeter.skymeterapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skymeter.skymeterapp.data.pojo.model.PicturesTable

@Dao
interface PicturesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPictures(data: PicturesTable)

    @Query("SELECT * FROM PicturesTable")
    fun getPictures(): List<PicturesTable>

    @Query("DELETE FROM PicturesTable WHERE id = :id")
    fun deletePictureById(id: Int)

}