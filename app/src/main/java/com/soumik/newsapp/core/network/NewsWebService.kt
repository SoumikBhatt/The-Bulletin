package com.soumik.newsapp.core.network

import com.soumik.newsapp.features.home.domain.model.NewsModel
import com.soumik.newsapp.core.utils.Constants
import io.reactivex.rxjava3.core.Flowable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
created by Soumik on 3/22/2022
piyal.developer@gmail.com
copyright (c) 2022 Soumik Bhattacharjee. All rights reserved
 **/

interface NewsWebService {

    @GET("top-headlines")
    fun fetchTopHeadlines(
        @Query("country") country: String? = "us",
        @Query("apiKey") apiKey: String? = Constants.API_KEY,
        @Query("category") category: String?=""
    ): Flowable<Response<NewsModel>>
}