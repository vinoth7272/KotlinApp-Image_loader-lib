package com.example.greedygameassesment.model

import android.os.Parcel
import android.os.Parcelable

 data class ImageUrls(val thumbnailUrl:String,val largeImageUrl:String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(thumbnailUrl)
        parcel.writeString(largeImageUrl)
    }

    override fun describeContents(): Int {
        return hashCode()
    }

    companion object CREATOR : Parcelable.Creator<ImageUrls> {
        override fun createFromParcel(parcel: Parcel): ImageUrls {
            return ImageUrls(parcel)
        }

        override fun newArray(size: Int): Array<ImageUrls?> {
            return arrayOfNulls(size)
        }
    }
}
