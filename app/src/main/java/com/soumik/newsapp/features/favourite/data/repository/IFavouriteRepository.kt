package com.soumik.newsapp.features.favourite.data.repository

import com.soumik.newsapp.features.favourite.domain.entity.Favourite
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

/**
created by Soumik on 10/4/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/
interface IFavouriteRepository {

    fun fetchFavouriteNews(): Flowable<List<Favourite>>
    fun insertFavouriteNews(favourite: Favourite): Single<Long>
    fun deleteFavouriteNews(favourite: Favourite) : Single<Int>
}