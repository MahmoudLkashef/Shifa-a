package com.syncdev.shifaa.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.core.view.isGone
import com.google.android.material.chip.Chip
import com.syncdev.domain.model.Medication
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.AddMedicinePrescriptionDialogBinding
import com.syncdev.shifaa.databinding.AddNewChronicDiseasesDialogBinding
import com.syncdev.shifaa.databinding.DialogCancelUpcomingAppointmentBinding
import com.syncdev.shifaa.databinding.DialogCantCancelAppointmentBinding
import com.syncdev.shifaa.databinding.DialogCantRescheduleAppointmentBinding
import com.syncdev.shifaa.databinding.DialogDispenseMedicineBinding
import com.syncdev.shifaa.databinding.DialogRateDoctorBinding
import com.syncdev.shifaa.databinding.DialogSignOutBinding
import com.syncdev.shifaa.databinding.EditEmergecyContactsDialogBinding
import com.syncdev.shifaa.databinding.MedicalCardQrCodeDialogBinding
import com.syncdev.shifaa.databinding.UpdatePatientMedicalCardDialogBinding
import com.syncdev.shifaa.ui.doctor.home.prescription.PrescriptionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class Dialogs {

    private val TAG = "Dialogs"
    fun showSignOutDialog(context: Context, onSignOut: () -> Unit) {
        val dialogBinding = DialogSignOutBinding.inflate(LayoutInflater.from(context))
        val dialogView = dialogBinding.root

        val dialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)

        val alertDialog = dialogBuilder.create()

        //To make the background of the dialog transparent and show the rounded corners
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.btnSignOutDialog.setOnClickListener {
            onSignOut.invoke() // Invoke the provided method for sign out
            alertDialog.dismiss() // Close the dialog if needed
        }

        dialogBinding.btnCancelSignOutDialog.setOnClickListener {
            alertDialog.dismiss() // Close the dialog
        }

        alertDialog.show()
    }

    fun showRateDoctorDialog(context: Context, onSubmitRating: (Float) -> Unit) {
        val dialogBinding = DialogRateDoctorBinding.inflate(LayoutInflater.from(context))
        val dialogView = dialogBinding.root

        val dialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)

        val alertDialog = dialogBuilder.create()

        // To make the background of the dialog transparent and show the rounded corners
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.btnSubmitDoctorRating.isEnabled = false // Disable the submit button initially

        dialogBinding.rbRatingDoctor.setOnRatingBarChangeListener { _, rating, _ ->
            dialogBinding.btnSubmitDoctorRating.isEnabled =
                rating > 0 // Enable the submit button if a rating is selected
        }

        dialogBinding.btnSubmitDoctorRating.setOnClickListener {
            val rating = dialogBinding.rbRatingDoctor.rating
            dialogBinding.apply {
                tvRatingDoctorTitle.text = "Thank You For Your Rating"
                rbRatingDoctor.isGone = true
                btnSubmitDoctorRating.isGone = true
                tvDismissRatingDoctor.isGone = true
            }
            CoroutineScope(Dispatchers.Main).launch {
                delay(2500)
                onSubmitRating.invoke(rating) // Invoke the provided method for submit rating
                alertDialog.dismiss()
            }
        }

        dialogBinding.tvDismissRatingDoctor.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    fun editEmergencyContactsDialog(
        context: Context,
        onSavedClicked: (firstContact: String, SecondContact: String) -> Unit
    ) {
        val dialogBinding = EditEmergecyContactsDialogBinding.inflate(LayoutInflater.from(context))
        val dialogView = dialogBinding.root

        val dialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)

        val alertDialog = dialogBuilder.create()

        //To make the background of the dialog transparent and show the rounded corners
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.btnCancelEmergencyDialog.setOnClickListener {
            alertDialog.dismiss()
        }

        dialogBinding.btnSaveEmergencyContactsDialog.setOnClickListener {
            if (validEmergencyContacts(dialogBinding)) {
                val firstContact = dialogBinding.etFirstEmergencyContactDialog.text.toString()
                val secondContact = dialogBinding.etSecondEmergencyContactDialog.text.toString()
                Log.i(TAG, "editEmergencyContactsDialog: $firstContact / $secondContact")
                onSavedClicked(firstContact, secondContact)
                alertDialog.dismiss()
            }
        }

        alertDialog.show()
    }

    fun showCancelUpcomingAppointmentDialog(context: Context, onCancelAppointment: () -> Unit) {
        val dialogBinding =
            DialogCancelUpcomingAppointmentBinding.inflate(LayoutInflater.from(context))
        val dialogView = dialogBinding.root

        val dialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)

        val alertDialog = dialogBuilder.create()

        //To make the background of the dialog transparent and show the rounded corners
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.btnConfirmCancellingAppointment.setOnClickListener {
            onCancelAppointment.invoke() // Invoke the provided method for sign out
            alertDialog.dismiss() // Close the dialog if needed
        }

        dialogBinding.btnCloseCancelAppointmentDialog.setOnClickListener {
            alertDialog.dismiss() // Close the dialog
        }

        alertDialog.show()
    }

    fun showCantCancelUpcomingAppointmentDialog(context: Context) {
        val dialogBinding = DialogCantCancelAppointmentBinding.inflate(LayoutInflater.from(context))
        val dialogView = dialogBinding.root

        val dialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)

        val alertDialog = dialogBuilder.create()

        //To make the background of the dialog transparent and show the rounded corners
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.btnCantCancelAppointment.setOnClickListener {
            alertDialog.dismiss() // Close the dialog
        }

        alertDialog.show()
    }

    fun showAddNewChronicDiseasesDialog(
        context: Context,
        onSaveClicked: (chronicDisease: String) -> Unit
    ) {
        val dialogBinding = AddNewChronicDiseasesDialogBinding.inflate(LayoutInflater.from(context))
        val dialogView = dialogBinding.root

        val dialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)

        val alertDialog = dialogBuilder.create()

        //To make the background of the dialog transparent and show the rounded corners
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.btnSaveChronicAddDialog.setOnClickListener {
            val chronicDisease = dialogBinding.etChronicDiseasesAddDialog.text.toString()
            onSaveClicked.invoke(chronicDisease)
            alertDialog.dismiss()
        }

        dialogBinding.btnCancelChronicAddDialog.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()

    }

    fun showUpdatePatientMedicalCardDialog(
        context: Context,
        onSaveClicked: (bloodType: String, height: String, weight: String) -> Unit
    ) {
        val dialogBinding =
            UpdatePatientMedicalCardDialogBinding.inflate(LayoutInflater.from(context))
        val dialogView = dialogBinding.root

        val dialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)

        val alertDialog = dialogBuilder.create()

        //To make the background of the dialog transparent and show the rounded corners
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val bloodTypesList = context.resources.getStringArray(R.array.blood_types)
        val medicineTypeAdapter = ArrayAdapter(context, R.layout.dropdown_item, bloodTypesList)
        dialogBinding.dropdownMenuBloodTypeMedicalCardUpdateDialog.setAdapter(medicineTypeAdapter)

        dialogBinding.btnSaveMedicalCardUpdateDialog.setOnClickListener {
            val bloodType =
                dialogBinding.dropdownMenuBloodTypeMedicalCardUpdateDialog.text.toString()
            val height = dialogBinding.etHeightMedicalCardUpdateDialog.text.toString()
            val weight = dialogBinding.etWeightMedicalCardUpdateDialog.text.toString()
            onSaveClicked.invoke(bloodType, height, weight)
            alertDialog.dismiss()
        }

        dialogBinding.btnCancelMedicalCardUpdateDialog.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()

    }

    fun showCantRescheduleUpcomingAppointmentDialog(context: Context) {
        val dialogBinding =
            DialogCantRescheduleAppointmentBinding.inflate(LayoutInflater.from(context))
        val dialogView = dialogBinding.root

        val dialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)

        val alertDialog = dialogBuilder.create()

        //To make the background of the dialog transparent and show the rounded corners
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.btnCantRescheduleAppointment.setOnClickListener {
            alertDialog.dismiss() // Close the dialog
        }

        alertDialog.show()
    }

    fun addNewMedicineToPrescriptionDialog(context: Context, viewModel: PrescriptionViewModel) {
        val dialogBinding =
            AddMedicinePrescriptionDialogBinding.inflate(LayoutInflater.from(context))
        val dialogBuilder = AlertDialog.Builder(context).setView(dialogBinding.root)
        val alertDialog = dialogBuilder.create()

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val scheduleLabelsList = mutableListOf<String>()
        val medicineTypeList = context.resources.getStringArray(R.array.medicine_type)
        val medicineTypeAdapter = ArrayAdapter(context, R.layout.dropdown_item, medicineTypeList)
        dialogBinding.dropdownMenuMedicineTypeAddDialog.setAdapter(medicineTypeAdapter)

        dialogBinding.btnAddMedicineScheduleDialog.setOnClickListener {
            var scheduleLabel = dialogBinding.etScheduleAddDialog.text.toString()
            if (scheduleLabel.isNotEmpty()) {
                scheduleLabelsList.add(scheduleLabel)
                dialogBinding.chipsGroupScheduleAddDialog.removeAllViews()
                for (scheduleLabel in scheduleLabelsList) {
                    val chip = Chip(dialogBinding.chipsGroupScheduleAddDialog.context)
                    chip.text = scheduleLabel
                    dialogBinding.chipsGroupScheduleAddDialog.addView(chip)
                }
                dialogBinding.etScheduleAddDialog.text?.clear()
            }
        }

        setupSaveButton(dialogBinding, viewModel, medicineTypeList, scheduleLabelsList, alertDialog)
        dialogBinding.btnCancelAddDialog.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    fun showQrCodeDialog(context: Context, qrCode: Bitmap, onDone: () -> Unit) {
        val dialogBinding = DialogDispenseMedicineBinding.inflate(LayoutInflater.from(context))
        val dialogView = dialogBinding.root

        val dialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)

        val alertDialog = dialogBuilder.create()

        //To make the background of the dialog transparent and show the rounded corners
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.apply {
            btnDoneQrcodeDialog.setOnClickListener {
                onDone.invoke() // Invoke the provided method for sign out
                alertDialog.dismiss() // Close the dialog if needed
            }
            btnCancelQrcodeDialog.setOnClickListener {
                alertDialog.dismiss() // Close the dialog
            }
            ivQrCodeDialog.setImageBitmap(Bitmap.createBitmap(qrCode))
        }

        alertDialog.show()
    }

    fun showMedicalCardQrCode(context: Context, qrCode: Bitmap) {
        val dialogBinding = MedicalCardQrCodeDialogBinding.inflate(LayoutInflater.from(context))
        val dialogView = dialogBinding.root

        val dialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)

        val alertDialog = dialogBuilder.create()

        //To make the background of the dialog transparent and show the rounded corners
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.imgQrCodeCardDialog.setImageBitmap(Bitmap.createBitmap(qrCode))

        alertDialog.show()
    }

    private fun setupSaveButton(
        dialogBinding: AddMedicinePrescriptionDialogBinding,
        viewModel: PrescriptionViewModel,
        medicineTypeList: Array<String>,
        scheduleLabelsList: MutableList<String>,
        alertDialog: AlertDialog
    ) {
        dialogBinding.btnSaveAddDialog.setOnClickListener {
            if (validInputs(dialogBinding, medicineTypeList)) {
                val medicineData = Medication(
                    id = Random.nextInt(1,1000000000).toString(),
                    dialogBinding.etMedicineNameAddDialog.text.toString(),
                    dialogBinding.dropdownMenuMedicineTypeAddDialog.text.toString(),
                    dialogBinding.etDurationAddDialog.text.toString(),
                    dialogBinding.etDoseAddDialog.text.toString(),
                    dialogBinding.etFrequencyAddDialog.text.toString(),
                    scheduleLabelsList.toList()
                )
                viewModel.updateMedicationList(medicineData)
                alertDialog.dismiss()
            }
        }
    }

    private fun validInputs(
        dialogBinding: AddMedicinePrescriptionDialogBinding,
        medicineTypeList: Array<String>
    ): Boolean {

        return Validation.validateName(
            dialogBinding.etMedicineNameAddDialog.text.toString(),
            dialogBinding.tilMedicineNameAddDialog
        ) and Validation.validateName(
            dialogBinding.etDurationAddDialog.text.toString(),
            dialogBinding.tilDurationAddDialog
        ) and Validation.validateName(
            dialogBinding.etDoseAddDialog.text.toString(),
            dialogBinding.tilDoseAddDialog
        ) and Validation.validateName(
            dialogBinding.etFrequencyAddDialog.text.toString(),
            dialogBinding.tilFrequencyAddDialog
        ) and Validation.validateMedicineType(
            dialogBinding.dropdownMenuMedicineTypeAddDialog.text.toString(),
            medicineTypeList,
            dialogBinding.tilMedicineTypeAddDialog
        ) and Validation.validateScheduleLabel(
            dialogBinding.chipsGroupScheduleAddDialog.childCount,
            dialogBinding.tilScheduleAddDialog
        )

    }

    private fun validEmergencyContacts(dialogBinding: EditEmergecyContactsDialogBinding): Boolean {
        return Validation.validatePhoneNumber(
            dialogBinding.etFirstEmergencyContactDialog.text.toString(),
            dialogBinding.tilFirstEmergecyContactDialog
        ) and Validation.validatePhoneNumber(
            dialogBinding.etSecondEmergencyContactDialog.text.toString(),
            dialogBinding.tilSecondEmergecyContactDialog
        )
    }


}