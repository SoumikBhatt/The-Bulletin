package com.soumik.newsapp.features.home.data.repository

import com.soumik.newsapp.features.home.data.source.remote.HomeWebService
import com.soumik.newsapp.features.home.domain.model.NewsModel
import io.reactivex.rxjava3.core.Flowable
import retrofit2.Response
import javax.inject.Inject

/**
created by Soumik on 3/22/2022
piyal.developer@gmail.com
copyright (c) 2022 Soumik Bhattacharjee. All rights reserved
 **/

class HomeRepositoryImpl @Inject constructor(private val remoteService: HomeWebService):
    HomeRepository {
    override fun fetchTopHeadlines(country: String?,category:String?): Flowable<Response<NewsModel>> {
        return remoteService.fetchTopHeadlines(country=country, category = category)
    }
}