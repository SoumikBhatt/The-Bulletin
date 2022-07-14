package com.soumik.newsapp.core.di.modules

import android.content.Context
import com.soumik.newsapp.features.home.presentation.newsfeed.ui.NewsListPagingAdapter
import dagger.Module
import dagger.Provides

/**
created by Soumik on 14/7/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/

@Module
class AdapterModule {

    @Provides
    fun provideNewsListAdapter(context: Context) : NewsListPagingAdapter {
        return NewsListPagingAdapter(context)
    }
}