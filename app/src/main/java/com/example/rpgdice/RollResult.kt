package com.example.rpgdice

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RollResult(
    var whatDiceWasRolled: String,
    var rolls: ArrayList<Int> = ArrayList(),
    var mainResult: Int = 0
) : Parcelable