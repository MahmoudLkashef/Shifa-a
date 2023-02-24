package com.syncdev.shifaa.ui.auth.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentOnBoardingFirstBinding


class OnBoardingFirstFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardingFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardingFirstBinding.inflate(inflater,container,false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)


        binding.btnOnboardingFirstNext.setOnClickListener {
            viewPager?.currentItem = 1
        }

        binding.tvOnboardingFirstSkip.setOnClickListener {
            viewPager?.currentItem = 2
        }

        return binding.root
    }


}