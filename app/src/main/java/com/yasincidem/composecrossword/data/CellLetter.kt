package com.yasincidem.composecrossword.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class CellLetter(
    val isFrozen: Boolean,
    val char: Char,
) : Parcelable