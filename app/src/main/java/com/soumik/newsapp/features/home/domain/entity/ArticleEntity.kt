package com.soumik.newsapp.features.home.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_articles")
data class ArticleEntity(
    @PrimaryKey (autoGenerate = true)
    var id: Long=0,
    var author: String?,
    var content: String?,
    var description: String?,
    var publishedAt: String?,
    var sourceId: String?,
    var sourceName: String?,
    var title: String?,
    var url: String?,
    var urlToImage: String?,
    var page: Int?
)
