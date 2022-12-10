package com.example.myapplication.retrofit
const val BASE_URL = "https://mobile-study.simbirsoft1.com/"

class Common {
    val retrofitServices: NewsAPI
        get() = NewsService.getClient(BASE_URL).create(NewsAPI::class.java)
}
