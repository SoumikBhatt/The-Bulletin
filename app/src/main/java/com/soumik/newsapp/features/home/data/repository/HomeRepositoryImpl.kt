package com.soumik.newsapp.features.home.data.repository

import com.soumik.newsapp.core.utils.Constants
import com.soumik.newsapp.core.utils.Resource
import com.soumik.newsapp.features.home.data.source.remote.HomeWebService
import com.soumik.newsapp.features.home.domain.model.NewsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
created by Soumik on 3/22/2022
piyal.developer@gmail.com
copyright (c) 2022 Soumik Bhattacharjee. All rights reserved
 **/

class HomeRepositoryImpl @Inject constructor(private val remoteService: HomeWebService) :
    HomeRepository {

    override suspend fun fetchTopHeadlines(
        country: String?,
        category: String?,
        page: Int?
    ): Flow<Resource<NewsModel>> = flow {
        emit(Resource.loading())
        try {
            val response = remoteService.fetchTopHeadlines(country = country, category = category, page = page)
            if (response.isSuccessful && response.code() == 200 && response.body() != null) {
                if (response.body()!!.articles != null) {
                    if (response.body()!!.articles!!.isNotEmpty()) {
                        emit(Resource.success(response.body()))
                    } else emit(Resource.failed(Constants.NO_ITEM_FOUND))
                } else emit(Resource.failed(Constants.ERROR_MESSAGE))
            } else {
                emit(Resource.failed(Constants.ERROR_MESSAGE))
            }
        } catch (e: Exception) {
            emit(Resource.failed(Constants.ERROR_MESSAGE))
        }
    }
}