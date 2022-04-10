package com.soumik.newsapp.features.favourite.data.repository

import com.soumik.newsapp.features.favourite.data.source.local.FavouriteDao
import com.soumik.newsapp.features.favourite.domain.entity.Favourite
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
created by Soumik on 10/4/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/
class FavouriteRepositoryImpl @Inject constructor(private var favouriteDao: FavouriteDao): IFavouriteRepository {
    override fun fetchFavouriteNews(): Flowable<List<Favourite>> {
        return favouriteDao.getFavouriteNews()
    }

    override fun insertFavouriteNews(favourite: Favourite): Single<Long> {
        return favouriteDao.insertFavouriteNews(favourite)
    }

    override fun deleteFavouriteNews(favourite: Favourite): Single<Int> {
        return favouriteDao.deleteFavouriteNews(favourite)
    }
}