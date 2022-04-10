package com.soumik.newsapp.features.home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soumik.newsapp.core.utils.Constants
import com.soumik.newsapp.core.utils.IConnectivity
import com.soumik.newsapp.features.favourite.data.repository.IFavouriteRepository
import com.soumik.newsapp.features.favourite.domain.entity.Favourite
import com.soumik.newsapp.features.home.data.repository.HomeRepository
import com.soumik.newsapp.features.home.domain.model.Article
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class NewsFeedViewModel @Inject constructor(private val homeRepository : HomeRepository,private val connectivity: IConnectivity,private val favouriteRepository: IFavouriteRepository) :
    ViewModel() {

    companion object {
        private const val TAG = "NewsFeedViewModel"
    }

    private val compositeDisposable = CompositeDisposable()

    private val _newsData: MutableLiveData<List<Article>> = MutableLiveData<List<Article>>()
    val newsData: LiveData<List<Article>> get() = _newsData

    private val _errorMessage : MutableLiveData<String> = MutableLiveData<String>()
    val errorMessage : LiveData<String> get() = _errorMessage

    private val _loading : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> get() = _loading

    private val _favouriteItemInserted = MutableLiveData<Boolean>()
    val favouriteItemInserted : LiveData<Boolean> = _favouriteItemInserted

    fun fetchTopHeadlines(country:String?,category:String?) {
        if (connectivity.hasInternetConnection()) {
            _loading.value = true
            compositeDisposable.add(
                homeRepository.fetchTopHeadlines(country,category).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            _loading.value = false
                            if (it.isSuccessful && it.code()==200 && it.body() != null ) {
                                if (it.body()!!.articles!=null) {
                                    if (it.body()!!.articles!!.isNotEmpty()) _newsData.value = it.body()!!.articles!!
                                    else _errorMessage.value = Constants.NO_ITEM_FOUND
                                } else _errorMessage.value = Constants.ERROR_MESSAGE
                            } else {
                                _errorMessage.value = Constants.ERROR_MESSAGE
                            }
                        }, {
                            it.printStackTrace()
                            _loading.value = false
                            _errorMessage.value = it.message
                        }
                    )
            )
        } else {
            _loading.value=false
            _errorMessage.value = Constants.NO_NETWORK_CONNECTION
        }

    }

    fun insertFavouriteItem(favourite: Favourite) {
        compositeDisposable.add(
            favouriteRepository.insertFavouriteNews(favourite).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io()).subscribe({
                    Log.d(TAG, "insertFavouriteItem: Success: $it")
                },{
                    _errorMessage.value=Constants.NO_ITEM_FOUND
                })
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }

}