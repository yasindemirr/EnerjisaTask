package com.demir.yasin.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.demir.yasin.R


fun Context.showDialog(
    title: String? = null,
    description: String? = null,
) {


    val dialog = Dialog(this)
    dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.alert_dialog)


    val titleTv = dialog.findViewById(R.id.lastPayDate) as TextView
    val descriptionTv = dialog.findViewById(R.id.lastPayText) as TextView
    val button = dialog.findViewById<Button>(R.id.button)


    titleTv.text = title

    descriptionTv.text = description


    dialog.window?.setLayout(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.WRAP_CONTENT
    )
    dialog.window?.setGravity(Gravity.CENTER)
    dialog.show()
    button.setOnClickListener {
        dialog.dismiss()
    }

}

