package com.example.myapplication.retrofit

import com.example.myapplication.news.NewsItem
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface NewsAPI {
    @GET("news")
    fun getNewsList(): Single<MutableList<NewsItem>>
}
