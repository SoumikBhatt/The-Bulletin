package com.soumik.newsapp.features.news_feed.data.repository

import com.soumik.newsapp.features.news_feed.data.model.NewsModel
import io.reactivex.rxjava3.core.Flowable
import retrofit2.Response

/**
created by Soumik on 3/22/2022
piyal.developer@gmail.com
copyright (c) 2022 Soumik Bhattacharjee. All rights reserved
 **/

interface HomeRepository{
    fun fetchTopHeadlines(country:String?="us") : Flowable<Response<NewsModel>>
}