package com.skymeter.skymeterapp.utils.dialogs

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.skymeter.skymeterapp.R
import com.skymeter.skymeterapp.data.pojo.model.PicturesTable
import com.skymeter.skymeterapp.databinding.DialogPicturesBinding
import com.skymeter.skymeterapp.ui.home.HomeViewModel
import com.skymeter.skymeterapp.utils.DialogsListener
import com.skymeter.skymeterapp.utils.getEncoded64ImageStringFromBitmap
import com.theartofdev.edmodo.cropper.CropImage

class PicturesPickerDialog(val dialogsListener: DialogsListener,
                           val homeViewModel: HomeViewModel,
                           val bitmap: Bitmap, private val mContext: Context)
    :DialogFragment()
{

    lateinit var binding: DialogPicturesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_pictures, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp()

    }

    private fun setUp() {

        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        val wlp = dialog!!.window!!.attributes
//        wlp.gravity = Gravity.BOTTOM
//        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
//        dialog!!.window!!.attributes = wlp

//        CropImage.activity()
//            .start(mContext, this)

        binding.imageView.setImageBitmap(bitmap)
        binding.Confirm.setOnClickListener {
            editPhoto()
        }


    }

    private fun editPhoto(){
        val cropImage = binding.imageView.croppedImage

        val encodedImage = getEncoded64ImageStringFromBitmap(cropImage)

        homeViewModel.insertPicture(
            PicturesTable(
                0,"", encodedImage!!
            )
        )

        dismiss()

    }

}