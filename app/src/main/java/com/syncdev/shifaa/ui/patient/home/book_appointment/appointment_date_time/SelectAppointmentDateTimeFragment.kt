package com.syncdev.shifaa.ui.patient.home.book_appointment.appointment_date_time

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentSelectAppointmentDateTimeBinding
import com.syncdev.shifaa.ui.patient.home.book_appointment.appointment_details.BookAppointmentDetailsFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar


@AndroidEntryPoint
class SelectAppointmentDateTimeFragment : Fragment() {


    private lateinit var binding: FragmentSelectAppointmentDateTimeBinding
    private val selectDateTimeViewModel by viewModels<SelectDateTimeViewModel>()
    private val TAG = "SelectAppointmentDateTimeFragment"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_select_appointment_date_time,
            container,
            false
        )
        val args: BookAppointmentDetailsFragmentArgs by navArgs()
        val doctorId: String = args.doctorId

        initHideViews()

        binding.apply {
            // Set the minimum and maximum dates for the CalendarView
            cvSelectDate.minDate = selectDateTimeViewModel.getCurrentDate()
            cvSelectDate.maxDate = selectDateTimeViewModel.getMaxDate()

            cvSelectDate.setOnDateChangeListener { view, year, month, dayOfMonth ->
                val calendar = Calendar.getInstance()
                calendar.set(year, month, dayOfMonth)
                val selectedDate = selectDateTimeViewModel.handleSelectedDate(calendar.timeInMillis)
                cgTime.removeAllViews()
                selectDateTimeViewModel.setDate(selectedDate)
                selectDateTimeViewModel.getAppointmentTimeList(selectedDate,doctorId)
            }

            buttonBackSelectAppointment.setOnClickListener {
                findNavController().popBackStack()
            }

            btnSelectDateTime.setOnClickListener {
                if (selectDateTimeViewModel.validateData()){
                    findNavController().navigate(
                        SelectAppointmentDateTimeFragmentDirections.actionSelectAppointmentDateTimeFragmentToBookAppointmentDetailsFragment(
                            doctorId = doctorId,
                            date = selectDateTimeViewModel.date.value,
                            time = selectDateTimeViewModel.time.value
                        )
                    )
                }else{
                    showError(selectDateTimeViewModel.errorMessage.value)
                }
            }
        }

        selectDateTimeViewModel.appointmentTimeList.observe(viewLifecycleOwner, Observer {availableTime ->
            if (availableTime.isEmpty()){
                showNoTimeAvailable(true)
            }else {
                showNoTimeAvailable(false)
                for (time in availableTime) {
                    val chip = selectDateTimeViewModel.createChip(requireContext(), time)
                    binding.cgTime.addView(chip)
                }
            }
        })


        return binding.root
    }

    private fun showNoTimeAvailable(show: Boolean){
        binding.tvSelectTime.isVisible = !show
        binding.btnSelectDateTime.isVisible = !show
        binding.tvNoTimeAvailable.isVisible = show
        binding.ivNoTimeAvailable.isVisible = show
    }

    private fun initHideViews(){
        binding.apply {
            ivNoTimeAvailable.isVisible = false
            tvNoTimeAvailable.isVisible = false
            tvSelectTime.isVisible = false
            btnSelectDateTime.isVisible = false
        }
    }

    private fun showError(error: String?){
        if (error != null) {
            Snackbar.make(requireView(),error,Snackbar.ANIMATION_MODE_SLIDE).show()
            Log.i(TAG, "showError: $error")
        }
    }

}