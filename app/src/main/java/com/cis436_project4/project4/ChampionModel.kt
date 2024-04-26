package com.cis436_project4.project4

class ChampionModel(private var puuid: String, private var championId: Int, private var championLevel: Int, private var championName: String) {
    fun getPuuid(): String {
        return puuid
    }

    fun getChampionId(): Int {
        return championId
    }

    fun getChampionLevel(): Int {
        return championLevel
    }

    fun setPuuid(puuid: String) {
        this.puuid = puuid
    }

    fun setChampionId(championId: Int) {
        this.championId = championId
    }

    fun setChampionLevel(championLevel: Int) {
        this.championLevel = championLevel
    }

    fun getChampionName(): String {
        return championName
    }

    fun setChampionName(championName: String) {
        this.championName = championName
    }
}