package com.soumik.newsapp.features.home.domain.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.soumik.newsapp.features.home.domain.entity.ArticleEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
    @SerializedName("author")
    var author: String?,
    @SerializedName("content")
    var content: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("publishedAt")
    var publishedAt: String?,
    @SerializedName("source")
    var source: Source?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("url")
    var url: String?,
    @SerializedName("urlToImage")
    var urlToImage: String?
):Parcelable