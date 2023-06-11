package com.syncdev.shifaa.ui.doctor.patients.new_patients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentDoctorNewPatientsBinding
import com.syncdev.shifaa.ui.doctor.patients.DoctorPatientsFragmentDirections
import com.syncdev.shifaa.ui.doctor.patients.past_patients.PatientTest
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DoctorNewPatientsFragment : Fragment() {


    private lateinit var binding: FragmentDoctorNewPatientsBinding
    private lateinit var adapter: DoctorNewPatientAdapter
    private val doctorNewPatientsViewModel by viewModels<DoctorNewPatientsViewModel>()

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

        adapter = DoctorNewPatientAdapter(requireContext())

        doctorNewPatientsViewModel.fetchAppointmentRequests()

        binding.apply {
            rvNewPatients.adapter = adapter
        }

        doctorNewPatientsViewModel.appointmentRequests.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            binding.tvNewPatientsNumber.text = "${it.size} Pending Requests"
            if (it.isEmpty()){
                showNoDataFound(true)
            }else showNoDataFound(false)
        })

        doctorNewPatientsViewModel.updateList.observe(viewLifecycleOwner, Observer {update->
            if (update){
                doctorNewPatientsViewModel.fetchAppointmentRequests()
            }
        })

        adapter.onSeeMoreClicked = {
            val fullName = "${it.patient.firstName} " + it.patient.lastName
            findNavController().navigate(
                DoctorPatientsFragmentDirections
                    .actionDoctorPatientsFragmentToAppointmentRequestDetailsFragment(
                        patientName = fullName,
                        patientId = it.patient.id!!,
                        patientGender = it.patient.gender,
                        time = it.time,
                        date = it.date,
                        comment = it.comment
                    )
            )
        }

        adapter.onDeclineClicked = {
            doctorNewPatientsViewModel.deleteAppointmentRequest(
                doctorId = it.doctorId,
                date = it.date,
                time = it.time
            )
        }

        return binding.root
    }

    private fun showNoDataFound(show: Boolean){
        binding.apply {
            tvNoRequestsFound.isVisible = show
            ivNoRequestsFound.isVisible = show
        }
    }

}