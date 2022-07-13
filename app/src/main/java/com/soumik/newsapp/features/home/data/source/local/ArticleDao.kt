package com.soumik.newsapp.features.home.data.source.local

import androidx.room.*
import com.soumik.newsapp.features.home.domain.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

/**
created by Soumik on 6/24/2022
piyal.developer@gmail.com
copyright (c) 2022 Soumik Bhattacharjee. All rights reserved
 **/

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticleList(articleList : List<ArticleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(articleEntity: ArticleEntity)

    @Query("SELECT * FROM TBL_ARTICLES WHERE page= :page")
    fun getArticles(page: Int) : Flow<List<ArticleEntity>>

    @Delete
    suspend fun deleteArticlesFromDB(articleEntities: List<ArticleEntity>)
}