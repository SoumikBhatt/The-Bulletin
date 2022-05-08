package com.soumik.newsapp.core.di.modules

import com.soumik.newsapp.core.network.HttpRequestInterceptor
import com.soumik.newsapp.core.utils.Constants
import com.soumik.newsapp.features.home.data.source.remote.HomeWebService
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
    fun provideHomeWebService(retrofit: Retrofit): HomeWebService = retrofit.create(HomeWebService::class.java)

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
            .addInterceptor(HttpRequestInterceptor())
            .addInterceptor { chain ->
                val request =  chain.request()
                val response = chain.proceed(request)
                if (response.code()==500) {
                    throw Exception(Constants.INTERNAL_SERVER_ERROR)
                } else if (response.code()==404) {
                    throw Exception(Constants.URL_ERROR)
                }
                response
            }
            .connectTimeout(2000,TimeUnit.MILLISECONDS)
            .readTimeout(2000,TimeUnit.MILLISECONDS)
            .build()
    }
}