package com.skymeter.skymeterapp.ui.view_shot

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.skymeter.skymeterapp.R
import com.skymeter.skymeterapp.databinding.FragmentViewAerosolsBinding
import com.skymeter.skymeterapp.ui.home.HomeViewModel
import com.skymeter.skymeterapp.utils.getAirPollution
import com.skymeter.skymeterapp.utils.getBase64
import com.skymeter.skymeterapp.utils.getBitMap
import org.koin.android.ext.android.inject


class ViewAerosolsFragment : Fragment() {

    private lateinit var binding: FragmentViewAerosolsBinding
    private val tAG = ViewAerosolsFragment::class.java.simpleName
    private lateinit var mContext: Context
    private lateinit var navController: NavController
    private val homeViewModel: HomeViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewAerosolsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mContext = binding.root.context
        navController = Navigation.findNavController(binding.root)


        setUi()
    }

    private fun setUi(){

        val imagePath = requireArguments().getString("image","")
        val date = requireArguments().getString("date","")
        val time = requireArguments().getString("time","")
        val id = requireArguments().getString("id","")

        binding.toolbar.dateTv.text = date
        binding.toolbar.timeTv.text = time

        bindPollution(imagePath,id)

    }

    private fun bindPollution(imagePath: String,id: String){

        homeViewModel.getPicture(id)

        val imageUri = Uri.parse(imagePath)

        Log.e(tAG, "setUi: $imageUri " )

//        val imageBitmap = getBitMap(imagePath)
//        binding.pollutionTv.text = "$pollutionTxt : $pollutionPercentage %"

        homeViewModel.pictureMutableLiveData.observe(viewLifecycleOwner){

            val imageString = it.picturePath
            val imageBitmap = getBitMap(imageString)



            val pollutionPercentage = getAirPollution(imageBitmap)

            val pollutionTxt = "Pollution percentage"
            binding.pollutionTv.text = "$pollutionTxt : $pollutionPercentage %"


            when(getAirPollution(imageBitmap)){


                in 0F..30F ->{
                    binding.pollutionTxtTv.text = getString(R.string.low_pollution)

                    binding.viewAerosols.setBackgroundColor(resources.getColor(R.color.green))
                    binding.aerosolsTv.text = getString(R.string.low_aerosols)
                    binding.viewHazy.setBackgroundColor(resources.getColor(R.color.deep_blue))
                    binding.hazyTv.text = getString(R.string.unusually_clear)
                }

                in 31F..60F ->{
                    binding.pollutionTxtTv.text = getString(R.string.medium_pollution)

                    binding.viewAerosols.setBackgroundColor(resources.getColor(R.color.yellow))
                    binding.aerosolsTv.text = getString(R.string.medium_aerosols)
                    binding.viewHazy.setBackgroundColor(resources.getColor(R.color.light_blue))
                    binding.hazyTv.text = getString(R.string.somewhat_hazy)

                }
                in 61F..100F ->{
                    binding.pollutionTxtTv.text = getString(R.string.high_pollution)

                    binding.viewAerosols.setBackgroundColor(resources.getColor(R.color.red))
                    binding.aerosolsTv.text = getString(R.string.high_aerosols)
                    binding.viewHazy.setBackgroundColor(resources.getColor(R.color.milky))
                    binding.hazyTv.text = getString(R.string.Extremely_hazy)

                }
            }


        }



    }

    companion object {

    }
}