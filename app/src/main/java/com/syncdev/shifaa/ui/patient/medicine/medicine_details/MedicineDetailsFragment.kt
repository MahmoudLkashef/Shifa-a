package com.syncdev.shifaa.ui.patient.medicine.medicine_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentMedicineDetailsBinding
import com.syncdev.shifaa.utils.DateUtils
import com.syncdev.shifaa.utils.ImageMapping
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MedicineDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMedicineDetailsBinding
    private val medicineDetailsViewModel by viewModels<MedicineDetailsViewModel>()

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

        val args: MedicineDetailsFragmentArgs by navArgs()
        val medicineId = args.medicineId

        medicineDetailsViewModel.getMedicineById(medicineId)

        medicineDetailsViewModel.medicine.observe(viewLifecycleOwner, Observer {
            val drawable = ImageMapping(requireContext()).getDrawableByType(it.type)
            binding.apply {
                ivMedicineDetails.setImageDrawable(drawable)
                tvMedicineDetailsName.text = it.name
                tvMedicineDetailsFrequency.text = if (it.frequency =="1") "Once a day" else "${it.frequency} times a day"
                tvMedicineDetailsDosage.text = "${it.dosage} mg"
                tvMedicineDetailsDuration.text = DateUtils.calculateDays(it.startDate,it.endDate)
                btnBackMedicineDetails.setOnClickListener {
                    findNavController().popBackStack()
                }
            }
            for (time in it.scheduleLabels){
                val chip = medicineDetailsViewModel.createChip(requireContext(),time)
                binding.cgScheduledMedicineDetails.addView(chip)
            }

        })

        return binding.root
    }


}
