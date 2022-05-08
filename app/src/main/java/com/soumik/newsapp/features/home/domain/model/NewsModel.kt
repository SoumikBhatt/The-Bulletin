package com.soumik.newsapp.features.home.domain.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsModel(
    @SerializedName("articles")
    var articles: List<Article>?=null,
    @SerializedName("status")
    var status: String?=null,
    @SerializedName("totalResults")
    var totalResults: Int?=0
):Parcelable