package com.soumik.newsapp.features.home.data.repository

import com.soumik.newsapp.base.network.NewsWebService
import com.soumik.newsapp.features.home.data.model.NewsModel
import com.soumik.newsapp.features.home.repository.HomeRepository
import io.reactivex.rxjava3.core.Flowable
import retrofit2.Response
import javax.inject.Inject

/**
created by Soumik on 3/22/2022
piyal.developer@gmail.com
copyright (c) 2022 Soumik Bhattacharjee. All rights reserved
 **/

class HomeRepositoryImpl @Inject constructor(private val newsWebService: NewsWebService): HomeRepository {
    override fun fetchTopHeadlines(country: String?): Flowable<Response<NewsModel>> {
        return newsWebService.fetchTopHeadlines(country=country)
    }
}