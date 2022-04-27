package com.soumik.newsapp.features.favourite.presenter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soumik.newsapp.core.utils.Constants
import com.soumik.newsapp.core.utils.Status
import com.soumik.newsapp.features.favourite.data.repository.IFavouriteRepository
import com.soumik.newsapp.features.favourite.domain.entity.Favourite
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteViewModel @Inject constructor(private val favouriteRepository: IFavouriteRepository) : ViewModel() {

    companion object {
        private const val TAG = "FavouriteViewModel"
    }

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _favouriteList = MutableLiveData<List<Favourite>>()
    val favouriteList:LiveData<List<Favourite>> = _favouriteList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String> = _errorMessage


    fun fetchFavouriteList() {
        viewModelScope.launch {
            favouriteRepository.fetchFavouriteNews().catch {
                Log.e(TAG, "fetchFavouriteListCo: Exception: $this")
            }.collect {
                when(it.status) {
                    Status.LOADING -> _loading.value = true
                    Status.SUCCESS -> _favouriteList.value = it.data!!
                    Status.FAILED -> _errorMessage.value = it.errorMessage!!
                }
            }
        }
    }

    fun deleteFavouriteList(favourite: Favourite) {
        viewModelScope.launch {
            favouriteRepository.deleteFavouriteNews(favourite).catch {
                Log.e(TAG, "deleteFavouriteListCo: Exception: $this")
            }.collect {
                when(it.status) {
                    Status.SUCCESS -> Log.d(TAG, "deleteFavouriteListCo: Success: $it")
                    Status.FAILED -> _errorMessage.value = Constants.ERROR_MESSAGE
                    Status.LOADING -> _loading.value = true
                }
            }
        }
    }

}