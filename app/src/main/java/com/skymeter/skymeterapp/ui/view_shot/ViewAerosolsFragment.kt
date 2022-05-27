package com.skymeter.skymeterapp.ui.view_shot

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.skymeter.skymeterapp.R
import com.skymeter.skymeterapp.databinding.FragmentViewAerosolsBinding


class ViewAerosolsFragment : Fragment() {

    private lateinit var binding: FragmentViewAerosolsBinding
    private val tAG = ViewAerosolsFragment::class.java.simpleName
    private lateinit var mContext: Context
    private lateinit var navController: NavController

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

    }

    companion object {

    }
}