package com.soumik.newsapp.core.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.facebook.shimmer.ShimmerFrameLayout

private const val TAG = "Helper"


fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.isGone() = this.visibility == View.GONE

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)



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

fun Context.share(subject:String,body:String,chooserTitle:String){
    try {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        shareIntent.putExtra(Intent.EXTRA_TEXT, "$body\n\n\nDownload the App now: https://play.google.com/store/apps/details?id=$packageName")
        startActivity(Intent.createChooser(shareIntent, chooserTitle))
    } catch (e: Exception) {
    }
}

fun Context.feedback(email:String,subject: String?="",body:String?=""){
    val installed = appInstalledOrNot("com.google.android.gm")
    if (installed) {
        try {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "text/html"
            val pm = packageManager
            val matches = pm.queryIntentActivities(emailIntent, 0)
            var className: String? = null
            for (info in matches) {
                if (info.activityInfo.packageName == "com.google.android.gm") {
                    className = info.activityInfo.name
                    if (className != null && className.isNotEmpty()) {
                        break
                    }
                }
            }
            emailIntent.setClassName("com.google.android.gm", className!!)
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            emailIntent.putExtra(Intent.EXTRA_TEXT, body)
            startActivity(emailIntent)
        } catch (e: Exception) {
        }
    } else {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            val data = Uri.parse("mailto:$email?subject=$subject")
            intent.data = data
            intent.putExtra(Intent.EXTRA_TEXT,body)
            startActivity(intent)

        } catch (e: Exception) {
            e.printStackTrace()
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https:/mail.google.com")))
        }
    }
}

private fun Context.appInstalledOrNot(uri: String): Boolean {
    val pm = packageManager
    val appInstalled: Boolean = try {
        pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
    return appInstalled
}

fun Context.rateApp(){
    val appId = packageName
    val rateIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("market://details?id=$appId")
    )
    var marketFound = false

    // find all applications able to handle our rateIntent
    try {
        val otherApps = packageManager
            .queryIntentActivities(rateIntent, 0)
        for (otherApp in otherApps) {
            // look for Google Play application
            if (otherApp.activityInfo.applicationInfo.packageName == "com.android.vending") {

                val otherAppActivity = otherApp.activityInfo
                val componentName = ComponentName(
                    otherAppActivity.applicationInfo.packageName,
                    otherAppActivity.name
                )
                // make sure it does NOT open in the stack of your activity
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                // task reparenting if needed
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                // if the Google Play was already open in a search result
                //  this make sure it still go to the app page you requested
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                // this make sure only the Google Play app is allowed to
                // intercept the intent
                rateIntent.component = componentName
                startActivity(rateIntent)
                marketFound = true
                break

            }
        }
    } catch (e: Exception) {}

    // if GP not present on device, open web browser
    if (!marketFound) {
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://play.google.com/store/apps/details?id=$appId")
        )
        startActivity(webIntent)
    }
}

fun Context.likeOnFB(pageID:String,pageUserName:String){
    return try {
        packageManager.getPackageInfo("com.facebook.katana", 0)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/$pageID"))
        return  startActivity(intent)

    } catch (e: Exception) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/$pageUserName")))
    }
}