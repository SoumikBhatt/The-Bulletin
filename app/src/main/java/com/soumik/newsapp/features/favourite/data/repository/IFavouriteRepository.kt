package com.soumik.newsapp.features.favourite.data.repository

import com.soumik.newsapp.core.utils.Resource
import com.soumik.newsapp.features.favourite.domain.entity.Favourite
import kotlinx.coroutines.flow.Flow

/**
created by Soumik on 10/4/22.
soumik.nybsys@gmail.com
Copyright (c) 2022 NybSys. All rights reserved
 **/
interface IFavouriteRepository {
    suspend fun fetchFavouriteNews() : Flow<Resource<List<Favourite>?>>
    suspend fun insertFavouriteNews(favourite: Favourite) : Flow<Resource<Long>>
    suspend fun deleteFavouriteNews(favourite: Favourite) : Flow<Resource<Int>>

}