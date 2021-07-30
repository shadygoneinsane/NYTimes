package com.assignment.ui.dto

import com.assignment.data.datasource.remote.dto.NewsListResponseDto
import com.assignment.data.datasource.remote.dto.ResultDto
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.modules.junit4.PowerMockRunner

/**
 * Test for [NewsListResponseDto]
 */
@RunWith(PowerMockRunner::class)
class NewsListResponseDtoTest {
    @Test
    fun testNewsListResponseDto() {
        var newsListResponseDto: NewsListResponseDto? = null
        Assert.assertNull(newsListResponseDto)
        newsListResponseDto =
            NewsListResponseDto(
                "", "", 1,
                listOf(
                    ResultDto(
                        "", "", 0, 0,
                        "", "", "", "",
                        "", "", "byline", "",
                        "title", "", listOf(), 0
                    )
                )
            )
        Assert.assertNotNull(newsListResponseDto)
        Assert.assertEquals("byline", newsListResponseDto.results[0].byline)
    }
}