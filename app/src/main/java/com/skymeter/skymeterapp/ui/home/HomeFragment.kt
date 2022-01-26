package com.skymeter.skymeterapp.ui.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.skymeter.skymeterapp.R
import com.skymeter.skymeterapp.databinding.FragmentHomeBinding

import android.content.pm.PackageManager
import android.provider.MediaStore
import androidx.core.content.ContextCompat.checkSelfPermission
import android.widget.Toast

import android.graphics.Bitmap

import android.app.Activity
import com.skymeter.skymeterapp.data.pojo.model.PicturesTable
import org.koin.android.ext.android.inject
import android.graphics.Bitmap.CompressFormat
import android.util.Base64
import java.io.ByteArrayOutputStream
import androidx.test.core.app.ApplicationProvider.getApplicationContext

import android.content.ContextWrapper
import android.graphics.BitmapFactory
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.skymeter.skymeterapp.utils.getEncoded64ImageStringFromBitmap
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var navController: NavController
    lateinit var mContext: Context
    private val homeViewModel: HomeViewModel by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(binding.root)
        mContext = binding.root.context

        setUi()

    }

    private fun setUi(){

        binding.explorePicturesLayout.root.setOnClickListener {
            navController.navigate(R.id.shotsExplorerFragment)
        }
        binding.explorePicturesLayout.uploadPhotoIv.setImageDrawable(mContext.getDrawable(R.drawable.ic_gallery))
        binding.explorePicturesLayout.uploadPhotoTv.text = getString(R.string.explore_shots)

        binding.exitApp.uploadPhotoIv.setImageDrawable(mContext.getDrawable(R.drawable.ic_baseline_exit_to_app_24))
        binding.exitApp.uploadPhotoTv.text = getString(R.string.exit_app)


        binding.uploadPictureLayout.root.setOnClickListener {

            if (checkSelfPermission(mContext,Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
            } else {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            }

        }

        binding.exitApp.root.setOnClickListener {
            requireActivity().finish()
        }

    }





    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode === MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(mContext, "camera permission granted", Toast.LENGTH_LONG).show()
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            } else {
                Toast.makeText(mContext, "camera permission denied", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === CAMERA_REQUEST && resultCode === Activity.RESULT_OK) {
            binding.imageView.setImageBitmap(data?.extras?.get("data") as Bitmap?)

            val encodedImage = getEncoded64ImageStringFromBitmap(data?.extras?.get("data") as Bitmap)

            homeViewModel.insertPicture(
                PicturesTable(
                    0,"", encodedImage!!
                )
            )

        }

    }





    companion object{

        const val MY_CAMERA_PERMISSION_CODE = 1111
        const val CAMERA_REQUEST = 2222
        private const val TAG = "HomeFragment"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

}