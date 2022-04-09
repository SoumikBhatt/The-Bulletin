package com.soumik.newsapp.core.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import com.facebook.shimmer.ShimmerFrameLayout

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

fun View.handleVisibility(value: Boolean) {
    if (value) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

fun ShimmerFrameLayout.handleShimmer(value: Boolean) {
    if (value) this.startShimmer()
    else this.stopShimmer()
}