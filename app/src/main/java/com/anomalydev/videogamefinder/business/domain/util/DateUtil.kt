package com.anomalydev.videogamefinder.business.domain.util

object DateUtil {

    // Will remove time from date -> "2021-03-03T20:31:29" -> "2021-03-03"
    fun removeTimeFromDateString(stringDate: String): String {
        return stringDate.substring(0, stringDate.indexOf("T"))
    }
}