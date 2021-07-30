package com.assignment.data.mapper.dtotoentity

import com.assignment.data.datasource.remote.dto.MediaDto
import com.assignment.data.datasource.remote.dto.MediaMetaDto
import com.assignment.data.datasource.remote.dto.NewsListResponseDto
import com.assignment.data.datasource.remote.dto.ResultDto
import com.assignment.domain.entity.response.news.NYEntity

/**
 * File Description
 * Created by: Vikesh Dass
 **/
fun NewsListResponseDto.map() = NYEntity.NewsList(
    status, results.map { it.map() }
)

fun ResultDto.map() = NYEntity.NewsResult(
    uri, url, publishedDate, section, byline, title, media.map { it.map() }
)

fun MediaDto.map() = NYEntity.Media(mediaMetadata.map { it.map() })

fun MediaMetaDto.map() = NYEntity.MediaMeta(url, format)