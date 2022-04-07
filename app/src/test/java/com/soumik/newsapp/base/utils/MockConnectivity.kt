package com.soumik.newsapp.base.utils

import com.soumik.newsapp.core.utils.IConnectivity

/**
created by Soumik on 4/6/2022
piyal.developer@gmail.com
copyright (c) 2022 Soumik Bhattacharjee. All rights reserved
 **/

class MockConnectivity : IConnectivity {
    override fun hasInternetConnection(): Boolean {
        return true
    }
}