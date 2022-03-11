package com.yasincidem.composecrossword.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import com.yasincidem.composecrossword.data.CornerType

fun getGridRoundedCornerShape(
    index: Int,
    cellCount: Int,
): RoundedCornerShape {

    val radiusPercent = 32

    val currentCornerType = when (index) {
        0 -> {
            CornerType.TOP_START
        }
        cellCount - 1 -> {
            CornerType.TOP_END
        }
        ((cellCount * cellCount) - (cellCount - 1)) - 1 -> {
            CornerType.BOTTOM_START
        }
        (cellCount * cellCount) - 1 -> {
            CornerType.BOTTOM_END
        }
        else -> null
    }

    fun getCornerPercent(type: CornerType) = if (currentCornerType == type) radiusPercent else 0

    return RoundedCornerShape(
        topStartPercent = getCornerPercent(CornerType.TOP_START),
        topEndPercent = getCornerPercent(CornerType.TOP_END),
        bottomStartPercent = getCornerPercent(CornerType.BOTTOM_START),
        bottomEndPercent = getCornerPercent(CornerType.BOTTOM_END)
    )
}