package com.soumik.newsapp.features.home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soumik.newsapp.core.utils.Constants
import com.soumik.newsapp.core.utils.Status
import com.soumik.newsapp.features.favourite.data.repository.IFavouriteRepository
import com.soumik.newsapp.features.favourite.domain.entity.Favourite
import com.soumik.newsapp.features.favourite.presenter.FavouriteViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsDetailsViewModel @Inject constructor(private val favouriteRepository: IFavouriteRepository) : ViewModel() {
    companion object {
        private const val TAG = "NewsDetailsViewModel"
    }

    private val _errorMessage : MutableLiveData<String> = MutableLiveData<String>()
    val errorMessage : LiveData<String> get() = _errorMessage

    private val _loading : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> get() = _loading

    private val _isFavourite : MutableLiveData<Boolean> = MutableLiveData()
    val isFavourite : LiveData<Boolean> get() = _isFavourite

    fun insertFavouriteItem(favourite: Favourite) {
        viewModelScope.launch {
            favouriteRepository.insertFavouriteNews(favourite).catch {
                Log.e(TAG, "insertFavouriteItem: Exception: $this")
            }.collect {
                when(it.status) {
                    Status.LOADING -> _loading.value = true
                    Status.FAILED -> _errorMessage.value = Constants.NO_ITEM_FOUND
                    Status.SUCCESS -> {
                        _isFavourite.value = true
                        Log.d(TAG, "insertFavouriteItem: Success: ${it.data}")
                    }
                }
            }
        }
    }

    fun checkIfFavourite (title:String,category:String,author:String?) {
        viewModelScope.launch {
            favouriteRepository.checkIfFavourite(title, category, author).collect {
                when(it.status) {
                    Status.LOADING -> _loading.value = true
                    Status.FAILED -> _errorMessage.value = it.errorMessage!!
                    Status.SUCCESS -> _isFavourite.value = it.data == 1
                }
            }
        }
    }

    fun deleteFavouriteList(favourite: Favourite) {
        viewModelScope.launch {
            favouriteRepository.deleteFavourite(favourite.title!!,favourite.category!!,favourite.author).catch {
                Log.e(TAG, "deleteFavouriteList: Exception: $this")
            }.collect {
                when(it.status) {
                    Status.SUCCESS -> {
                        _isFavourite.value = false
                        Log.d(TAG, "deleteFavouriteList: Success: $it")
                    }
                    Status.FAILED -> _errorMessage.value = Constants.ERROR_MESSAGE
                    Status.LOADING -> _loading.value = true
                }
            }
        }
    }
}