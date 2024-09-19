package com.hungln.retechtest.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.hungln.retechtest.R

class LoadingProgress(private val context: Context) {
    private val dialog = Dialog(context)

    fun showDialog() {
        dialog.setContentView(R.layout.layout_loading_progress)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.create()
        dialog.show()
    }

    fun hideDialog() {
        dialog.dismiss()
    }

}