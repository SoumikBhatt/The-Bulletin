package com.soumik.newsapp.core.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log

private const val TAG = "Helper"

fun Context.launchUrl(url: String?) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
        Log.e(TAG, "launchUrl: Exception: " + e.localizedMessage)
    }
}