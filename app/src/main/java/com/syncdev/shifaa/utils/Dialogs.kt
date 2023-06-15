package com.syncdev.shifaa.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import androidx.core.view.isGone
import com.syncdev.shifaa.databinding.DialogRateDoctorBinding
import com.syncdev.shifaa.databinding.DialogCancelUpcomingAppointmentBinding
import com.syncdev.shifaa.databinding.DialogCantCancelAppointmentBinding
import com.syncdev.shifaa.databinding.DialogCantRescheduleAppointmentBinding
import com.syncdev.shifaa.databinding.DialogSignOutBinding
import com.syncdev.shifaa.databinding.EditEmergecyContactsDialogBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Dialogs {

    private val TAG="Dialogs"
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
            dialogBinding.btnSubmitDoctorRating.isEnabled = rating > 0 // Enable the submit button if a rating is selected
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

    fun editEmergencyContactsDialog(context: Context) {
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
            val firstContact = dialogBinding.etFirstEmergencyContactDialog.text.toString()
            val secondContact = dialogBinding.etSecondEmergencyContactDialog.text.toString()
            Log.i(TAG, "editEmergencyContactsDialog: $firstContact / $secondContact")
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    fun showCancelUpcomingAppointmentDialog(context: Context, onCancelAppointment: () -> Unit) {
        val dialogBinding = DialogCancelUpcomingAppointmentBinding.inflate(LayoutInflater.from(context))
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

    fun showCantRescheduleUpcomingAppointmentDialog(context: Context) {
        val dialogBinding = DialogCantRescheduleAppointmentBinding.inflate(LayoutInflater.from(context))
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


}