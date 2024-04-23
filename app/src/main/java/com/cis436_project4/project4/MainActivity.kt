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

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val originalRequest = chain.request()
                val originalUrl = originalRequest.url()
                val url = originalUrl.newBuilder()
                    .addQueryParameter("api_key", "RGAPI-edd848c3-ae2e-45bd-bff9-2c383f8e82d9")
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
    }

    private fun setupSearchView() {
        val searchView: SearchView = findViewById(R.id.svPUUID)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val parts = query.split("#")
                if (parts.size == 2) {
                    fetchUserData(parts[0], parts[1])
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

    private fun fetchUserData(gameName: String, tagLine: String) {
        apiService.getUserByGameNameAndTagLine(gameName, tagLine).enqueue(object : Callback<SummonerProfile> {
            override fun onResponse(call: Call<SummonerProfile>, response: Response<SummonerProfile>) {
                if (response.isSuccessful) {
                    response.body()?.let { profile ->
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