package com.soumik.newsapp.core.di.component

import android.content.Context
import com.soumik.newsapp.core.di.modules.ConnectivityModule
import com.soumik.newsapp.core.di.modules.RepositoryModule
import com.soumik.newsapp.core.di.modules.NetworkModules
import com.soumik.newsapp.core.di.modules.StorageModule
import com.soumik.newsapp.features.favourite.presenter.FavouriteFragment
import com.soumik.newsapp.features.home.presentation.newsDetails.ui.NewsDetailsFragment
import com.soumik.newsapp.features.home.presentation.newsfeed.ui.NewsFeedFragment
import com.soumik.newsapp.features.settings.presenter.ui.SettingsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
created by Soumik on 3/22/2022
piyal.developer@gmail.com
copyright (c) 2022 Soumik Bhattacharjee. All rights reserved
 **/

@Singleton
@Component(modules = [NetworkModules::class, RepositoryModule::class, ConnectivityModule::class, StorageModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(newsFeedFragment: NewsFeedFragment)
    fun inject (favouriteFragment: FavouriteFragment)
    fun inject(newsDetailsFragment: NewsDetailsFragment)
    fun inject(settingsFragment: SettingsFragment)

}