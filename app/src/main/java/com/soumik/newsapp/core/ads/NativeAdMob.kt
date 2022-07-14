package com.soumik.newsapp.core.ads

import android.content.Context
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.soumik.newsapp.R
import timber.log.Timber

object NativeAdMob : AdListener() {

    private const val TEST_NATIVE_AD_ID = "ca-app-pub-3940256099942544/2247696110"

    private var mNativeAd: NativeAd? = null


    fun showNativeAd(context: Context, templateView: TemplateView) {

        try {
            if (mNativeAd != null) {
                val styles = NativeTemplateStyle
                    .Builder()
//                    .withMainBackgroundColor(ColorDrawable(Color.parseColor("#FFFFFF")))
                    .build()
                templateView.setStyles(styles)
                templateView.setNativeAd(mNativeAd)
            } else {
                val adLoader = AdLoader.Builder(context, TEST_NATIVE_AD_ID)
                    .forNativeAd { ad: NativeAd ->
                        mNativeAd = ad
                        val styles = NativeTemplateStyle
                            .Builder()
//                            .withMainBackgroundColor(ColorDrawable(Color.parseColor("#FFFFFF")))
                            .build()
                        templateView.setStyles(styles)
                        templateView.setNativeAd(ad)

                    }
                    .withAdListener(this)
                    .withNativeAdOptions(
                        com.google.android.gms.ads.nativead.NativeAdOptions
                            .Builder()
                            .build()
                    )
//                            .withNativeAdOptions(NativeAdOptions
//                                    .Builder()
//                                    .build())
                    .build()
                adLoader.loadAd(AdRequest.Builder().build())
            }
        } catch (e: Exception) {
        }
    }

    fun destroy() {
        if (mNativeAd != null) {
            mNativeAd?.destroy()
        }
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