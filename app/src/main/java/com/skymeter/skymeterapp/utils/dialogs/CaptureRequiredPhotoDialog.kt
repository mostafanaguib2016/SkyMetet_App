package com.skymeter.skymeterapp.utils.dialogs

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.skymeter.skymeterapp.databinding.DialogCaptureRequiredPhotoBinding
import com.skymeter.skymeterapp.utils.DialogsListener


class CaptureRequiredPhotoDialog(private val listener: DialogsListener,
                                 private val txt: String): DialogFragment()
{

    private lateinit var binding: DialogCaptureRequiredPhotoBinding
    private lateinit var mContext : Context
    private val tAG = "CardAnimationDialog"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCaptureRequiredPhotoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mContext = binding.root.context
        setUp()
        binding.startBtn.setOnClickListener {
            listener.onDismiss(this)
        }
    }

    private fun setUp() {

        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setCancelable(false)
//        val wlp = dialog!!.window!!.attributes
//        wlp.gravity = Gravity.BOTTOM
//        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
//        dialog!!.window!!.attributes = wlp

    }


}