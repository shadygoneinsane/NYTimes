package com.assignment.data.datasource.remote.dto

import com.google.gson.annotations.SerializedName

data class NewsListResponseDto(
    @SerializedName("status") val status: String,
    @SerializedName("copyright") val copyright: String,
    @SerializedName("num_results") val numResults: Int,
    @SerializedName("results") val results: List<ResultDto>
)

data class ResultDto(
    @SerializedName("uri") val uri: String,
    @SerializedName("url") val url: String,
    @SerializedName("id") val id: Long,
    @SerializedName("asset_id") val assetId: Long,
    @SerializedName("source") val source: String,
    @SerializedName("published_date") val publishedDate: String,
    @SerializedName("updated") val updated: String,
    @SerializedName("section") val section: String,
    @SerializedName("subsection") val subsection: String,
    @SerializedName("column") val column: String,
    @SerializedName("byline") val byline: String,
    @SerializedName("type") val type: String,
    @SerializedName("title") val title: String,
    @SerializedName("abstract") val abstract: String,
    @SerializedName("media") val media: List<MediaDto>,
    @SerializedName("eta_id") val etaId: Int
)

data class MediaDto(
    @SerializedName("type") val type: String,
    @SerializedName("subtype") val subtype: String,
    @SerializedName("caption") val caption: String,
    @SerializedName("copyright") val copyright: String,
    @SerializedName("approved_for_syndication") val approvedForSyndication: Int,
    @SerializedName("media-metadata") val mediaMetadata: List<MediaMetaDto>
)

data class MediaMetaDto(
    @SerializedName("url") val url: String,
    @SerializedName("format") val format: String,
    @SerializedName("height") val height: Int,
    @SerializedName("width") val width: Int
)