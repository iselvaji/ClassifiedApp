package com.dubizzle.classifiedapp

import com.dubizzle.classifiedapp.utils.DateUtils
import org.junit.Assert.assertEquals
import org.junit.Test

class DateUtilTest {

    private val dateUtils = DateUtils()

    @Test
    fun validateFormattedDateTime() {

        val createdAtDate = "2019-02-23 11:40:26.022080"
        val expectedFormattedDate = "2019-02-23 11:40"

        val formattedDate = dateUtils.getFormattedDateTimeToDisplay(createdAtDate)
        assertEquals(expectedFormattedDate, formattedDate)
    }
}