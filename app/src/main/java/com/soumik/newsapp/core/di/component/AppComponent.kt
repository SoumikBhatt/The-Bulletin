package com.soumik.newsapp.core.di.component

import android.content.Context
import com.soumik.newsapp.core.di.modules.ConnectivityModule
import com.soumik.newsapp.core.di.modules.HomeRepositoryModule
import com.soumik.newsapp.core.di.modules.NetworkModules
import com.soumik.newsapp.features.home.presentation.ui.NewsFeedFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
created by Soumik on 3/22/2022
piyal.developer@gmail.com
copyright (c) 2022 Soumik Bhattacharjee. All rights reserved
 **/

@Singleton
@Component(modules = [NetworkModules::class,HomeRepositoryModule::class,ConnectivityModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(newsFeedFragment: NewsFeedFragment)

}