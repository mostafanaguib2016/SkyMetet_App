package com.skymeter.skymeterapp.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skymeter.skymeterapp.data.pojo.model.PicturesTable
import com.skymeter.skymeterapp.data.room.RoomManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val roomManager: RoomManager
): ViewModel()
{

    private val TAG = HomeViewModel::class.java.simpleName
    private val _pictureMutableLiveData = MutableLiveData<List<PicturesTable>>()
    val pictureMutableLiveData = _pictureMutableLiveData

    fun insertPicture(picturesModel: PicturesTable){

        CoroutineScope(Dispatchers.IO).launch {
            kotlin.runCatching {

                roomManager.getPicturesDao().insertPictures(picturesModel)

            }.onFailure {

            }.onSuccess {
                Log.e(TAG, "insertPicture: Success" )
            }
        }

    }

    fun getPictures(){

        CoroutineScope(Dispatchers.IO).launch {

            kotlin.runCatching {
                roomManager.getPicturesDao().getPictures()
            }.onFailure {

            }.onSuccess {

                _pictureMutableLiveData.postValue(it)
                Log.e(TAG, "getPictures: " )
            }

        }

    }

}