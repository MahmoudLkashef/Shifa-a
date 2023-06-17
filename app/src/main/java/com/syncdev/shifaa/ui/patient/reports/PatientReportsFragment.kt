package com.syncdev.shifaa.ui.patient.reports

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentPatientReportsBinding
import com.syncdev.shifaa.utils.Conversion
import com.syncdev.shifaa.utils.Dialogs
import com.syncdev.shifaa.utils.notification.NotificationUtils
import com.syncdev.shifaa.utils.qrcode.QrCode
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PatientReportsFragment : Fragment() {

    private val TAG="PatientReportsFragment"
    private lateinit var binding:FragmentPatientReportsBinding
    private lateinit var reportsAdapter: PatientReportsAdapter
    private val reportsViewModel by viewModels<PatientReportsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_patient_reports,
            container,
            false
        )

        reportsAdapter = PatientReportsAdapter()

        reportsViewModel.getReports()

        binding.apply {
            rvPatientReports.adapter = reportsAdapter
        }

        reportsViewModel.reports.observe(viewLifecycleOwner, Observer {
            reportsAdapter.submitList(it)
            if (it.isEmpty()){
                showNoReports(true)
            }else showNoReports(false)
        })

        reportsAdapter.onDispenseMedicineClicked = {medications ->
            val serializedMedicines = QrCode.serializeMedicines(medications,"Medications")
            val qrCode = QrCode.generateQRCode(serializedMedicines)
            if (qrCode != null) {
                Dialogs().showQrCodeDialog(requireContext(),qrCode){
                    val scheduledMedications = reportsViewModel.convertMedicationsToScheduledMedications(medications)
                    reportsViewModel.insertScheduledMedication(scheduledMedications)
                    NotificationUtils(requireContext()).createMedicationReminders(medications)
                    Log.i(TAG, "$scheduledMedications")
                    Toast.makeText(requireContext(),"Done",Toast.LENGTH_LONG).show()
                }
            }
        }


        return binding.root
    }

    private fun showNoReports(show: Boolean){
        binding.apply {
            ivNoReportsFound.isVisible = show
            tvNoReportsFound.isVisible = show
        }
    }

}