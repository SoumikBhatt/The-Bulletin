package com.soumik.newsapp.core.di.modules

import com.soumik.newsapp.core.network.NewsWebService
import com.soumik.newsapp.core.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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
    fun provideRetrofitClient(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient():OkHttpClient {
        return OkHttpClient
            .Builder()
            .connectTimeout(2000,TimeUnit.MILLISECONDS)
            .readTimeout(2000,TimeUnit.MILLISECONDS)
            .build()
    }
}