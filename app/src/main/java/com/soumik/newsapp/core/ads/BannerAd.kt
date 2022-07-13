package com.soumik.newsapp.core.ads

import android.app.Activity
import android.util.DisplayMetrics
import android.widget.FrameLayout
import com.google.android.gms.ads.*
import timber.log.Timber

/**
created by Soumik on 13/7/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/
object BannerAd : AdListener() {

    private const val TEST_BANNER_ID = "ca-app-pub-3940256099942544/6300978111"

    private fun Activity.getAdSize(): AdSize {
        val display = windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)

        val density = outMetrics.density
        val adWidthPixels = outMetrics.widthPixels.toFloat()
        val adWidth = (adWidthPixels / density).toInt()
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
    }


    fun loadBanner(
        activity: Activity,
        adContainerView: FrameLayout,
        size: AdSize = activity.getAdSize(),
        adUnitInt: String = TEST_BANNER_ID
    ) {
        try {
            val adView = AdView(activity)
            adView.adUnitId = adUnitInt
            adContainerView.addView(adView)

            val adRequest = AdRequest.Builder()
    //            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build()
            adView.setAdSize(size)

            adView.loadAd(adRequest)
            adView.adListener = this
        } catch (e: Exception) {
            Timber.e(e, "Exception in loadBanner(): ${e.localizedMessage}")
        }

    }

    fun removeBanner(adContainerView: FrameLayout) {
        adContainerView.removeAllViews()
    }

    override fun onAdClicked() {
        // Code to be executed when the user clicks on an ad.
        Timber.d("onAdClicked(): ")
    }

    override fun onAdClosed() {
        // Code to be executed when the user is about to return
        // to the app after tapping on an ad.
        Timber.d("onAdClosed(): ")
    }

    override fun onAdFailedToLoad(adError: LoadAdError) {
        // Code to be executed when an ad request fails.
        Timber.d("onAdFailedToLoad(): ${adError.code}")
    }

    override fun onAdImpression() {
        // Code to be executed when an impression is recorded
        // for an ad.
        Timber.d("onAdImpression(): ")
    }

    override fun onAdLoaded() {
        // Code to be executed when an ad finishes loading.
        Timber.d("onAdLoaded(): ")
    }

    override fun onAdOpened() {
        // Code to be executed when an ad opens an overlay that
        // covers the screen.
        Timber.d("onAdOpened(): ")
    }


}