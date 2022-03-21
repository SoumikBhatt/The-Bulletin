package com.soumik.newsapp.di.component

import android.content.Context
import com.soumik.newsapp.di.modules.NetworkModules
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
created by Soumik on 3/22/2022
piyal.developer@gmail.com
copyright (c) 2022 Soumik Bhattacharjee. All rights reserved
 **/

@Singleton
@Component(modules = [NetworkModules::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }


}