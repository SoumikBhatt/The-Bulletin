package com.soumik.newsapp.features.home.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.soumik.newsapp.core.network.NewsWebService
import com.soumik.newsapp.features.home.domain.model.Article
import com.soumik.newsapp.features.home.domain.model.NewsModel
import com.soumik.newsapp.features.home.domain.model.Source
import com.soumik.newsapp.features.home.presentation.viewmodel.RxImmediateSchedulerRule
import io.reactivex.rxjava3.core.Flowable
import junit.framework.TestCase
import okhttp3.ResponseBody
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class HomeRepositoryImplTest : TestCase() {

    @Mock
    private lateinit var mockNewsWebService: NewsWebService
    private lateinit var testHomeRepositoryImpl: HomeRepositoryImpl
    private lateinit var mockNewsModel: NewsModel


    companion object {
        @ClassRule
        @JvmField
        val scheduler = RxImmediateSchedulerRule()
    }

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    public override fun setUp() {
        MockitoAnnotations.openMocks(this)
        testHomeRepositoryImpl = HomeRepositoryImpl(mockNewsWebService)
        mockNewsModel =
            NewsModel(listOf(Article("", "", "", "", Source("", ""), "", "", "")), "ok", 20)
    }

    @Test
    fun test_fetchTopHeadlines_success() {
        Mockito.`when`(mockNewsWebService.fetchTopHeadlines("us")).thenReturn(
            Flowable.just(Response.success(mockNewsModel))
        )

        testHomeRepositoryImpl.fetchTopHeadlines("us","").test()
            .assertComplete()
    }

    @Test
    fun test_fetchTopHeadlines_failed() {
        val errorResponse = Response.error<NewsModel>(500, ResponseBody.create(null, "Error"))

        Mockito.`when`(mockNewsWebService.fetchTopHeadlines("us")).thenReturn(
            Flowable.just(errorResponse)
        )

        testHomeRepositoryImpl.fetchTopHeadlines("us","").test().assertValue(errorResponse)
    }

    @After
    public override fun tearDown() {
    }
}