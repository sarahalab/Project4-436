package com.cis436_project4.project4

data class SummonerProfile(
    val id: String,
    val accountId: String,
    val puuid: String,
    val name: String,
    val profileIconId: Int,
    val summonerLevel: Long
)