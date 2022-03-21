package com.soumik.newsapp.base

import android.app.Application
import com.soumik.newsapp.di.component.AppComponent
import com.soumik.newsapp.di.component.DaggerAppComponent

/**
created by Soumik on 3/22/2022
piyal.developer@gmail.com
copyright (c) 2022 Soumik Bhattacharjee. All rights reserved
 **/

class NewsApp : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}