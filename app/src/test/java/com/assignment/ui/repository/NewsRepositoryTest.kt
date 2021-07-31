package com.assignment.ui.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.assignment.base.BaseUnitTest
import com.assignment.di.module.application.ApiModule
import com.assignment.di.module.application.RepositoryModule
import com.assignment.domain.common.ResultState
import com.assignment.domain.repository.NewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.modules.junit4.PowerMockRunner
import java.net.HttpURLConnection

/**
 * Test class for [NewsRepository]
 */
@ExperimentalCoroutinesApi
@RunWith(PowerMockRunner::class)
class NewsRepositoryTest : BaseUnitTest() {
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
    }

    @Test
    fun testFetchMostViewedNews() = runBlocking {
        mockNetworkResponseWithFileContent("fetch_news_success.json", HttpURLConnection.HTTP_OK)
        val firstItem = repository.fetchMostViewedNews("", 1)
        testCoroutineScope.advanceTimeBy(2000)
        Assert.assertNotNull(firstItem)
        firstItem.collect {
            Assert.assertNotNull((it as? ResultState.Success)?.data)
        }
    }

    @Test
    fun testFetchMostViewedNewsFailure() = runBlocking {
        mockNetworkResponseWithFileContent("fetch_news_error.json", HttpURLConnection.HTTP_GATEWAY_TIMEOUT)
        val firstItem = repository.fetchMostViewedNews("", 1)
        testCoroutineScope.advanceTimeBy(2000)
        Assert.assertNotNull(firstItem)
        firstItem.collect {
            Assert.assertNull((it as? ResultState.Success)?.data)
        }
    }
}