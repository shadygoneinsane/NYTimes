package com.assignment.domain.entity.response.common

/**
 * Keep all the error related model class here
 * Created by: Vikesh Dass
 **/
sealed class ErrorEntity {
    data class Error(val errorCode: Any?, val errorMessage: String? = "") : ErrorEntity()
}