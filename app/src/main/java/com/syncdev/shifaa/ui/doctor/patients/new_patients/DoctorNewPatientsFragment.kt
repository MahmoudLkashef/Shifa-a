package com.syncdev.shifaa.ui.doctor.patients.new_patients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentDoctorNewPatientsBinding
import com.syncdev.shifaa.ui.doctor.patients.past_patients.PatientTest


class DoctorNewPatientsFragment : Fragment() {


    private lateinit var binding: FragmentDoctorNewPatientsBinding
    private lateinit var adapter: DoctorNewPatientAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_doctor_new_patients,
            container,
            false
        )

        adapter = DoctorNewPatientAdapter()
        val newPatientsList = listOf<PatientTest>(
            PatientTest(1,"Mohammed Magdy","22 Feb 2023","10:00 AM",R.drawable.patient_male),
            PatientTest(2,"Mohammed Ayman","29 Feb 2023","10:30 AM",R.drawable.patient_male),
            PatientTest(3,"Sarah Magdy","25 Jan 2023","11:00 AM",R.drawable.patient_female),
            PatientTest(4,"Abdulmageed Awad","22 Oct 2022","11:30 AM",R.drawable.patient_male),
            PatientTest(5,"Hossam Khedr","15 Feb 2023","12:00 PM",R.drawable.patient_male),
            PatientTest(6,"Kholoud Rady","6 Feb 2023","12:30 PM",R.drawable.patient_female),
            PatientTest(7,"Samy Ahmed","13 Dec 2022","1:00 PM",R.drawable.patient_male),
            PatientTest(8,"Khaled Omar","26 Jan 2023","1:30 PM",R.drawable.patient_male),
            PatientTest(9,"Aya Sayed","3 Mar 2023","2:00 PM",R.drawable.patient_female),
            PatientTest(10,"Mohab Hussein","1 Mar 2023","2:30 PM",R.drawable.patient_male),
            PatientTest(11,"Walaa Ali","11 Nov 2022","3:30 PM",R.drawable.patient_female),
            PatientTest(12,"Lila Salah","8 Feb 2023","4:00 PM",R.drawable.patient_female),
            PatientTest(13,"Mahmoud Reda","3 Feb 2023","4:30 PM",R.drawable.patient_male),
            PatientTest(14,"Mohammed Hassaan","3 Feb 2023","5:00 PM",R.drawable.patient_male),
        )

        adapter.submitList(newPatientsList)

        binding.tvNewPatientsNumber.text = "${newPatientsList.size} Pending requests"

        binding.rvNewPatients.adapter = adapter




        return binding.root
    }


}