package com.soumik.newsapp.core.utils

/**
created by Soumik on 3/22/2022
piyal.developer@gmail.com
copyright (c) 2022 Soumik Bhattacharjee. All rights reserved
 **/

data class Resource<T> (val status: Status,val data: T? = null,val errorMessage:String?=null) {
    companion object {
        fun <T> success(data:T?) : Resource<T> = Resource(status = Status.SUCCESS,data=data)
        fun <T> failed(message:String?) : Resource<T> = Resource(status = Status.FAILED, errorMessage = message)
        fun <T> loading() : Resource<T> = Resource(status = Status.LOADING)
    }
}