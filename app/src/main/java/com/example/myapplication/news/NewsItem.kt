package com.example.myapplication.news

import android.os.Parcel
import android.os.Parcelable

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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList() as ArrayList<String>,
        parcel.createStringArrayList() as ArrayList<String>,
        parcel.readString(),
        parcel.createStringArrayList() as ArrayList<String>,
        parcel.createStringArrayList() as ArrayList<String>
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(img)
        parcel.writeLong(started_at)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(address)
        parcel.writeString(site)
        parcel.writeString(organization)
        parcel.writeString(email)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsItem> {
        override fun createFromParcel(parcel: Parcel): NewsItem {
            return NewsItem(parcel)
        }

        override fun newArray(size: Int): Array<NewsItem?> {
            return arrayOfNulls(size)
        }
    }
}
