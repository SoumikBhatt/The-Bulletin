package com.soumik.newsapp.core.di.modules

import android.content.Context
import com.soumik.newsapp.core.utils.Connectivity
import com.soumik.newsapp.core.utils.IConnectivity
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
created by Soumik on 4/6/2022
piyal.developer@gmail.com
copyright (c) 2022 Soumik Bhattacharjee. All rights reserved
 **/

@Module
class ConnectivityModule {

    @Singleton
    @Provides
    fun provideConnectivityImpl(context: Context) : IConnectivity {
        return Connectivity(context)
    }

}