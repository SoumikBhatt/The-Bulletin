package com.soumik.newsapp.features.home.data.usecase

import com.soumik.newsapp.features.home.data.model.NewsModel
import com.soumik.newsapp.features.home.repository.HomeRepository
import com.soumik.newsapp.utils.Constants
import com.soumik.newsapp.utils.Resource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
created by Soumik on 3/22/2022
piyal.developer@gmail.com
copyright (c) 2022 Soumik Bhattacharjee. All rights reserved
 **/

class FetchTopHeadlineUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    private val compositeDisposable = CompositeDisposable()

    fun fetchTopHeadlines(country: String?): Resource<NewsModel>? {
        var response: Resource<NewsModel>? = null
        compositeDisposable.add(
            homeRepository.fetchTopHeadlines(country).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                    {
                        response = if (it.isSuccessful && it.body() != null && it.code()==200) {
                            Resource.success(it.body())
                        } else {
                            Resource.failed(Constants.ERROR_MESSAGE)
                        }
                    }, {
                        it.stackTrace
                        response = Resource.failed(it.localizedMessage)
                    }
                )
        )

        return response
    }

    fun clear() {
        compositeDisposable.clear()
    }
}