package com.yasincidem.composecrossword.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.consumeDownChange
import androidx.compose.ui.input.pointer.pointerInput

fun Modifier.disableMultiTouch(): Modifier = pointerInput(Unit) {
    awaitPointerEventScope {
        while (true) {
            awaitPointerEvent(PointerEventPass.Initial).changes.forEachIndexed { index, change ->
                if (index > 0)
                    change.consumeDownChange()
            }
        }
    }
}