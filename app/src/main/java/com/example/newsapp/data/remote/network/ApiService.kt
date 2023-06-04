package com.example.newsapp.data.remote.network

import com.example.newsapp.data.remote.response.NewsResponse
import com.example.newsapp.utils.Constant.API_KEY
import com.example.newsapp.utils.Constant.COUNTRY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/v2/top-headlines")
    fun loadHeadlines(@Query("country") countryCode:String = COUNTRY, @Query("apiKey") apiKey: String = API_KEY): Call<NewsResponse>

    @GET("/v2/everything")
    fun loadAllNews(@Query("q") q: String = "Android", @Query("apiKey") apiKey: String = API_KEY): Call<NewsResponse>
}