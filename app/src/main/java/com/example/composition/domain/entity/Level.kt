package com.example.composition.domain.entity

import android.os.Parcel
import android.os.Parcelable

enum class Level: Parcelable {
    TEST, EASY, NORMAL, HARD;

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Level> {
        override fun createFromParcel(parcel: Parcel): Level {
            val levelName = parcel.readString() ?: TEST.name
            return valueOf(levelName)
        }

        override fun newArray(size: Int): Array<Level?> {
            return arrayOfNulls(size)
        }
    }
}