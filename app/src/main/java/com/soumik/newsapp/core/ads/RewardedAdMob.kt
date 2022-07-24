package com.soumik.newsapp.core.ads

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.gson.Gson
import timber.log.Timber

object RewardedAdMob : FullScreenContentCallback() {
    
    private const val TEST_REWARDED_AD_ID = "ca-app-pub-3940256099942544/5224354917"
    
    private var mRewardedAd: RewardedAd?=null
    private var mRewardedAdController : RewardedAdController?=null


    fun loadRewardedAd(activity: Activity, adUnitId: String= TEST_REWARDED_AD_ID,rewardedAdController: RewardedAdController) {
        this.mRewardedAdController = rewardedAdController
        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(activity,adUnitId,adRequest,object : RewardedAdLoadCallback(){
            override fun onAdFailedToLoad(p0: LoadAdError) {
                Timber.d("onAdFailedToLoad(): ${p0.code}")
                mRewardedAd = null
            }

            override fun onAdLoaded(p0: RewardedAd) {
                mRewardedAd = p0
            }
        })

        mRewardedAd?.fullScreenContentCallback = this

        showRewardedAd(activity)
    }

    private fun showRewardedAd(activity: Activity) {
        if (mRewardedAd != null) {
            mRewardedAd?.show(activity) {
                val rewardAmount = it.amount
                val rewardType = it.type
                Timber.d("loadRewardedAd(): ${Gson().toJson(it)}")
            }
        } else {
            Timber.d("loadRewardedAd(): ad isn't ready")
            mRewardedAdController?.onAdUnready()
        }
    }

    override fun onAdClicked() {
        // Called when a click is recorded for an ad.
        Timber.d("Ad was clicked.")
    }

    override fun onAdDismissedFullScreenContent() {
        // Called when ad is dismissed.
        Timber.d("Ad dismissed fullscreen content.")
        mRewardedAd = null
        mRewardedAdController?.onAdDismissed()
    }

    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
        // Called when ad fails to show.
        Timber.e("Ad failed to show fullscreen content: ${adError.code}")
        mRewardedAd = null
    }

    override fun onAdImpression() {
        // Called when an impression is recorded for an ad.
        Timber.d("Ad recorded an impression.")
    }

    override fun onAdShowedFullScreenContent() {
        // Called when ad is shown.
        Timber.d("Ad showed fullscreen content.")
    }

    interface RewardedAdController {
        fun onAdDismissed()
        fun onAdUnready()
    }
    
}