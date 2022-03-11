package com.yasincidem.composecrossword.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Keep
@Serializable
@Parcelize
data class Word(
    val id: Int,
    val index: Int,
    val startIndex: Int,
    val actualWord: String,
    val direction: Direction,
    val clue: String,
    @Transient
    var isRevealed: Boolean = false
) : Parcelable {
    fun actualWordCharList() = actualWord.toCharArray()
    fun actualWordLength() = actualWord.length
}