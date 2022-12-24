package com.example.myapplication.news

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsItem(
    var id: Int,
    var img: String?,
    var started_at: Long,
    var title: String?,
    var description: String?,
    var address: String?,
    var site: String?,
    var organization: String?,
    var phoneList: ArrayList<String>,
    var contributors: ArrayList<String>,
    var email: String?,
    var categories: ArrayList<String>,
    var gallery: ArrayList<String>
) : Parcelable
