package com.yasincidem.composecrossword.data

import androidx.annotation.DrawableRes

data class ClueHeaderData(
    @DrawableRes val icon: Int,
    val iconDescription: String,
    val title: String,
)