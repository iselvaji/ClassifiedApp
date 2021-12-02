package com.dubizzle.classifiedapp.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

object UiUtils {

    fun showErrorDialog(context: Context, messageToDisplay : String) {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.apply {
            setMessage(messageToDisplay)
            setPositiveButton(context.getString(android.R.string.ok)) { _, _ ->
                //dismiss the dialog
            }
        }.create().show()
    }
}