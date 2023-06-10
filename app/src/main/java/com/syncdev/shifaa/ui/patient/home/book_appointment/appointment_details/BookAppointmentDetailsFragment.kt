package com.syncdev.shifaa.ui.patient.home.book_appointment.appointment_details

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.syncdev.domain.model.Doctor
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentBookAppointmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BookAppointmentDetailsFragment : Fragment() {

    private lateinit var binding: FragmentBookAppointmentDetailsBinding
    private val TAG = "BookAppointmentDetailsFragment"
    private val bookAppointmentViewModel by viewModels<BookAppointmentDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_book_appointment_details,
            container,
            false
        )

        val args: BookAppointmentDetailsFragmentArgs by navArgs()
        val doctorId: String = args.doctorId

        bookAppointmentViewModel.setTime(args.time)
        bookAppointmentViewModel.setDate(args.date)

        bookAppointmentViewModel.getDoctorById(doctorId)

        if (bookAppointmentViewModel.haveDateAndTime()){
            hidePreWrittenText()
            showDataAndTime()
        }

        binding.apply {
            btnBookNowBookAppointmentDetails.setOnClickListener {
                if (bookAppointmentViewModel.haveDateAndTime()){
                    findNavController().navigate(
                        BookAppointmentDetailsFragmentDirections
                            .actionBookAppointmentDetailsFragmentToProblemDescriptionFragment(
                                doctorId = doctorId,
                                time = bookAppointmentViewModel.time.value!!,
                                date = bookAppointmentViewModel.date.value!!
                            )
                    )
                }else{
                    showError()
                }
            }

            ivBackBookAppointmentDetails.setOnClickListener {
                findNavController().popBackStack()
            }

            cvAvailableTimeBookAppointmentDetails.setOnClickListener {
                findNavController()
                    .navigate(BookAppointmentDetailsFragmentDirections
                        .actionBookAppointmentDetailsFragmentToSelectAppointmentDateTimeFragment(doctorId))
            }
        }

        bookAppointmentViewModel.doctor.observe(viewLifecycleOwner, Observer { doctor ->
            binding.apply {
                tvDoctorNameBookAppointmentDetails.text = bookAppointmentViewModel.getFullName(doctor.firstName,doctor.lastName)
                tvDoctorSpecialtyBookAppointmentDetails.text = doctor.speciality
                tvNumOfPatientsBookAppointmentDetails.text = doctor.numOfPatients.toString()
                tvExperienceYearsBookAppointmentDetails.text = doctor.yearsOfExperience.toString() + " yr"
                tvRatingBookAppointmentDetails.text = bookAppointmentViewModel.getRate(
                    doctor.totalRating,doctor.numOfReviews
                )

                tvAboutBookAppointmentDetails.text = doctor.aboutDoctor
                when(doctor.gender){
                    "Male" -> {
                        ivDoctorPicBookAppointmentDetails
                            .setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.doctor_male))
                    }
                    "Female" -> {
                        ivDoctorPicBookAppointmentDetails
                            .setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.doctor_female))
                    }
                }
            }
        })


        return binding.root
    }

    private fun hidePreWrittenText(){
        binding.apply {
            tvAvailabilityBookAppointmentDetails.isVisible = false
            tvAvailableTimeBookAppointmentDetails.isVisible = false
        }
    }

    private fun showDataAndTime(){
        binding.apply {
            ivSeletedTime.isVisible = true
            ivSelectedDate.isVisible = true
            tvSelectedTime.text = bookAppointmentViewModel.time.value
            tvSelectedDate.text = bookAppointmentViewModel.date.value
        }
    }

    private fun showError(){

        binding.cvAvailableTimeBookAppointmentDetails.background = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.card_view_available_time_error_backgroung
        )

        Snackbar.make(requireView(),"Please Select Time And Date",Snackbar.ANIMATION_MODE_SLIDE).show()
    }

}