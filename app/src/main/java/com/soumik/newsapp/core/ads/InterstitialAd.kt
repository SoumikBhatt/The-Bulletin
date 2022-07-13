package com.soumik.newsapp.core.ads

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import timber.log.Timber

object InterstitialAdMob : FullScreenContentCallback() {

    private const val TEST_INTERSTITIAL_ID = "ca-app-pub-3940256099942544/1033173712"

    private var mInterstitialAd: InterstitialAd? = null
    private var mInterstitialAdController : InterstitialAdController?=null


    fun loadInterstitialAd(activity: Activity,adUnitId: String= TEST_INTERSTITIAL_ID,interstitialAdController: InterstitialAdController) {
        mInterstitialAdController = interstitialAdController
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(activity,adUnitId,adRequest,object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(p0: LoadAdError) {
                Timber.e( "Exception in onAdFailedToLoad(): $p0")
                mInterstitialAd = null
            }

            override fun onAdLoaded(p0: InterstitialAd) {
                mInterstitialAd = p0
            }
        })
        mInterstitialAd?.fullScreenContentCallback = this

        if (mInterstitialAd != null) {
            mInterstitialAd?.show(activity)
        } else {
            Timber.d("loadInterstitialAd(): ad wasn't ready")
            mInterstitialAdController?.onAddDismissed()
        }
    }

    override fun onAdClicked() {
        // Called when a click is recorded for an ad.
        Timber.d("Ad was clicked.")
    }

    override fun onAdDismissedFullScreenContent() {
        // Called when ad is dismissed.
        Timber.d("Ad dismissed fullscreen content.")
        mInterstitialAd = null
        mInterstitialAdController?.onAddDismissed()
    }

    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
        // Called when ad fails to show.
        Timber.e("Ad failed to show fullscreen content: ${adError.code}")
        mInterstitialAd = null
    }

    override fun onAdImpression() {
        // Called when an impression is recorded for an ad.
        Timber.d("Ad recorded an impression.")
    }

    override fun onAdShowedFullScreenContent() {
        // Called when ad is shown.
        Timber.d("Ad showed fullscreen content.")
    }


    interface InterstitialAdController {
        fun onAddDismissed()
    }
}