package com.soumik.newsapp.features.news_feed.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soumik.newsapp.core.utils.Connectivity
import com.soumik.newsapp.core.utils.Constants
import com.soumik.newsapp.features.news_feed.data.model.NewsModel
import com.soumik.newsapp.features.news_feed.domain.usecase.FetchTopHeadlineUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val fetchTopHeadlineUseCase: FetchTopHeadlineUseCase) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _newsData: MutableLiveData<NewsModel?> = MutableLiveData<NewsModel?>()
    val newsData: LiveData<NewsModel?> get() = _newsData

    private val _errorMessage : MutableLiveData<String> = MutableLiveData<String>()
    val errorMessage : LiveData<String> get() = _errorMessage

    private val _loading : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> get() = _loading

    fun fetchTopHeadlines(country:String?) {
        _loading.value = true
            compositeDisposable.add(
                fetchTopHeadlineUseCase.fetchTopHeadlines(country).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            _loading.value = false
                            if (it.isSuccessful && it.body() != null && it.code()==200) {
                                _newsData.value = it.body()
                            } else {
                                _errorMessage.value = Constants.ERROR_MESSAGE
                            }
                        }, {
                            it.printStackTrace()
                            _loading.value = false
                            _errorMessage.value = Constants.TIMEOUT_EXCEPTION_MESSAGE
                        }
                    )
            )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }

}