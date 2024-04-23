package com.cis436_project4.project4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var apiService: APIInterface
    interface DataListener {
        fun onProfileDataReceived(profile: SummonerProfile)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Retrofit to communicate with API for NA
        val retrofit = Retrofit.Builder()
            .baseUrl("https://na1.api.riotgames.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(APIInterface::class.java)

        val searchView: SearchView = findViewById(R.id.svPUUID)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                fetchUserData(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun fetchUserData(username: String) {
        apiService.getUserByName(username).enqueue(object : Callback<SummonerProfile> {
            override fun onResponse(call: Call<SummonerProfile>, response: Response<SummonerProfile>) {
                if (response.isSuccessful) {
                    val userProfile = response.body()
                    userProfile?.let { profile ->
                        supportFragmentManager.fragments.forEach { fragment ->
                            (fragment as? DataListener)?.onProfileDataReceived(profile)
                        }
                    }
                } else {
                    Log.e("API Error", "Response Code: ${response.code()} ErrorBody: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<SummonerProfile>, t: Throwable) {
                Log.e("API Call Failure", "Call failed with exception: ${t.message}")
            }
        })
    }
}
