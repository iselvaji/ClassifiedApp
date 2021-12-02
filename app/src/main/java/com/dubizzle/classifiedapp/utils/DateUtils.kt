package com.dubizzle.classifiedapp.utils

import com.dubizzle.classifiedapp.utils.DateUtils.DateFormat.get
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    private fun formatStringToDateLong(stringDate: String?, dateFormat: SimpleDateFormat): Long? {
        if (stringDate == null || stringDate.isEmpty()) return null
        val date: Date? = try {
            dateFormat.parse(stringDate)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
        return date?.time
    }

    private fun formatDateToString(date: Long?, dateFormat: SimpleDateFormat): String {
        if (date == null) return ""
        return dateFormat.format(date)
    }

    object DateFormat {
        const val DATE_TIME_HOURS_MINUTES_SECONDS_MICRO = "yyyy-MM-dd HH:mm:ss.SSSSS"
        const val DATE_TIME_HOURS_MINUTES_SEC = "yyyy-MM-dd HH:mm"

        fun get(format: String, locale: Locale = Locale.getDefault()): SimpleDateFormat =
            SimpleDateFormat(format, locale)
    }

    fun getFormattedDateTimeToDisplay(input: String?): String {
        return if (input != null) {
            val dateFormat = get(DateFormat.DATE_TIME_HOURS_MINUTES_SECONDS_MICRO, Locale.getDefault())
            val dateUtils = DateUtils()
            val millisec = dateUtils.formatStringToDateLong(input, dateFormat)
            val dateFormatToDisplay = get(DateFormat.DATE_TIME_HOURS_MINUTES_SEC, Locale.getDefault())
            val formattedDate = dateUtils.formatDateToString(millisec, dateFormatToDisplay)
            formattedDate
        } else ""
    }
}