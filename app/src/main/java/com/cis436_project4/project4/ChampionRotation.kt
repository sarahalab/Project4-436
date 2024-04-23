package com.cis436_project4.project4

import com.google.gson.annotations.SerializedName

data class ChampionRotation(
    @SerializedName("freeChampionIds") val freeChampionIds: List<Int>
)
