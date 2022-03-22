package com.soumik.newsapp.core.di.modules

import com.soumik.newsapp.core.network.NewsWebService
import com.soumik.newsapp.core.utils.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
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
    fun provideNewsWebService(retrofit: Retrofit): NewsWebService = retrofit.create(NewsWebService::class.java)

    @Singleton
    @Provides
    fun provideRetrofitClient() : Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
    }
}