package com.blacklivesmatter.policebrutality.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.blacklivesmatter.policebrutality.config.INCIDENT_DATA_FILENAME
import com.blacklivesmatter.policebrutality.data.AppDatabase
import com.blacklivesmatter.policebrutality.data.model.IncidentsSource
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.coroutineScope
import timber.log.Timber
import javax.inject.Inject

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    init {
        // Inject into Workers (androidx.WorkManager API)
        // https://github.com/google/dagger/issues/1183#issuecomment-601158396
        val injector = context.applicationContext as HasAndroidInjector
        injector.androidInjector().inject(this)
    }

    @Inject
    internal lateinit var gson: Gson

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
