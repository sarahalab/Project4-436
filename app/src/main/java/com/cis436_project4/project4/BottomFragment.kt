package com.cis436_project4.project4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class BottomFragment : Fragment(), MainActivity.DataListener {

    private lateinit var tvGameName: TextView
    private lateinit var tvTagLine: TextView
    private lateinit var tvPUUID: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom, container, false)
        tvGameName = view.findViewById(R.id.tvGameNAme)
        tvTagLine = view.findViewById(R.id.tvTagLine)
        tvPUUID = view.findViewById(R.id.tvPUUID)
        return view
    }

    override fun onProfileDataReceived(profile: SummonerProfile) {
        tvGameName.text = profile.name
        tvTagLine.text = "Level: ${profile.summonerLevel}"
        tvPUUID.text = profile.puuid
    }
}
