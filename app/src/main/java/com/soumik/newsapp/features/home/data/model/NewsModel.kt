package com.soumik.newsapp.features.home.data.model


import com.google.gson.annotations.SerializedName

data class NewsModel(
    @SerializedName("articles")
    var articles: List<Article>?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("totalResults")
    var totalResults: Int?
)