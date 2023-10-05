package com.syncdev.shifaa.ui.patient.medicine

import CalendarAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentPatientMedicineBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PatientMedicineFragment : Fragment() {

    private lateinit var binding: FragmentPatientMedicineBinding
    private lateinit var calenderAdapter: CalendarAdapter
    private lateinit var medicineAdapter: MedicineAdapter
    private val patientMedicineViewModel by viewModels<PatientMedicineViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_patient_medicine,
            container,
            false
        )

        calenderAdapter = CalendarAdapter()
        medicineAdapter = MedicineAdapter(requireContext())

        binding.apply {
            rvCalendarMedication.adapter = calenderAdapter
            rvMedications.adapter = medicineAdapter
        }

        patientMedicineViewModel.getAvailableDaysList()
        patientMedicineViewModel.getAllMedicine()

        calenderAdapter.submitList(
            patientMedicineViewModel.availableDays.value
        )

        calenderAdapter.onDateClicked ={
            patientMedicineViewModel.chosenDate.value = it.date
            patientMedicineViewModel.filterMedicationsByEndDate(date = it.date)
        }

        patientMedicineViewModel.medicineByDay.observe(viewLifecycleOwner, Observer {
            medicineAdapter.submitList(it)
            if (it != null) {
                if (it.isEmpty()){
                    showNoMedicine(true)
                }else showNoMedicine(false)
            }
        })

        medicineAdapter.onMedicineClicked ={medicineId->
            findNavController().navigate(
                PatientMedicineFragmentDirections
                    .actionPatientMedicineFragmentToMedicineDetailsFragment(medicineId)
            )
        }


        return binding.root
    }

    private fun showNoMedicine(show: Boolean){
        binding.apply {
            ivNoMedicine.isVisible = show
            tvNoMedicine.isVisible = show
            viewHorizontalMedication.isVisible = !show
            rvMedications.isVisible = !show
        }
    }

}