package com.soumik.newsapp.features.home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.soumik.newsapp.core.utils.Constants
import com.soumik.newsapp.core.utils.IConnectivity
import com.soumik.newsapp.core.utils.Status
import com.soumik.newsapp.features.favourite.data.repository.IFavouriteRepository
import com.soumik.newsapp.features.favourite.domain.entity.Favourite
import com.soumik.newsapp.features.home.data.repository.HomeRepository
import com.soumik.newsapp.features.home.domain.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsFeedViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val connectivity: IConnectivity,
    private val favouriteRepository: IFavouriteRepository
) :
    ViewModel() {

    companion object {
        private const val TAG = "NewsFeedViewModel"
    }

    private val _newsData: MutableLiveData<List<Article>> = MutableLiveData<List<Article>>()
    val newsData: LiveData<List<Article>> get() = _newsData

    private val _errorMessage: MutableLiveData<String> = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _loading: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun fetchTopHeadlines(country: String?, category: String?, page: Int?) {
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

    fun fetchTopHeadlinesPage(
        country: String?,
        category: String?,
        page: Int?
    ): Flow<PagingData<Article>> {

        return homeRepository.fetchTopHeadlinesPaged(country, category, page).map {
            it.map { it }
        }.cachedIn(viewModelScope)
    }

    fun insertFavouriteItem(favourite: Favourite) {
        viewModelScope.launch {
            favouriteRepository.insertFavouriteNews(favourite).catch {
                Log.e(TAG, "insertFavouriteItem: Exception: $this")
            }.collect {
                when (it.status) {
                    Status.LOADING -> _loading.value = true
                    Status.FAILED -> _errorMessage.value = Constants.NO_ITEM_FOUND
                    Status.SUCCESS -> Log.d(TAG, "insertFavouriteItem: Success: ${it.data}")
                }
            }
        }
    }

}