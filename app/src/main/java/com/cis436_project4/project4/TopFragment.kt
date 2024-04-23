package com.cis436_project4.project4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide

class TopFragment : Fragment(), MainActivity.DataListener {
    private lateinit var ivProfilePicture: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_top, container, false)
        ivProfilePicture = view.findViewById(R.id.ivProfilePicture)
        return view
    }

    override fun onProfileDataReceived(profile: SummonerProfile) {
        val imageUrl = "https://ddragon.leagueoflegends.com/cdn/14.8.1/img/profileicon/${profile.profileIconId}.png"
        Glide.with(this).load(imageUrl).into(ivProfilePicture)
    }
}