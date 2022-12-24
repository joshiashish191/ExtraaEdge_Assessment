package net.softglobe.extraaedgeassessment.model.network

import net.softglobe.extraaedgeassessment.data.Rocket
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("rockets/")
    suspend fun getRocketsList() : Response<List<Rocket>>
}