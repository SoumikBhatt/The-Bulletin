package com.soumik.newsapp.core.di.modules

import com.soumik.newsapp.core.network.NewsWebService
import com.soumik.newsapp.features.news_feed.data.repository.HomeRepository
import com.soumik.newsapp.features.news_feed.data.repository.HomeRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
created by Soumik on 22/3/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/

@Module
class HomeRepositoryModule {

    @Provides
    @Singleton
    fun provideHomeRepositoryImplementation(newsWebService: NewsWebService) : HomeRepository {
        return HomeRepositoryImpl(newsWebService)
    }
}