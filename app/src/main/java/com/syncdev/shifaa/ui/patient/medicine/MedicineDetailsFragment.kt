package com.syncdev.shifaa.ui.patient.medicine

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentMedicineDetailsBinding


class MedicineDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMedicineDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_medicine_details,
            container,
            false
        )

        return binding.root
    }


}