package com.soumik.newsapp.core.utils

import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

object Messenger {

    fun showSnackBar(binding: ViewBinding,message:String,length: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(binding.root,message,length).show()
    }
}