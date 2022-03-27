package com.soumik.newsapp.features.news_feed.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsModel(
    @SerializedName("articles")
    var articles: List<Article>?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("totalResults")
    var totalResults: Int?
):Parcelable