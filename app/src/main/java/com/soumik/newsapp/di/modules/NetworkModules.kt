package com.soumik.newsapp.di.modules

import com.soumik.newsapp.base.network.NewsWebService
import com.soumik.newsapp.utils.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
created by Soumik on 3/22/2022
piyal.developer@gmail.com
copyright (c) 2022 Soumik Bhattacharjee. All rights reserved
 **/

@Module
class NetworkModules {

    @Singleton
    @Provides
    fun provideNewsWebService(retrofit: Retrofit) = retrofit.create(NewsWebService::class.java)

    @Singleton
    @Provides
    fun provideRetrofitClient() : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
    }
}