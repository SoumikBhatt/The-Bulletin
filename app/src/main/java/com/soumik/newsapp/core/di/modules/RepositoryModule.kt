package com.soumik.newsapp.core.di.modules

import com.soumik.newsapp.core.persistance.AppDatabase
import com.soumik.newsapp.features.favourite.data.repository.FavouriteRepositoryImpl
import com.soumik.newsapp.features.favourite.data.repository.IFavouriteRepository
import com.soumik.newsapp.features.favourite.data.source.local.FavouriteDao
import com.soumik.newsapp.features.home.data.repository.HomeRepository
import com.soumik.newsapp.features.home.data.repository.HomeRepositoryImpl
import com.soumik.newsapp.features.home.data.source.remote.HomeWebService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
created by Soumik on 22/3/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideHomeRepositoryImplementation(remoteService: HomeWebService) : HomeRepository {
        return HomeRepositoryImpl(remoteService)
    }

    @Provides
    @Singleton
    fun provideFavouriteRepositoryImplementation(favouriteDao: FavouriteDao) : IFavouriteRepository {
        return FavouriteRepositoryImpl(favouriteDao)
    }
}