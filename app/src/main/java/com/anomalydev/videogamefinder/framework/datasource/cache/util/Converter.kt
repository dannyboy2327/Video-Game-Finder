package com.anomalydev.videogamefinder.framework.datasource.cache.util

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.anomalydev.videogamefinder.business.domain.model.Rating
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@ProvidedTypeConverter
class Converter {

    @TypeConverter
    fun ratingsToString(ratings: List<Rating>): String {
        val gson = Gson()
        return gson.toJson(ratings)
    }

    @TypeConverter
    fun stringToRatings(ratingsString: String): List<Rating> {
        val listType: Type = object: TypeToken<List<Rating>>() {}.type
        return Gson().fromJson(ratingsString, listType)
    }
}
