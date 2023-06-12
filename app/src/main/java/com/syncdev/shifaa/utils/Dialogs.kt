package com.syncdev.shifaa.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.syncdev.shifaa.databinding.DialogRateDoctorBinding
import com.syncdev.shifaa.databinding.DialogSignOutBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Dialogs {

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

}