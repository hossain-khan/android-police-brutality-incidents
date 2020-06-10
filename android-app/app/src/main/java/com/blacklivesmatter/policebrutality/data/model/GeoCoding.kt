package com.blacklivesmatter.policebrutality.data.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

/**
 * Inner object of [Incident].
 *
 * Sample data:
 * ```
 *  {
 *     "lat":"36.3728538",
 *     "long":"-94.2088172"
 *  }
 * ```
 *
 * See [Incident]
 */
@Keep
data class GeoCoding(
    @SerializedName("lat") @ColumnInfo(name = "lat") val lat: String? = null,
    @SerializedName("long") @ColumnInfo(name = "long") val long: String? = null
)
