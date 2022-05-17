package com.soumik.newsapp.features.home.data.repository

import com.soumik.newsapp.core.utils.Resource
import com.soumik.newsapp.features.home.domain.model.NewsModel
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

/**
created by Soumik on 3/22/2022
piyal.developer@gmail.com
copyright (c) 2022 Soumik Bhattacharjee. All rights reserved
 **/

interface HomeRepository {
    suspend fun fetchTopHeadlines(country:String?="us", category:String?,page:Int?) : Flow<Resource<NewsModel>>
}