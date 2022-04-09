package com.soumik.newsapp.features.favourite.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
created by Soumik on 10/4/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/

@Entity(
    tableName = "tbl_favourite"
)
data class Favourite(
    @PrimaryKey(autoGenerate = true)
    var id : Int?,
    @ColumnInfo(name = "author")
    var author: String?,
    @ColumnInfo(name = "content")
    var content: String?,
    @ColumnInfo(name = "description")
    var description: String?,
    @ColumnInfo(name = "publishedAt")
    var publishedAt: String?,
    @ColumnInfo(name = "title")
    var title: String?,
    @ColumnInfo(name = "url")
    var url: String?,
    @ColumnInfo(name = "urlToImage")
    var urlToImage: String?,
    @ColumnInfo(name = "category")
    var category: String? = ""
)