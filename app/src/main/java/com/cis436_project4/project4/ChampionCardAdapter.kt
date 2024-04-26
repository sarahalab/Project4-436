package com.cis436_project4.project4

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.Locale

class ChampionCardAdapter(private val context: Context, private val championModelArrayList: ArrayList<ChampionModel>) : RecyclerView.Adapter<ChampionCardAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvChampionName)
        val rbLevel: RatingBar = view.findViewById(R.id.rbChampionLevel)
        val ivImage: ImageView = view.findViewById(R.id.ivChampionImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.champion_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val champion = championModelArrayList[position]
        holder.tvName.text = champion.getChampionName()
        holder.rbLevel.rating = champion.getChampionLevel().toFloat()

        var championName : String = champion.getChampionName()
        championName = if(championName.contains(" ")) {
            val words = championName.split(" ")
            words[0][0].uppercase() + words[0].substring(1).lowercase() + words[1][0].uppercase() + words[1].substring(1).lowercase()

        } else if (championName.contains("'")) {
            val words = championName.split("'")
            words[0][0].uppercase() + words[0].substring(1).lowercase() + words[1].lowercase()
        } else {
            championName[0].uppercase() + championName.substring(1).lowercase()
        }


        val imageUrl = "https://ddragon.leagueoflegends.com/cdn/14.8.1/img/champion/${championName}.png"
        Glide.with(context).load(imageUrl).into(holder.ivImage)
    }

    override fun getItemCount(): Int {
        return championModelArrayList.size
    }
}