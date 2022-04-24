package com.soumik.newsapp.features.favourite.data.repository

import com.soumik.newsapp.core.utils.Constants
import com.soumik.newsapp.core.utils.Resource
import com.soumik.newsapp.features.favourite.data.source.local.FavouriteDao
import com.soumik.newsapp.features.favourite.domain.entity.Favourite
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
created by Soumik on 10/4/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/
class FavouriteRepositoryImpl @Inject constructor(private var favouriteDao: FavouriteDao): IFavouriteRepository {

    companion object {
        private const val TAG = "FavouriteRepositoryImpl"
    }

    override fun fetchFavouriteNews(): Flowable<List<Favourite>> {
        return favouriteDao.getFavouriteNews()
    }

    override fun insertFavouriteNews(favourite: Favourite): Single<Long> {
        return favouriteDao.insertFavouriteNews(favourite)
    }

    override fun deleteFavouriteNews(favourite: Favourite): Single<Int> {
        return favouriteDao.deleteFavouriteNews(favourite)
    }

    override suspend fun fetchFavouriteNewsCo(): Flow<Resource<List<Favourite>?>> = flow {
        Resource.loading<List<Favourite>>()
        val favourites = favouriteDao.getFavouriteNewsCo()

        if (favourites!=null && favourites.isNotEmpty()) {
            emit(Resource.success(favourites))
        } else {
            emit(Resource.failed(Constants.NO_ITEM_FOUND))
        }
    }
    override suspend fun insertFavouriteNewsCo(favourite: Favourite): Flow<Resource<Long>> = flow {
//        Resource.loading<Long>()
        emit(Resource.success(favouriteDao.insertFavouriteNewsCo(favourite)))
    }

    override suspend fun deleteFavouriteNewsCo(favourite: Favourite) = flow {
//        Resource.loading<Int>()
        emit(Resource.success(favouriteDao.deleteFavouriteNewsCo(favourite)))
    }
}