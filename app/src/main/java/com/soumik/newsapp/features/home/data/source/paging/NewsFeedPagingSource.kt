package com.soumik.newsapp.features.home.data.source.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.soumik.newsapp.core.utils.Constants
import com.soumik.newsapp.features.home.data.source.local.ArticleDao
import com.soumik.newsapp.features.home.data.source.remote.HomeWebService
import com.soumik.newsapp.features.home.domain.model.Article
import com.soumik.newsapp.features.home.domain.model.asDatabaseModel
import retrofit2.HttpException
import java.io.IOException

/**
created by Soumik on 5/6/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/

class NewsFeedPagingSource(
    private val homeWebService: HomeWebService,
    private val articleDao: ArticleDao,
    private val category: String,
    private val country: String
) : PagingSource<Int, Article>() {

    companion object {
        private const val STARTING_PAGE_NUMBER = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: STARTING_PAGE_NUMBER
        return try {
            val response = homeWebService.fetchTopHeadlines(
                country = country,
                category = category,
                page = page
            )
            if (response.isSuccessful && response.code() == 200 && response.body() != null) {
                if (response.body()!!.articles != null) {
                    val article = response.body()!!.articles!!
                    Log.d("TAG", "load: ${article.size}")
                    articleDao.insertArticleList(response.body()?.asDatabaseModel(page)!!)
                    LoadResult.Page(
                        data = article,
                        nextKey = if (article.isNotEmpty()) page + 1 else null,
                        prevKey = if (page == STARTING_PAGE_NUMBER) null else page - 1
                    )
                } else LoadResult.Error(Throwable(Constants.ERROR_MESSAGE))
            } else LoadResult.Error(Throwable("${response.code()}"))
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}