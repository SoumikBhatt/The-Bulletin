package com.soumik.newsapp.features.home.data.repository

import androidx.paging.PagingData
import com.soumik.newsapp.features.home.domain.model.Article
import kotlinx.coroutines.flow.Flow

/**
created by Soumik on 3/22/2022
piyal.developer@gmail.com
copyright (c) 2022 Soumik Bhattacharjee. All rights reserved
 **/

interface HomeRepository {
    fun fetchTopHeadlines(country:String?="us", category:String?, page:Int) : Flow<PagingData<Article>>
}