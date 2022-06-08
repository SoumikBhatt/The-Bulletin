package com.soumik.newsapp.core.di.modules

import android.content.Context
import com.soumik.newsapp.core.network.HttpRequestInterceptor
import com.soumik.newsapp.core.utils.Constants
import com.soumik.newsapp.core.utils.IConnectivity
import com.soumik.newsapp.features.home.data.source.remote.HomeWebService
import dagger.Module
import dagger.Provides
import okhttp3.Cache
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
    fun provideHomeWebService(retrofit: Retrofit): HomeWebService =
        retrofit.create(HomeWebService::class.java)

    @Singleton
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideCache(context: Context): Cache {
        val cacheSize = (5 * 1024 * 1024).toLong()
        return Cache(context.cacheDir, cacheSize)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(cache: Cache, connectivity: IConnectivity): OkHttpClient {
        return OkHttpClient
            .Builder()
            .cache(cache)
            .addInterceptor(HttpRequestInterceptor())
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (connectivity.hasInternetConnection()) {
                    /*
                       *  If there is Internet, get the cache that was stored 5 seconds ago.
                       *  If the cache is older than 5 seconds, then discard it,
                       *  and indicate an error in fetching the response.
                       *  The 'max-age' attribute is responsible for this behavior.
                       */
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                } else {
                    /*
                        *  If there is no Internet, get the cache that was stored 7 days ago.
                        *  If the cache is older than 7 days, then discard it,
                        *  and indicate an error in fetching the response.
                        *  The 'max-stale' attribute is responsible for this behavior.
                        *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
                        */
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                    ).build()
                }
                val response = chain.proceed(request)
                if (response.code() == 500) {
                    throw Exception(Constants.INTERNAL_SERVER_ERROR)
                } else if (response.code() == 404) {
                    throw Exception(Constants.URL_ERROR)
                }
                response
            }
            .connectTimeout(2000, TimeUnit.MILLISECONDS)
            .readTimeout(2000, TimeUnit.MILLISECONDS)
            .build()
    }
}