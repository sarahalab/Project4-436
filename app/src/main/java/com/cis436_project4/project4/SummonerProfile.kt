package com.cis436_project4.project4

import com.google.gson.annotations.SerializedName

data class SummonerProfile(
    val id: String,
    val accountId: String,
    val puuid: String,
    val gameName: String,
    val profileicon: Int,
    val summonerLevel: Long,
    val tagLine: String
)