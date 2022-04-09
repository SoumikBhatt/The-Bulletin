package com.soumik.newsapp.core.di.modules

import android.content.Context
import androidx.room.Room
import com.soumik.newsapp.core.persistance.AppDatabase
import com.soumik.newsapp.features.favourite.data.source.local.FavouriteDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
created by Soumik on 10/4/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/

@Module
class StorageModule {

    @Singleton
    @Provides
    fun provideAppDB(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "${context.packageName}_db")
            .build()
    }

    @Singleton
    @Provides
    fun provideFavouriteDao(database: AppDatabase) : FavouriteDao {
        return database.getFavouriteDao()
    }
}