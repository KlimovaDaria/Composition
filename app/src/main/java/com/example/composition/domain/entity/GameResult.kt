package com.example.composition.domain.entity

import android.os.Parcel
import android.os.Parcelable


data class GameResult(
    val winner: Boolean,
    val countOfRightAnswers: Int,
    val countOfAnswers: Int,
    val gameSettings: GameSettings
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readParcelable(GameSettings::class.java.classLoader)
            ?: throw RuntimeException("Не удалось прочитать GameSettings")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (winner) 1 else 0)
        parcel.writeInt(countOfRightAnswers)
        parcel.writeInt(countOfAnswers)
        parcel.writeParcelable(gameSettings, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GameResult> {
        override fun createFromParcel(parcel: Parcel): GameResult {
            return GameResult(parcel)
        }

        override fun newArray(size: Int): Array<GameResult?> {
            return arrayOfNulls(size)
        }
    }

}