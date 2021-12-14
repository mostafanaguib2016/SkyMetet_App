package com.skymeter.skymeterapp.ui.splash

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.skymeter.skymeterapp.R
import com.skymeter.skymeterapp.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    lateinit var binding: FragmentSplashBinding
    lateinit var navController: NavController
    lateinit var mContext: Context



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(binding.root)
        mContext = binding.root.context

        setUi()

    }

    private fun setUi(){

        val animationimage = AnimationUtils.loadAnimation(
            context,
            R.anim.fade_in
        )

        binding.tv.startAnimation(animationimage)

        animationimage.setAnimationListener(object : Animation.AnimationListener{

            override fun onAnimationEnd(p0: Animation) {
                navController.navigate(R.id.homeFragment)
            }

            override fun onAnimationRepeat(p0: Animation) {}

            override fun onAnimationStart(p0: Animation) {}

        })


    }

}