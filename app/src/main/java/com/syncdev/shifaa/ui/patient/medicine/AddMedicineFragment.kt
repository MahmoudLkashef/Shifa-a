package com.syncdev.shifaa.ui.patient.medicine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentAddMedicineBinding

class AddMedicineFragment : Fragment() {

    private lateinit var binding: FragmentAddMedicineBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_add_medicine,
            container,
            false
        )

        return binding.root
    }

}