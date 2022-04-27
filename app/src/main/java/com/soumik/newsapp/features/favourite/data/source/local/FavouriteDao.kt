package com.soumik.newsapp.features.favourite.data.source.local

import androidx.room.*
import com.soumik.newsapp.features.favourite.domain.entity.Favourite
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

/**
created by Soumik on 10/4/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/
@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteNews(favourite: Favourite): Long

    @Query("SELECT * FROM tbl_favourite")
    fun getFavouriteNews() : Flow<List<Favourite>?>

    @Delete
    suspend fun deleteFavouriteNews(favourite: Favourite) : Int
}