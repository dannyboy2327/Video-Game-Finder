package com.anomalydev.videogamefinder.business.domain.util

object DescriptionUtil {

    fun removeTagFromDescriptionString(description: String): String {
        val word1 = "<p>"
        val firstDescription = description.replace(word1, "")

        val word2 = "</p>"
        val secondDescription = firstDescription.replace(word2, "")

        val word3 = "<br />"
        return secondDescription.replace(word3, "")
    }
}