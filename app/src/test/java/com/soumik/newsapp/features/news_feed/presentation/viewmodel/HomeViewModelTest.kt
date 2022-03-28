package com.soumik.newsapp.features.news_feed.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.soumik.newsapp.core.network.NewsWebService
import com.soumik.newsapp.core.utils.Connectivity
import com.soumik.newsapp.core.utils.Constants
import com.soumik.newsapp.features.news_feed.data.model.Article
import com.soumik.newsapp.features.news_feed.data.model.NewsModel
import com.soumik.newsapp.features.news_feed.data.model.Source
import com.soumik.newsapp.features.news_feed.data.repository.HomeRepositoryImpl
import com.soumik.newsapp.features.news_feed.domain.usecase.FetchTopHeadlineUseCase
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import junit.framework.TestCase
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class HomeViewModelTest : TestCase() {

    @Mock
    private lateinit var mockFetchTopHeadlineUseCase: FetchTopHeadlineUseCase
    @Mock
    private lateinit var mockHomeRepositoryImpl: HomeRepositoryImpl
    @Mock
    private lateinit var mockNewsWebService: NewsWebService
    @Mock
    private lateinit var mockResponseObserver: Observer<NewsModel?>
    @Mock
    private lateinit var mockErrorObserver: Observer<String?>
    private lateinit var mHomeViewModel: HomeViewModel
    private lateinit var mNewsModel: NewsModel

    @Before
    public override fun setUp() {
        MockitoAnnotations.openMocks(this)
        mockHomeRepositoryImpl = HomeRepositoryImpl(mockNewsWebService)
        mockFetchTopHeadlineUseCase = FetchTopHeadlineUseCase(mockHomeRepositoryImpl)
        mHomeViewModel = HomeViewModel(mockFetchTopHeadlineUseCase)
        mNewsModel = NewsModel(listOf(Article("","","","", Source("",""),"","","")),"ok",20)
    }

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    public override fun tearDown() {}

    @Test
    fun test_fetchTopHeadlines_success() {
        Mockito.`when`(mockFetchTopHeadlineUseCase.fetchTopHeadlines("us"))
            .thenReturn(Flowable.just(Response.success(mNewsModel)))

        mHomeViewModel.newsData.observeForever(mockResponseObserver)
        mHomeViewModel.fetchTopHeadlines("us")

        assertTrue("Success",mHomeViewModel.newsData.value?.status=="ok")
    }

    @Test
    fun test_fetchTopHeadlines_failed() {
        Mockito.`when`(mockFetchTopHeadlineUseCase.fetchTopHeadlines("us"))
            .thenReturn(Flowable.just(Response.error(500, ResponseBody.create(null,"Error"))))

        mHomeViewModel.errorMessage.observeForever(mockErrorObserver)
        mHomeViewModel.fetchTopHeadlines("us")

        assertTrue("Failed",mHomeViewModel.errorMessage.value==Constants.ERROR_MESSAGE)

    }
}