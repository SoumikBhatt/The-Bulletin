package com.soumik.newsapp.features.news_feed.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Source(
    @SerializedName("id")
    var id: String?,
    @SerializedName("name")
    var name: String?
):Parcelable