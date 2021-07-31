package com.assignment.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.assignment.base.BaseUnitTest
import com.assignment.data.constants.NetworkConstants
import com.assignment.di.module.application.ApiModule
import com.assignment.di.module.application.RepositoryModule
import com.assignment.domain.entity.response.news.NYEntity
import com.assignment.domain.repository.NewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.modules.junit4.PowerMockRunner
import java.net.HttpURLConnection

/**
 * Unit test for Main View Model
 */
@ExperimentalCoroutinesApi
@RunWith(PowerMockRunner::class)
class MainViewModelTest : BaseUnitTest() {

    private lateinit var viewModel: MainViewModel
    private lateinit var repository: NewsRepository
    private lateinit var apiModule: ApiModule
    private lateinit var repositoryModule: RepositoryModule

    @Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    override fun setUp() {
        super.setUp()
        apiModule = ApiModule()
        repositoryModule = RepositoryModule()
        repository =
            repositoryModule.providePopularRepository(apiModule.providePopularApi(retrofit))
        viewModel = MainViewModel(repository)
    }

    @Test
    fun assertObjects() {
        Assert.assertNotNull(repositoryModule)
        Assert.assertNotNull(repository)
        Assert.assertNotNull(viewModel)
    }

    @Test
    fun testFetchNews() = runBlocking {
        mockNetworkResponseWithFileContent("fetch_news_success.json", HttpURLConnection.HTTP_OK)
        viewModel.fetchNews(false)
        Assert.assertNotNull(viewModel.getList())
    }

    @Test
    fun testSwipeToRefresh() = runBlockingTest {
        mockNetworkResponseWithFileContent("fetch_news_success.json", HttpURLConnection.HTTP_OK)
        viewModel.swipeRefreshEvent()
        Assert.assertNotNull(viewModel.getList())
    }

    @Test
    fun testFetchNewsFailure() = runBlockingTest {
        mockNetworkResponseWithFileContent(
            "fetch_news_error.json",
            HttpURLConnection.HTTP_SERVER_ERROR
        )
        viewModel.fetchNews(false)
        viewModel.errorEvent.observeForever {
            Assert.assertEquals(
                NetworkConstants.NETWORK_ERROR_MESSAGES.SERVICE_UNAVAILABLE,
                it.errorMessage
            )
        }
    }

    @Test
    fun testPrepareNewsList() = runBlockingTest {
        val newsResult = listOf(
            NYEntity.NewsResult(
                "",
                "https://www.nytimes.com.html",
                "", "", "", "",
                listOf(NYEntity.Media(listOf(NYEntity.MediaMeta("abc", ""))))
            )
        )
        Assert.assertNotNull(newsResult)
        viewModel.prepareNewsList(newsResult)
    }
}