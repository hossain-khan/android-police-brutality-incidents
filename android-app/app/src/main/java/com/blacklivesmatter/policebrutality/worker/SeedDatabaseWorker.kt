package com.blacklivesmatter.policebrutality.worker

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.blacklivesmatter.policebrutality.config.INCIDENT_DATA_FILENAME
import com.blacklivesmatter.policebrutality.data.AppDatabase
import com.blacklivesmatter.policebrutality.data.model.IncidentsSource
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.coroutineScope
import timber.log.Timber

class SeedDatabaseWorker @WorkerInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    val gson: Gson
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(INCIDENT_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val incidents = gson.fromJson<IncidentsSource>(jsonReader, IncidentsSource::class.java)
                    Timber.i("Processed ${incidents.data.size} incidents from JSON.")

                    val database = AppDatabase.getInstance(applicationContext)
                    database.incidentDao().insertAll(incidents.data)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Timber.e(ex, "Error seeding database")
            Result.failure()
        }
    }
}
