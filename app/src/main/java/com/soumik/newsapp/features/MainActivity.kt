package com.soumik.newsapp.features

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.MobileAds
import com.soumik.newsapp.core.SharedViewModel
import com.soumik.newsapp.core.utils.EventObserver
import com.soumik.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityMainBinding
    private val sharedViewModel : SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mBinding.root
        setContentView(view)
        val navController = mBinding.fragmentContainerView.getFragment<NavHostFragment>().navController

        mBinding.btmNavHome.setupWithNavController(navController)

        MobileAds.initialize(this) {}

        setViews()
    }

    private fun setViews() {
        sharedViewModel.apply {
            showBottomNav.observe(this@MainActivity,EventObserver {
                if (it) {
                    mBinding.btmNavHome.visibility=View.VISIBLE
                } else {
                    mBinding.btmNavHome.visibility=View.GONE
                }
            })
        }
    }
}