package com.soumik.newsapp.features.home.domain.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.soumik.newsapp.features.home.domain.entity.ArticleEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsModel(
    @SerializedName("articles")
    var articles: List<Article>?,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("totalResults")
    var totalResults: Int? = 0
) : Parcelable

fun NewsModel.asDatabaseModel(page: Int): List<ArticleEntity>? {
    return articles?.map {
        ArticleEntity(
            author = it.author,
            content = it.content,
            description = it.description,
            publishedAt = it.publishedAt,
            sourceId = it.source?.id,
            sourceName = it.source?.name,
            title = it.title,
            url = it.url,
            urlToImage = it.urlToImage,
            page = page
        )
    }
}