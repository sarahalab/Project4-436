package com.cis436_project4.project4

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Callback
import retrofit2.Response
interface APIInterface {
    @GET("lol/summoner/v4/summoners/by-name/{username}?api_key=RGAPI-edd848c3-ae2e-45bd-bff9-2c383f8e82d9")
    fun getUserByName(@Path("username") username: String): Call<SummonerProfile>
}
