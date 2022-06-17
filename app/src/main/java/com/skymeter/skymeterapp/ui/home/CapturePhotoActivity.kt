package com.skymeter.skymeterapp.ui.home

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.skymeter.skymeterapp.databinding.ActivityCapturePhotoBinding
import com.skymeter.skymeterapp.utils.DialogsListener
import com.skymeter.skymeterapp.utils.dialogs.CaptureRequiredPhotoDialog
import com.skymeter.skymeterapp.utils.getBase64
import com.skymeter.skymeterapp.utils.getBitMap
import java.util.*


class CapturePhotoActivity : AppCompatActivity(),DialogsListener {

    private lateinit var binding: ActivityCapturePhotoBinding
    private val tAG = CapturePhotoActivity::class.java.simpleName
    private var imageCapture: ImageCapture? = null

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
        { permissions ->

            try {
                if (permissions[Manifest.permission.CAMERA] == true) {
                    startCamera()
                } else {
                    ActivityCompat.requestPermissions(
                        (this@CapturePhotoActivity)
                                as Activity, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
                    )
                }
            } catch (e: Exception) {
//                FirebaseCrashlytics.getInstance().recordException(e)
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCapturePhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkPermissions()

        setUi()

    }

    private fun setUi(){

        val dialog = CaptureRequiredPhotoDialog(this,"")
        dialog.show(supportFragmentManager,"")

        binding.captureSideButton.setOnClickListener {
            takePhoto()
        }

    }

    private fun checkPermissions() {
        if (
            ContextCompat.checkSelfPermission(
                this@CapturePhotoActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE,
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(tAG, "Request Permissions")
            requestMultiplePermissions.launch(
                REQUIRED_PERMISSIONS
            )
        } else {
            Log.d(tAG, "Permission Already Granted")
            startCamera()
        }
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ActivityCompat.checkSelfPermission(
            this, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    private fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .setTargetResolution(Size(2048,2048))
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            imageCapture = ImageCapture.Builder()
                .setTargetResolution(Size(2048,2048))
//                .setTargetRotation(Surface.ROTATION_0)
                .build()

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview,imageCapture)

            } catch(exc: Exception) {
                Log.e(tAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this@CapturePhotoActivity))
    }

    private fun takePhoto() {

        Log.e(tAG, "takePhoto: " )
        val imageCaptured = imageCapture ?: return

        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(this.contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues)
            .build()

        imageCaptured.takePicture(outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved
                            (outputFileResults: ImageCapture.OutputFileResults)
                {
                    val imageString = getBase64(outputFileResults.savedUri!!
                        ,this@CapturePhotoActivity)
                    viewImage(imageString!!,outputFileResults.savedUri!!)
                }
                override fun onError(exception: ImageCaptureException) {
                    Log.e(tAG, "takePicture:onError ${exception.imageCaptureError} " )
                }
            }

        )

    }

    private fun viewImage(imageString: String,bitmap: Uri){

        binding.viewFinder.visibility = GONE
        binding.captureSideButton.visibility = GONE
        binding.loader.visibility = GONE
        binding.frontSideImageGroup.visibility = VISIBLE

        binding.capturedImageView.setImageBitmap(
            getBitMap(imageString,bitmap,this@CapturePhotoActivity)
        )

        binding.recaptureButton.setOnClickListener {
            binding.capturedImageView.setImageURI(null)
            binding.viewFinder.visibility = VISIBLE
            binding.captureSideButton.visibility = VISIBLE
            binding.frontSideImageGroup.visibility = GONE
        }

        binding.proceedButton.setOnClickListener {
            val data = Intent()
            data.putExtra("imagePath", bitmap)
//            data.putExtra("imageString", imageString)
            setResult(RESULT_OK, data)
            finish()
        }

    }

    override fun onDismiss(dialog: CaptureRequiredPhotoDialog) {
        dialog.dismiss()
    }

}