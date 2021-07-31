package com.assignment.ui.entity

import com.assignment.domain.entity.response.common.ErrorEntity
import org.junit.Assert
import org.junit.Test

/**
 * Test for [ErrorEntity]
 */
class ErrorEntityTest {
    @Test
    fun testError() {
        var error: ErrorEntity.Error? = null
        Assert.assertNull(error)
        error = ErrorEntity.Error("100", "error")
        Assert.assertEquals("error", error.errorMessage)
    }
}