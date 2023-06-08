package com.syncdev.shifaa.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.syncdev.shifaa.databinding.DialogSignOutBinding

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

}