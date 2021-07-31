package com.assignment.ui.entity

import com.assignment.domain.entity.response.common.CommonEntity
import org.junit.Assert
import org.junit.Test

/**
 * Tests for [CommonEntity]
 * Created by: Vikesh Dass
 */
class CommonEntityTest {
    @Test
    fun testCommonResponse() {
        var commonResponse: CommonEntity.CommonResponse<String>? = null
        Assert.assertNull(commonResponse)
        commonResponse =  CommonEntity.CommonResponse("response", "data")
        Assert.assertEquals("response", commonResponse.response)
        Assert.assertEquals("data", commonResponse.data)
    }

    @Test
    fun testServerDate() {
        var serverDate: CommonEntity.ServerDate? = null
        Assert.assertNull(serverDate)
        serverDate =  CommonEntity.ServerDate("date")
        Assert.assertEquals("date", serverDate.dateTime)
    }

    @Test
    fun testLocation() {
        var location: CommonEntity.Location? = null
        Assert.assertNull(location)
        location =  CommonEntity.Location(0.0, 0.0)
        Assert.assertEquals(0.0, location.latitude, 0.0)
    }
}