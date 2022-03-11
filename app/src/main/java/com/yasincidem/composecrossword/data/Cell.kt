package com.yasincidem.composecrossword.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Cell(
    var index: Int,
    var isAvailable: Boolean = false,
    var actualLetter: Char = ' ',
    var wordStartCornerText: String = "",
    var wordPair: Pair<Word?, Word?>? = null,
) : Parcelable {
    fun isIntersection() = wordPair?.second != null
    fun getFirstWord() = wordPair?.first
    fun getSecondWord() = wordPair?.second
}