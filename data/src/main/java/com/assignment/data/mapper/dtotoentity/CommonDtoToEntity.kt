package com.assignment.data.mapper.dtotoentity

import com.assignment.data.datasource.remote.dto.CommonDto
import com.assignment.domain.entity.response.common.CommonEntity

/**
 * Keep all the DTO to Entity Mapping here
 * Created by: Vikesh Dass
 */
fun <T> CommonDto.CommonResponse<T>.map() = CommonEntity.CommonResponse(
    response = response,
    data = data
)

fun CommonDto.ServerDate.map() = CommonEntity.ServerDate(
    dateTime = dateTime
)

fun CommonDto.Location.map() = CommonEntity.Location(latitude, longitude)
