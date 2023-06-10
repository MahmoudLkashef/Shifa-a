package com.syncdev.shifaa.ui.patient.home.book_appointment.problem_description

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentProblemDescriptionBinding
import com.syncdev.shifaa.utils.Internet
import com.syncdev.shifaa.utils.Validation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProblemDescriptionFragment : Fragment() {

    private lateinit var binding: FragmentProblemDescriptionBinding
    private val problemDescriptionViewModel by viewModels<ProblemDescriptionViewModel>()
    private val TAG = "ProblemDescriptionFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_problem_description,
            container,
            false
        )

        val args: ProblemDescriptionFragmentArgs by navArgs()
        val doctorId: String = args.doctorId
        val time: String = args.time
        val date: String = args.date

        binding.apply {
            viewModel = problemDescriptionViewModel

            buttonBackPatientProblemDescription.setOnClickListener {
                findNavController().popBackStack()
            }

            btnConfirmBooking.setOnClickListener {
                if (validateComment(tilProblemDescriptionComment)){
                    val appointment = problemDescriptionViewModel.gatherData(doctorId,time,date)
                    if (Internet.isInternetConnected(requireContext())){
                        problemDescriptionViewModel.createAppointmentRequest(appointment)
                    }else{
                        Snackbar.make(requireView(),"Check Your Internet Connection", Snackbar.ANIMATION_MODE_SLIDE)
                            .setAction("Retry") { problemDescriptionViewModel.createAppointmentRequest(appointment) }
                            .show()
                    }
                }
            }

        }

        problemDescriptionViewModel.navigate.observe(viewLifecycleOwner, Observer {
            if (it){
                findNavController().navigate(
                    ProblemDescriptionFragmentDirections.actionProblemDescriptionFragmentToPatientHomeFragment()
                )
            }
        })

        return binding.root
    }

    private fun validateComment(view: TextInputLayout): Boolean{
        return Validation.isEmptyTextInput(problemDescriptionViewModel.problemComment.value!!,view)
    }

}