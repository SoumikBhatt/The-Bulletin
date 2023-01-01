package com.soumik.newsapp.features.home.data.source.remote

import com.soumik.newsapp.core.utils.Constants
import com.soumik.newsapp.features.home.domain.model.NewsModel
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
created by Soumik on 10/4/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/
interface HomeWebService {

    @GET("top-headlines")
    suspend fun fetchTopHeadlines(
        @Query("country") country: String? = "us",
        @Query("apiKey") apiKey: String? = Constants.API_KEY,
        @Query("category") category: String?="",
        @Query("page") page: Int?=1,
        @Query("pageSize") pageSize: Int?=Constants.INITIAL_LOADING_ITEM_COUNT
    ): Response<NewsModel>
}