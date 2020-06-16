package com.blacklivesmatter.policebrutality.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blacklivesmatter.policebrutality.data.model.CharityOrg

/**
 * Charitable organization data access object interface for Room.
 *
 * See:
 * - https://developer.android.com/training/data-storage/room
 * - https://developer.android.com/topic/libraries/architecture/room
 */
@Dao
interface CharityDao {
    @Query("SELECT * FROM charity ORDER BY item_weight ASC")
    suspend fun getCharities(): List<CharityOrg>

    @Query("SELECT COUNT(org_url) FROM charity")
    suspend fun getTotalRecords(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(incidents: List<CharityOrg>)
}
