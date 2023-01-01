package com.soumik.newsapp.core.network

import android.util.Log
import com.google.gson.Gson
import com.soumik.newsapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
created by Soumik on 8/5/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/
class HttpRequestInterceptor : Interceptor {
    
    companion object {
        private const val TAG = "HttpRequestInterceptor"
    }
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder().url(request.url()).build()
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "intercept: Request: $requestBuilder")
        }
        val response = chain.proceed(requestBuilder)
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "intercept: Response: ${Gson().toJson(response.code())}")
        }

        return response
    }
}