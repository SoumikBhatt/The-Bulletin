package com.soumik.newsapp.core.utils

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.soumik.newsapp.R

/**
created by Soumik on 26/6/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/



/**
 * load image with url
 */
fun ImageView.loadImage(url: String?,placeHolder: Drawable?=null) {

    try {

        val circularProgressDrawable = CircularProgressDrawable(this.context)
        circularProgressDrawable.apply {
            strokeWidth = 5f
            centerRadius = 30f
            setColorSchemeColors(
                ContextCompat.getColor(context, R.color.black)
            )
            start()
        }

        Glide.with(this)
            .load(url)
            .apply(RequestOptions().override(2048, 1600))
            .placeholder(placeHolder ?: circularProgressDrawable)
            .into(this)
    } catch (e: Exception) {
    }
}

/**
 * load image with bitmap
 */
fun ImageView.loadImage(bitmap: Bitmap?,placeHolder: Drawable?=null) {

    try {

        if (bitmap!=null) {
            val circularProgressDrawable = CircularProgressDrawable(this.context)
            circularProgressDrawable.apply {
                strokeWidth = 5f
                centerRadius = 30f
                setColorSchemeColors(
                    ContextCompat.getColor(context, R.color.black)
                )
                start()
            }

            Glide.with(this)
                .load(bitmap)
                .apply(RequestOptions().override(2048, 1600))
                .placeholder(placeHolder ?: circularProgressDrawable)
                .into(this)
        }

    } catch (e: Exception) {
    }
}

/**
 * load image with uri
 */
fun ImageView.loadImage(uri: Uri?,placeHolder: Drawable?=null) {
    try {

        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.apply {
            strokeWidth = 5f
            centerRadius = 30f
            setColorSchemeColors(
                ContextCompat.getColor(context, R.color.black)
            )
            start()
        }

        Glide.with(this)
            .load(uri)
            .placeholder(placeHolder ?: circularProgressDrawable)
            .apply(RequestOptions().override(2048, 1600))
            .into(this)
    } catch (e: Exception) {
    }
}

fun ImageView.setTint(@ColorRes color: Int?) {
    if (color == null) {
        ImageViewCompat.setImageTintList(this, null)
    } else {
        ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(ContextCompat.getColor(context, color)))
    }}