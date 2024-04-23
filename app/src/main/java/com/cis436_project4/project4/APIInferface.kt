package com.cis436_project4.project4

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface APIInterface {
    @GET("riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}")
    fun getUserByGameNameAndTagLine(
        @Path("gameName") gameName: String,
        @Path("tagLine") tagLine: String
    ): Call<SummonerProfile>
}
