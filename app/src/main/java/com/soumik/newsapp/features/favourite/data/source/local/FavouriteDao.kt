package com.soumik.newsapp.features.favourite.data.source.local

import androidx.room.*
import com.soumik.newsapp.features.favourite.domain.entity.Favourite
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

/**
created by Soumik on 10/4/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/
@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavouriteNews(favourite: Favourite): Single<Long>

    @Query("SELECT * FROM tbl_favourite")
    fun getFavouriteNews() : Flowable<List<Favourite>>

    @Delete
    fun deleteFavouriteNews(favourite: Favourite) : Single<Int>
}