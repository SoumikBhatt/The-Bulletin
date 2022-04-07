package com.soumik.newsapp.features.home.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.soumik.newsapp.base.utils.MockConnectivity
import com.soumik.newsapp.core.utils.Constants
import com.soumik.newsapp.core.utils.IConnectivity
import com.soumik.newsapp.features.home.data.repository.HomeRepository
import com.soumik.newsapp.features.home.domain.model.Article
import com.soumik.newsapp.features.home.domain.model.NewsModel
import com.soumik.newsapp.features.home.domain.model.Source
import io.reactivex.rxjava3.core.Flowable
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

@RunWith(JUnit4::class)
class NewsFeedViewModelTest : TestCase() {

    @Mock
    private lateinit var mockHomeRepositoryImpl: HomeRepository
    @Mock private lateinit var mockConnectivity: IConnectivity
    @Mock
    private lateinit var mockResponseObserver: Observer<List<Article>>
    @Mock
    private lateinit var mockErrorObserver: Observer<String?>
    private lateinit var mNewsFeedViewModel: NewsFeedViewModel
    private lateinit var mNewsModel: NewsModel
    private lateinit var mNewsModelEmptyList: NewsModel

    @Before
    public override fun setUp() {
        MockitoAnnotations.openMocks(this)
        mockConnectivity = MockConnectivity()
        mNewsFeedViewModel = NewsFeedViewModel(mockHomeRepositoryImpl,mockConnectivity)
        mNewsModel = NewsModel(listOf(Article("","","","", Source("",""),"","","")),"ok",20)
        mNewsModelEmptyList = NewsModel(emptyList(),"ok",20)
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
        Mockito.`when`(mockHomeRepositoryImpl.fetchTopHeadlines("us",""))
            .thenReturn(Flowable.just(Response.success(mNewsModel)))

        mNewsFeedViewModel.newsData.observeForever(mockResponseObserver)
        mNewsFeedViewModel.fetchTopHeadlines("us","")

        assertTrue("Success",mNewsFeedViewModel.newsData.value!!.isNotEmpty())
    }

    @Test
    fun test_fetchTopHeadlines_failed() {
        Mockito.`when`(mockHomeRepositoryImpl.fetchTopHeadlines("us",""))
            .thenReturn(Flowable.just(Response.error(500, ResponseBody.create(null,"Error"))))

        mNewsFeedViewModel.errorMessage.observeForever(mockErrorObserver)
        mNewsFeedViewModel.fetchTopHeadlines("us","")

        assertTrue("Failed",mNewsFeedViewModel.errorMessage.value==Constants.ERROR_MESSAGE)

    }

    @Test
    fun test_fetchTopHeadlines_noItem() {
        Mockito.`when`(mockHomeRepositoryImpl.fetchTopHeadlines("us",""))
            .thenReturn(Flowable.just(Response.success(mNewsModelEmptyList)))

        mNewsFeedViewModel.errorMessage.observeForever(mockErrorObserver)
        mNewsFeedViewModel.fetchTopHeadlines("us","")

        assertTrue("Failed",mNewsFeedViewModel.errorMessage.value==Constants.NO_ITEM_FOUND)

    }
    
    @Test
    fun test_fetchTopHeadlines_noNetwork() {
        Mockito.`when`(mockHomeRepositoryImpl.fetchTopHeadlines("us",""))
            .thenReturn(Flowable.error(Exception()))
        mNewsFeedViewModel.errorMessage.observeForever(mockErrorObserver)
        mNewsFeedViewModel.fetchTopHeadlines("us","")

        assertFalse(mNewsFeedViewModel.errorMessage.value==Constants.NO_NETWORK_CONNECTION)
    }
}