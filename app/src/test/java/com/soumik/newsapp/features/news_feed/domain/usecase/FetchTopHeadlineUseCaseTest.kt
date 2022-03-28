package com.soumik.newsapp.features.news_feed.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.soumik.newsapp.core.network.NewsWebService
import com.soumik.newsapp.features.news_feed.data.model.Article
import com.soumik.newsapp.features.news_feed.data.model.NewsModel
import com.soumik.newsapp.features.news_feed.data.model.Source
import com.soumik.newsapp.features.news_feed.data.repository.HomeRepositoryImpl
import com.soumik.newsapp.features.news_feed.presentation.viewmodel.RxImmediateSchedulerRule
import io.reactivex.rxjava3.core.Flowable
import junit.framework.TestCase
import okhttp3.ResponseBody
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class FetchTopHeadlineUseCaseTest : TestCase() {

    @Mock
    private lateinit var mockHomeRepositoryImpl: HomeRepositoryImpl
    @Mock
    private lateinit var mockNewsWebService: NewsWebService
    private lateinit var mFetchTopHeadlineUseCase: FetchTopHeadlineUseCase
    private lateinit var mNewsModel: NewsModel

    @Before
    public override fun setUp() {
        MockitoAnnotations.openMocks(this)
        mockHomeRepositoryImpl = HomeRepositoryImpl(mockNewsWebService)
        mFetchTopHeadlineUseCase = FetchTopHeadlineUseCase(mockHomeRepositoryImpl)
        mNewsModel =
            NewsModel(listOf(Article("", "", "", "", Source("", ""), "", "", "")), "ok", 20)
    }

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Test
    fun test_fetchTopHeadlines_completed() {
        Mockito.`when`(mockNewsWebService.fetchTopHeadlines("us"))
            .thenReturn(Flowable.just(Response.success(mNewsModel)))
        mFetchTopHeadlineUseCase.fetchTopHeadlines("us")
            .test()
            .assertComplete()
    }

    @Test
    fun test_fetchTopHeadlines_error() {
        val errorResponse = Response.error<NewsModel>(500, ResponseBody.create(null,"Error"))
        Mockito.`when`(mockNewsWebService.fetchTopHeadlines("us"))
            .thenReturn(Flowable.just(errorResponse))
        mFetchTopHeadlineUseCase.fetchTopHeadlines("us")
            .test()
            .assertValue(errorResponse)
    }
}