package com.demir.yasin.util

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.demir.yasin.R

class LoadingHelper(context: Context) {
    val progress = Dialog(context)


    init {
        val inflate = LayoutInflater.from(context).inflate(R.layout.progress_view, null)
        progress.setContentView(inflate)
        progress.setCancelable(false)
    }



    fun showDialog() {
        if (!progress.isShowing) progress.show()
    }

    fun hideDialog() {
        if (progress.isShowing) progress.dismiss()
    }

    companion object {
        @Volatile
        private var instance: LoadingHelper? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: LoadingHelper(context).also { instance = it }
        }
    }
}