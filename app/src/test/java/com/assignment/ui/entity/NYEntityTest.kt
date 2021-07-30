package com.assignment.ui.entity

import com.assignment.domain.entity.response.news.NYEntity
import org.junit.Assert
import org.junit.Test

/**
 * Test class for [NYEntityTest]
 */
class NYEntityTest {
    @Test
    fun testNYEntityNewsList() {
        var newsList: NYEntity.NewsList? = null
        Assert.assertNull(newsList)
        newsList =
            NYEntity.NewsList(
                "",
                listOf(
                    NYEntity.NewsResult(
                        "",
                        "https://www.nytimes.com.html",
                        "", "", "", "",
                        listOf()
                    )
                )
            )
        Assert.assertNotNull(newsList)
        Assert.assertEquals("https://www.nytimes.com.html", newsList.results[0].url)
    }
}