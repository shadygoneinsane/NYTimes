package com.assignment.data.datasource.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * File Description
 * Created by: Vikesh Dass
 **/
sealed class CommonDto {

    data class CommonResponse<T>(val response: Any?, val data: T?) : CommonDto()

    data class Error(
        @SerializedName("code")val errorCode: String,
        @SerializedName("message")val errorMessage: String
    ) : CommonDto()

    data class ServerDate(
        @SerializedName("serverDateTime")val dateTime: String? = ""
    ) : CommonDto()

    data class Location(
        @SerializedName("latitude") val latitude: Double,
        @SerializedName("longitude") val longitude: Double
    )
}