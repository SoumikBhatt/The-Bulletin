package com.soumik.newsapp.features.news_feed.domain.usecase

import com.soumik.newsapp.features.news_feed.data.model.NewsModel
import com.soumik.newsapp.features.news_feed.data.repository.HomeRepository
import io.reactivex.rxjava3.core.Flowable
import retrofit2.Response
import javax.inject.Inject

/**
created by Soumik on 3/22/2022
piyal.developer@gmail.com
copyright (c) 2022 Soumik Bhattacharjee. All rights reserved
 **/

class FetchTopHeadlineUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    fun fetchTopHeadlines(country: String?): Flowable<Response<NewsModel>> {
        return homeRepository.fetchTopHeadlines(country)
    }
}