package com.example.myapplication.retrofit

import com.example.myapplication.news.NewsItem
import retrofit2.Call
import retrofit2.http.GET

interface NewsAPI {
    @GET("news")
    fun getNewsList(): Call<MutableList<NewsItem>>
}
