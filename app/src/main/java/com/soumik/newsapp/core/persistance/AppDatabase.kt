package com.soumik.newsapp.core.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import com.soumik.newsapp.features.favourite.data.source.local.FavouriteDao
import com.soumik.newsapp.features.favourite.domain.entity.Favourite
import com.soumik.newsapp.features.home.data.source.local.ArticleDao
import com.soumik.newsapp.features.home.domain.entity.ArticleEntity

/**
created by Soumik on 10/4/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/

@Database(entities = [Favourite::class,ArticleEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getFavouriteDao(): FavouriteDao
    abstract fun getArticleDao(): ArticleDao
}