package com.assignment.domain.entity.response.news

/**
 * Keep all the News Repository related entities over here.
 * Entity has only those fields that are required by UI
 * Created by: Vikesh Dass
 **/
class NYEntity {
    data class NewsList(
        val status: String,
        val results: List<NewsResult>
    )

    open class NewsResult(
        val uri: String,
        val url: String,
        val publishedDate: String,
        val section: String,
        val byline: String,
        val title: String,
        val media: List<Media>,
    )

    data class Media(val mediaMetadata: List<MediaMeta>)

    data class MediaMeta(val url: String, val format: String)
}