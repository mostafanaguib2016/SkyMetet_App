package com.skymeter.skymeterapp.utils.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.skymeter.skymeterapp.R
import com.skymeter.skymeterapp.databinding.DialogPicturesBinding
import com.skymeter.skymeterapp.utils.DialogsListener

class PicturesPickerDialog(val dialogsListener: DialogsListener):DialogFragment()
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

        binding.cameraBtn.setOnClickListener {
            dialogsListener.onCameraClick(this)
        }

        binding.galleryBtn.setOnClickListener {

            dialogsListener.onGalleryClick(this)

        }

    }

}