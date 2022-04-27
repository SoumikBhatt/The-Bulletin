package com.soumik.newsapp.features.favourite.data.repository

import android.util.Log
import com.soumik.newsapp.core.utils.Constants
import com.soumik.newsapp.core.utils.Resource
import com.soumik.newsapp.features.favourite.data.source.local.FavouriteDao
import com.soumik.newsapp.features.favourite.domain.entity.Favourite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
created by Soumik on 10/4/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/
class FavouriteRepositoryImpl @Inject constructor(private var favouriteDao: FavouriteDao) :
    IFavouriteRepository {

    companion object {
        private const val TAG = "FavouriteRepositoryImpl"
    }

    override suspend fun fetchFavouriteNews(): Flow<Resource<List<Favourite>?>> = flow {
        Resource.loading<List<Favourite>>()
        favouriteDao.getFavouriteNews().catch {
            Log.e(TAG, "fetchFavouriteNews: Exception: $this")
            emit(Resource.failed(Constants.ERROR_MESSAGE))
        }.collect { favourites ->
            if (favourites != null && favourites.isNotEmpty()) {
                emit(Resource.success(favourites))
            } else {
                emit(Resource.failed(Constants.NO_ITEM_FOUND))
            }
        }


    }

    override suspend fun insertFavouriteNews(favourite: Favourite): Flow<Resource<Long>> = flow {
        emit(Resource.success(favouriteDao.insertFavouriteNews(favourite)))
    }

    override suspend fun deleteFavouriteNews(favourite: Favourite) = flow {
        emit(Resource.success(favouriteDao.deleteFavouriteNews(favourite)))
    }
}