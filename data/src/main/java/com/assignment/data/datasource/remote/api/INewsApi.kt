package com.assignment.data.datasource.remote.api

import com.assignment.data.datasource.remote.dto.NewsListResponseDto
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API endpoint of Most popular service call
 * Created by: Vikesh Dass
 **/
interface INewsApi {
    @POST("mostpopular/v2/viewed/{days}.json")
    suspend fun fetchMostViewedNews(@Path("days") days: Int, @Query("api-key") apiKey: String): NewsListResponseDto
}