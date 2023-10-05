package com.syncdev.shifaa.ui.auth.onboarding.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentOnBoardingSecondBinding


class OnBoardingSecondFragment : Fragment() {


    private lateinit var binding: FragmentOnBoardingSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardingSecondBinding.inflate(inflater,container,false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        binding.btnOnboardingSecondNext.setOnClickListener {
            viewPager?.currentItem = 2
        }

        binding.tvOnboardingSecondSkip.setOnClickListener {
            viewPager?.currentItem = 2
        }


        return binding.root
    }




}