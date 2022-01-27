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

    private val _deletePictureMutableLiveData = MutableLiveData<Unit>()
    val deletePictureMutableLiveData = _deletePictureMutableLiveData

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

    fun deletePicture(id: Int){

        CoroutineScope(Dispatchers.IO).launch {

            kotlin.runCatching {
                roomManager.getPicturesDao().deletePictureById(id)
            }.onFailure {

            }.onSuccess {

                _deletePictureMutableLiveData.postValue(it)
                Log.e(TAG, "getPictures: $it" )
            }

        }

    }

}