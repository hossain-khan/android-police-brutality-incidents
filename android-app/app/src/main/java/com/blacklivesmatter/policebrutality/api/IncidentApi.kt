package com.blacklivesmatter.policebrutality.api

import com.blacklivesmatter.policebrutality.data.model.Incident
import com.blacklivesmatter.policebrutality.data.model.IncidentsSource
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit API service class for https://github.com/949mac/846-backend#api
 */
interface IncidentApi {
    companion object {
        const val INCLUDE_TYPE_EVIDENCE = "evidence"
    }

    /**
     * List all catalog Incidents of Police Assault
     * The [includeType] can be [INCLUDE_TYPE_EVIDENCE] to include Video Evidence on all the incidents
     *
     * Examples:
     * - https://api.846policebrutality.com/api/incidents
     * - https://api.846policebrutality.com/api/incidents?include=evidence
     */
    @GET("incidents")
    suspend fun getAllIncidents(@Query("include") includeType: String? = null): IncidentsSource

    /**
     * Provides a Single Incident of Police Assault
     * The [includeType] can be [INCLUDE_TYPE_EVIDENCE] to include Video Evidence on the Incident
     *
     * Examples:
     * - https://api.846policebrutality.com/api/incidents/7b060ec0-a9d6-11ea-ab9b-7579ddf3de22
     * - https://api.846policebrutality.com/api/incidents/7b060ec0-a9d6-11ea-ab9b-7579ddf3de22?include=evidence
     */
    @GET("incidents/{id}")
    suspend fun getIncident(@Path("id") id: String, @Query("include") includeType: String? = null): Incident
}