package com.soumik.newsapp.features.home.presentation.newsfeed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.soumik.newsapp.core.utils.Constants
import com.soumik.newsapp.core.utils.IConnectivity
import com.soumik.newsapp.core.utils.Status
import com.soumik.newsapp.features.home.data.repository.HomeRepository
import com.soumik.newsapp.features.home.domain.model.Article
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsFeedViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val connectivity: IConnectivity
) :
    ViewModel() {

    companion object {
        private const val TAG = "NewsFeedViewModel"
    }

    private val _newsData: MutableLiveData<List<Article>> = MutableLiveData<List<Article>>()
    val newsData: LiveData<List<Article>> get() = _newsData

    private val _pagedNewsData: MutableLiveData<PagingData<Article>> =
        MutableLiveData<PagingData<Article>>()
    val pagedNewsData: LiveData<PagingData<Article>> get() = _pagedNewsData

    private val _errorMessage: MutableLiveData<String> = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _loading: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun fetchTopHeadlines(country: String?, category: String?, page: Int) {
        if (connectivity.hasInternetConnection()) {
            viewModelScope.launch {
                homeRepository.fetchTopHeadlines(country, category, page).catch {
                    _errorMessage.value = Constants.ERROR_MESSAGE
                }.collect {
                    when (it.status) {
                        Status.SUCCESS -> {
                            _loading.value = false
                            _newsData.value = it.data!!.articles!!
                        }
                        Status.FAILED -> {
                            _loading.value = false
                            _errorMessage.value = it.errorMessage!!
                        }
                        Status.LOADING -> _loading.value = true
                    }
                }
            }
        } else {
            _errorMessage.value = Constants.NO_NETWORK_CONNECTION
        }
    }

    fun fetchPagedTopHeadlines(country: String?, category: String?, page: Int) {
        if (connectivity.hasInternetConnection()) {
            _loading.value = true
            viewModelScope.launch {
                homeRepository.fetchPagedTopHeadlines(country, category, page).catch {
                    _loading.value = false
                    _errorMessage.value = Constants.ERROR_MESSAGE
                }.collectLatest {
                    _pagedNewsData.value = it
                    _loading.value = false
                }
            }
        } else {
            _loading.value = false
            _errorMessage.value = Constants.NO_NETWORK_CONNECTION
        }
    }
}