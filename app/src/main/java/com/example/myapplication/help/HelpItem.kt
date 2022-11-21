package com.example.myapplication.help

import android.os.Parcel
import android.os.Parcelable

data class HelpItem(val img: String?, val txt: String?, val value: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(img)
        parcel.writeString(txt)
        parcel.writeString(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HelpItem> {
        override fun createFromParcel(parcel: Parcel): HelpItem {
            return HelpItem(parcel)
        }

        override fun newArray(size: Int): Array<HelpItem?> {
            return arrayOfNulls(size)
        }
    }
}
