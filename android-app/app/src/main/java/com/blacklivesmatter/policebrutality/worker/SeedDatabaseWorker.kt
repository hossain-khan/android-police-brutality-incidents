package com.blacklivesmatter.policebrutality.worker

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.blacklivesmatter.policebrutality.config.CHARITY_DATA_FILENAME
import com.blacklivesmatter.policebrutality.config.INCIDENT_DATA_FILENAME
import com.blacklivesmatter.policebrutality.data.CharityDao
import com.blacklivesmatter.policebrutality.data.IncidentDao
import com.blacklivesmatter.policebrutality.data.model.CharityOrg
import com.blacklivesmatter.policebrutality.data.model.IncidentsSource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.coroutineScope
import timber.log.Timber

/**
 * Worker to create pre-populate the database with bundled data.
 *
 * See this article for more details:
 * https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
 */
@Suppress("BlockingMethodInNonBlockingContext")
class SeedDatabaseWorker @WorkerInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val gson: Gson,
    private val incidentDao: IncidentDao,
    private val charityDao: CharityDao
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(INCIDENT_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val incidents = gson.fromJson<IncidentsSource>(jsonReader, IncidentsSource::class.java)
                    Timber.i("Processed ${incidents.data.size} incidents from JSON.")

                    incidentDao.insertAll(incidents.data)

                    // Also seed charity organization list data
                    seedCharityTable()

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Timber.e(ex, "Error seeding database")
            Result.failure()
        }
    }

    private suspend fun seedCharityTable() {
        applicationContext.assets.open(CHARITY_DATA_FILENAME).use { inputStream ->
            val listTypeToken = object : TypeToken<List<CharityOrg>>() {}.type
            JsonReader(inputStream.reader()).use { jsonReader ->
                val charities = gson.fromJson<List<CharityOrg>>(jsonReader, listTypeToken)
                Timber.i("Processed ${charities.size} charities from JSON.")

                charityDao.insertAll(charities)
            }
        }
    }
}
