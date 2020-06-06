package com.github.policebrutality.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.github.policebrutality.config.INCIDENT_DATA_FILENAME
import com.github.policebrutality.data.model.IncidentsSource
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.github.policebrutality.data.AppDatabase
import kotlinx.coroutines.coroutineScope

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(INCIDENT_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val incidents =
                        Gson().fromJson<IncidentsSource>(jsonReader, IncidentsSource::class.java)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.incidentDao().insertAll(incidents.data)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "SeedDatabaseWorker"
    }
}