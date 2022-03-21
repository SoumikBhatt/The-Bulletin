package com.soumik.newsapp.features.home.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soumik.newsapp.features.home.data.model.NewsModel
import com.soumik.newsapp.features.home.data.usecase.FetchTopHeadlineUseCase
import com.soumik.newsapp.utils.Status
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val fetchTopHeadlineUseCase: FetchTopHeadlineUseCase) :
    ViewModel() {

    private val _newsData: MutableLiveData<NewsModel> = MutableLiveData<NewsModel>()
    val newsData: LiveData<NewsModel> get() = _newsData

    private val _errorMessage : MutableLiveData<String> = MutableLiveData<String>()
    val errorMessage : LiveData<String> get() = _errorMessage

    private val _loading : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> get() = _loading


    fun fetchTopHeadlines(country:String?) {
        val resource = fetchTopHeadlineUseCase.fetchTopHeadlines(country)

        when(resource?.status) {
            Status.SUCCESS -> _newsData.value = resource.data!!
            Status.FAILED -> _errorMessage.value = resource.errorMessage!!
            else -> {}
        }
    }

    override fun onCleared() {
        fetchTopHeadlineUseCase.clear()
        super.onCleared()
    }

}