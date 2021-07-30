package com.assignment.data.repository

import com.assignment.data.datasource.remote.api.INewsApi
import com.assignment.data.mapper.dtotoentity.map
import com.assignment.domain.common.ResultState
import com.assignment.domain.entity.response.news.NYEntity
import com.assignment.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * PopularRepository Implementation
 * Created by: Vikesh Dass
 **/
class NewsRepositoryImpl(
    private val popularApi: INewsApi,
) : BaseRepositoryImpl(), NewsRepository {
    override fun fetchMostViewedNews(
        apiKey: String, days: Int
    ): Flow<ResultState<NYEntity.NewsList>> = flow {
        emit(apiCall {
            popularApi.fetchMostViewedNews(days, apiKey).map()
        })
    }.flowOn(Dispatchers.IO)

}