package com.skymeter.skymeterapp.ui.view_shot

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.skymeter.skymeterapp.R
import com.skymeter.skymeterapp.databinding.FragmentViewShotBinding
import com.skymeter.skymeterapp.utils.getBitMap
import uk.co.senab.photoview.PhotoViewAttacher
import androidx.core.app.ActivityCompat.startActivityForResult

import android.content.Intent
import android.net.Uri
import com.skymeter.skymeterapp.ui.home.HomeViewModel
import com.skymeter.skymeterapp.utils.DialogsListener
import com.skymeter.skymeterapp.utils.dialogs.PicturesPickerDialog
import com.skymeter.skymeterapp.utils.getAirPollution
import com.theartofdev.edmodo.cropper.CropImage
import org.koin.android.ext.android.inject
import java.io.File


class ViewShotFragment : Fragment(),DialogsListener {

    lateinit var binding: FragmentViewShotBinding
    lateinit var navController: NavController
    lateinit var mContext: Context
    private val homeViewModel: HomeViewModel by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater,R.layout.fragment_view_shot, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(binding.root)
        mContext = binding.root.context

        setUi()

    }

    private fun setUi(){

        val imagePath = requireArguments().getString("image","")
        val date = requireArguments().getString("date","")
        val time = requireArguments().getString("time","")
        var pollutionPercentage = ""
        if (imagePath.isNotEmpty()){

            val imageBitmap = getBitMap(imagePath)
            binding.imageView.setImageBitmap(imageBitmap)

            val pollutionTxt = "Pollution percentage"

            pollutionPercentage = "${(getAirPollution(imageBitmap))}"
            binding.pollutionTv.text = "$pollutionTxt : $pollutionPercentage %"

            when((getAirPollution(imageBitmap))){

                in 0F..30F ->{
                    binding.pollutionTxtTv.text = getString(R.string.low_pollution)
                }

                in 31F..60F ->{
                    binding.pollutionTxtTv.text = getString(R.string.medium_pollution)
                }
                in 61F..100F ->{
                    binding.pollutionTxtTv.text = getString(R.string.high_pollution)
                }
            }

            val pAttacher = PhotoViewAttacher(binding.imageView)
            pAttacher.update()

        }

        binding.toolbar.dateTv.text = date
        binding.toolbar.timeTv.text = time

        binding.viewAerosolsTv.setOnClickListener {

            val bundle = Bundle()

            bundle.putString("pollution",pollutionPercentage)
            bundle.putString("image",imagePath)
            bundle.putString("date",date)
            bundle.putString("time",time)

            navController.navigate(R.id.viewAerosolsFragment,bundle)

        }


        binding.ivEdit.setOnClickListener {
            val pickerDialog =
                PicturesPickerDialog(this,homeViewModel, getBitMap(imagePath),mContext)
            pickerDialog.show(childFragmentManager,"")
        }

        binding.ivClose.setOnClickListener {
            navController.navigate(R.id.action_close)
        }



    }



}