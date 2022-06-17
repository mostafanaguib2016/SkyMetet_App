package com.skymeter.skymeterapp.utils

import com.skymeter.skymeterapp.utils.dialogs.CaptureRequiredPhotoDialog
import com.skymeter.skymeterapp.utils.dialogs.PicturesPickerDialog


interface DialogsListener {

    fun onDismiss(){}

    fun onDismiss(dialog: CaptureRequiredPhotoDialog){}
    fun onCameraClick(dialog: PicturesPickerDialog){}

    fun onGalleryClick(dialog: PicturesPickerDialog){}

}