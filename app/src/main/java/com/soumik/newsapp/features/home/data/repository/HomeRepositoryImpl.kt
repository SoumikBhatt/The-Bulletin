package com.soumik.newsapp.features.home.data.repository

import android.util.Log
import com.soumik.newsapp.core.utils.Constants
import com.soumik.newsapp.core.utils.Resource
import com.soumik.newsapp.features.home.data.source.remote.HomeWebService
import com.soumik.newsapp.features.home.domain.model.NewsModel
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

/**
created by Soumik on 3/22/2022
piyal.developer@gmail.com
copyright (c) 2022 Soumik Bhattacharjee. All rights reserved
 **/

class HomeRepositoryImpl @Inject constructor(private val remoteService: HomeWebService):
    HomeRepository {

    companion object {
        private const val TAG = "HomeRepositoryImpl"
    }

    override fun fetchTopHeadlines(country: String?,category:String?): Flowable<Response<NewsModel>> {
        return remoteService.fetchTopHeadlines(country=country, category = category)
    }

    override fun fetchTopHeadlinesCo(
        country: String?,
        category: String?
    ): Flow<Resource<NewsModel>> = flow {
        Resource.loading<NewsModel>()
        remoteService.fetchTopHeadlinesCo(country=country,category=category).catch {
            Log.e(TAG, "fetchTopHeadlinesCo: Exception: $this")
            emit(Resource.failed(Constants.ERROR_MESSAGE))
        } .collect { response ->
            if (response.isSuccessful && response.code()==200 && response.body()!=null) {
                if (response.body()!!.articles!=null) {
                    if (response.body()!!.articles!!.isNotEmpty()) emit(Resource.success(response.body()))
                    else emit(Resource.failed(Constants.NO_ITEM_FOUND))
                } else emit(Resource.failed(Constants.ERROR_MESSAGE))
            } else {
                emit(Resource.failed(Constants.ERROR_MESSAGE))
            }
        }
    }
}