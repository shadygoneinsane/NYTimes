package com.assignment.domain.repository

import com.assignment.domain.common.ResultState
import com.assignment.domain.entity.response.news.NYEntity
import kotlinx.coroutines.flow.Flow

/**
 * The abstraction of Popular repository goes here
 * Created by: Vikesh Dass
 **/
interface NewsRepository {
    fun fetchMostViewedNews(apiKey: String, days: Int): Flow<ResultState<NYEntity.NewsList>>
}