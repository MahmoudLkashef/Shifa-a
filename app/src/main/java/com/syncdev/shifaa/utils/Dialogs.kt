package com.syncdev.shifaa.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.DialogSignOutBinding
import com.syncdev.shifaa.databinding.EditEmergecyContactsDialogBinding

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


}