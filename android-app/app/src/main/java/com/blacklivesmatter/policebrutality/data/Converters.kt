package com.blacklivesmatter.policebrutality.data

import androidx.room.TypeConverter
import com.blacklivesmatter.policebrutality.data.model.GeoCoding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.lang.reflect.Type

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {
    private val gson = Gson()
    private val listType: Type = object : TypeToken<List<String>>() {}.type
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

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

    /**
     * See https://medium.com/androiddevelopers/room-time-2b4cf9672b98
     */
    @TypeConverter
    fun toOffsetDateTime(value: String?): OffsetDateTime? {
        return value?.let {
            return formatter.parse(value, OffsetDateTime::from)
        }
    }

    /**
     * See https://medium.com/androiddevelopers/room-time-2b4cf9672b98
     */
    @TypeConverter
    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
        return date?.format(formatter)
    }

    @TypeConverter
    fun geoCodingToJsonText(geoCoding: GeoCoding?): String? {
        return geoCoding?.let { gson.toJson(geoCoding, GeoCoding::class.java) }
    }

    @TypeConverter
    fun jsonTextToGeoCoding(geoCodingJsonObjectText: String?): GeoCoding? {
        if (geoCodingJsonObjectText == null) {
            return null
        } else {
            return gson.fromJson(geoCodingJsonObjectText, GeoCoding::class.java)
        }
    }
}
