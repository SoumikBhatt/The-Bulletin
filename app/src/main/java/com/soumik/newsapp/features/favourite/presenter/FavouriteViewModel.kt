package com.soumik.newsapp.features.favourite.presenter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soumik.newsapp.core.utils.Constants
import com.soumik.newsapp.features.favourite.data.repository.IFavouriteRepository
import com.soumik.newsapp.features.favourite.domain.entity.Favourite
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class FavouriteViewModel @Inject constructor(private val favouriteRepository: IFavouriteRepository) : ViewModel() {

    companion object {
        private const val TAG = "FavouriteViewModel"
    }

    private val compositeDisposable = CompositeDisposable()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _favouriteList = MutableLiveData<List<Favourite>>()
    val favouriteList:LiveData<List<Favourite>> = _favouriteList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String> = _errorMessage


    fun fetchFavouriteList() {
        _loading.value = true
        compositeDisposable.add(
            favouriteRepository.fetchFavouriteNews().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _loading.value = false
                    if (it.isNotEmpty()) _favouriteList.value = it
                    else _errorMessage.value = Constants.NO_ITEM_FOUND
                },{
                    it.printStackTrace()
                    _loading.value = false
                    _errorMessage.value = Constants.ERROR_MESSAGE
                })
        )
    }

    fun deleteFavouriteList(favourite: Favourite) {
        compositeDisposable.add(
            favouriteRepository.deleteFavouriteNews(favourite).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io()).subscribe({
                    Log.d(TAG, "deleteFavouriteList: Success: $it")
                },{
                    it.printStackTrace()
                    _errorMessage.value=Constants.ERROR_MESSAGE
                })
        )
    }

    override fun onCleared() {
        compositeDisposable.apply {
            dispose()
            clear()
        }
        super.onCleared()
    }
}