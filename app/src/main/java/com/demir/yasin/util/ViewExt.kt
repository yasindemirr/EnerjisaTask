package com.demir.yasin.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast

fun Context.copy(data : String){
    val clipboardManager = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText("text", data)
    clipboardManager.setPrimaryClip(clipData)
    Toast.makeText(this,"${data} panoya kopyalandı",Toast.LENGTH_SHORT).show()
}