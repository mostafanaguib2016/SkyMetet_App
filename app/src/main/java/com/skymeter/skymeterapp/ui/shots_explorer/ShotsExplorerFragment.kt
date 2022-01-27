package com.skymeter.skymeterapp.ui.shots_explorer

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.skymeter.skymeterapp.R
import com.skymeter.skymeterapp.data.pojo.model.PicturesTable
import com.skymeter.skymeterapp.databinding.FragmentShotsExplorerBinding
import com.skymeter.skymeterapp.ui.home.HomeViewModel
import com.skymeter.skymeterapp.utils.RecyclerViewOnItemClickListener
import org.koin.android.ext.android.inject

class ShotsExplorerFragment : Fragment()
    ,RecyclerViewOnItemClickListener {

    lateinit var binding: FragmentShotsExplorerBinding
    lateinit var navController: NavController
    lateinit var mContext: Context
    lateinit var adapter: PicturesAdapter
    private val homeViewModel: HomeViewModel by inject()
    private val TAG = ShotsExplorerFragment::class.java.simpleName


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_shots_explorer, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(binding.root)
        mContext = binding.root.context
        adapter = PicturesAdapter(mContext,navController,this)

        setUi()

    }

    private fun setUi(){

        binding.picturesRv.layoutManager =
            GridLayoutManager(mContext,2, VERTICAL,false)

        binding.picturesRv.adapter = adapter

        homeViewModel.getPictures()
        homeViewModel.pictureMutableLiveData.observe(viewLifecycleOwner){

            adapter.submitData(it as ArrayList<PicturesTable>)

        }

        homeViewModel.deletePictureMutableLiveData.observe(viewLifecycleOwner){

            Log.e(TAG, "setUi:Delete $it" )

        }


    }

    override fun onImageDelete(image: String, position: Int, id: Int) {

        homeViewModel.deletePicture(id)
        adapter.deleteImage(position)

    }

}