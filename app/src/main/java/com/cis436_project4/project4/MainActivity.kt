package com.cis436_project4.project4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import okhttp3.Interceptor
import okhttp3.OkHttpClient
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

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.bottomFragmentContainerView, BottomFragment())
                .commit()
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val originalRequest = chain.request()
                val originalUrl = originalRequest.url()
                val url = originalUrl.newBuilder()
                    .addQueryParameter("api_key", "RGAPI-5d43ca8d-6af3-4df7-aedc-112621e191fc")
                    .build()
                val requestBuilder = originalRequest.newBuilder().url(url)
                val request = requestBuilder.build()
                chain.proceed(request)
            })
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://americas.api.riotgames.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(APIInterface::class.java)
        setupSearchView()
        fetchFreeChampionIds()
    }

    private fun setupSearchView() {
        val searchView: SearchView = findViewById(R.id.svPUUID)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val parts = query.split("#")
                if (parts.size == 2) {
                    fetchUserPUUID(parts[0], parts[1])
                } else {
                    Log.e("InputError", "Expected format: 'GameName#TagLine'")
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun fetchUserPUUID(gameName: String, tagLine: String) {
        apiService.getUserByGameNameAndTagLine(gameName, tagLine).enqueue(object : Callback<SummonerProfile> {
            override fun onResponse(call: Call<SummonerProfile>, response: Response<SummonerProfile>) {
                if (response.isSuccessful) {
                    val rawJson = response.raw().toString() // Logging the raw response
                    Log.d("API Success", "Raw JSON Response: $rawJson")
                    response.body()?.let { profile ->
                        Log.d("API Success", "Profile Data Received: $profile")
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

    private fun fetchFreeChampionIds() {
        Log.d("API Call", "fetch champion rotations")
        apiService.getChampionRotations().enqueue(object : Callback<ChampionRotation> {
            override fun onResponse(call: Call<ChampionRotation>, response: Response<ChampionRotation>) {
                if (response.isSuccessful) {
                    response.body()?.let { rotation ->
                        Log.d("API Success", "Free Champions: ${rotation.freeChampionIds}")
                        updateChampionUI(rotation.freeChampionIds)
                    }
                } else {
                    Log.e("API Error", "Response Code: ${response.code()} ErrorBody: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ChampionRotation>, t: Throwable) {
                Log.e("API Call Failure", "Call failed: ${t.message}")
            }
        })
    }
    private fun updateChampionUI(ids: List<Int>) {
        val fragment = supportFragmentManager.findFragmentById(R.id.bottomFragmentContainerView) as? BottomFragment
        if (fragment != null && fragment.isAdded) {
            fragment.updateChampionIds(ids)
        } else {
            Log.e("Fragment Error", "Fragment not found")
        }
    }
}