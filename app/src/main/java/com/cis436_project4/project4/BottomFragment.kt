package com.cis436_project4.project4

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class BottomFragment : Fragment(), MainActivity.DataListener {

    private lateinit var tvGameName: TextView
    private lateinit var tvTagLine: TextView
    private lateinit var tvPUUID: TextView
    private lateinit var tvSummonerLevel: TextView
    private var lastReceivedProfile: SummonerProfile? = null
    private lateinit var tvFreeChampionIds: TextView
    private var championIds: List<Int>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom, container, false)
        tvGameName = view.findViewById(R.id.tvGameName)
        tvTagLine = view.findViewById(R.id.tvTagLine)
        tvPUUID = view.findViewById(R.id.tvPUUID)
        tvSummonerLevel = view.findViewById(R.id.tvSummonerLevel)
        tvFreeChampionIds = view.findViewById(R.id.tvFreeChampionIds)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lastReceivedProfile?.let {
            updateUI(it)
            updateChampionIdsUI()
        }

    }
    fun updateChampionIds(ids: List<Int>) {
        championIds = ids
        updateChampionIdsUI()
    }

    private fun updateChampionIdsUI() {
        if (isAdded) {
            val idsText = championIds?.joinToString(", ") ?: "Loading..."
            tvFreeChampionIds.text = getString(R.string.free_champion_ids, idsText)
            Log.d("UI Update", "Champion IDs updated on UI: $idsText")
        } else {
            Log.e("Fragment State", "Fragment not added to Activity")
        }
    }

    override fun onProfileDataReceived(profile: SummonerProfile) {
        lastReceivedProfile = profile
        updateUI(profile)
    }

    private fun updateUI(profile: SummonerProfile) {
        tvGameName.text = profile.gameName
        tvTagLine.text = profile.tagLine
        tvPUUID.text = profile.puuid
        tvSummonerLevel.text = profile.summonerLevel.toString()
    }
}
