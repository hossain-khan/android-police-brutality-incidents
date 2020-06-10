package com.blacklivesmatter.policebrutality.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.blacklivesmatter.policebrutality.config.INCIDENT_DATA_FILENAME
import com.blacklivesmatter.policebrutality.data.AppDatabase
import com.blacklivesmatter.policebrutality.data.OffsetDateTimeConverter
import com.blacklivesmatter.policebrutality.data.model.IncidentsSource
import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.coroutineScope
import org.threeten.bp.OffsetDateTime
import timber.log.Timber

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(INCIDENT_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val gson = GsonBuilder()
                        .registerTypeAdapter(OffsetDateTime::class.java, OffsetDateTimeConverter())
                        .create() // TODO - move gson creation to DI
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
