package com.soumik.newsapp.features.home.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.soumik.newsapp.core.utils.Constants
import com.soumik.newsapp.features.home.data.source.remote.HomeWebService
import com.soumik.newsapp.features.home.domain.model.Article
import com.soumik.newsapp.features.home.domain.model.NewsModel
import javax.inject.Inject

/**
created by Soumik on 17/5/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/
class TopHeadlinesPagingSource @Inject constructor(private val remoteService: HomeWebService) :
    PagingSource<Int, Article>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: Constants.DEFAULT_PAGE_NUM

        return try {
            val response = remoteService.fetchTopHeadlinesPaging(page = page, category = "sports")
            LoadResult.Page(
                response,
                prevKey = if (page == Constants.DEFAULT_PAGE_NUM) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)

        }
    }

}