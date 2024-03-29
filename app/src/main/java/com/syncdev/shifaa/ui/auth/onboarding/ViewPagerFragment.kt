package com.syncdev.shifaa.ui.auth.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.syncdev.shifaa.databinding.FragmentViewPagerBinding
import com.syncdev.shifaa.ui.auth.onboarding.screens.OnBoardingFirstFragment
import com.syncdev.shifaa.ui.auth.onboarding.screens.OnBoardingSecondFragment
import com.syncdev.shifaa.ui.auth.onboarding.screens.OnBoardingThirdFragment


class ViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewPagerBinding.inflate(inflater,container,false)

        val fragmentList = arrayListOf<Fragment>(
            OnBoardingFirstFragment(),
            OnBoardingSecondFragment(),
            OnBoardingThirdFragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter= adapter

        return binding.root
    }


}