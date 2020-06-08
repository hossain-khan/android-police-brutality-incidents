package com.blacklivesmatter.policebrutality.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {
    private val gson = Gson()
    val listType: Type = object : TypeToken<List<String>>() {}.type

    @TypeConverter
    fun listOfLinksToString(links: List<String>): String {
        return gson.toJson(links, listType)
    }

    @TypeConverter
    fun linksJsonToLinksArray(linksData: String): List<String> {
        val listType: Type = object : TypeToken<List<String>>() {}.type

        val linksList: List<String> = gson.fromJson(linksData, listType)
        return linksList
    }
}