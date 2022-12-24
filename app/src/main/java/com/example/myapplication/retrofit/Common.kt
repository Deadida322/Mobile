package com.example.myapplication.retrofit

import java.util.*

const val BASE_URL = "https://mobile-study.simbirsoft1.com/"

object Common {
    val retrofitServices = NewsService.getClient(BASE_URL)
}
