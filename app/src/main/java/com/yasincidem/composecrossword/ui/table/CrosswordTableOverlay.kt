package com.yasincidem.composecrossword.ui.table

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateIntOffset
import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.animateSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.yasincidem.composecrossword.data.Direction
import com.yasincidem.composecrossword.data.Word
import com.yasincidem.composecrossword.ui.AndroidText
import com.yasincidem.composecrossword.ui.theme.SeaGreen
import com.yasincidem.composecrossword.ui.theme.Mariner
import com.yasincidem.composecrossword.utils.IndexController

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun CrosswordTableOverlay(
    tableSize: IntSize,
    itemWidth: Float,
    itemHeight: Float,
    word: Word,
    indexController: IndexController,
) {
    val canvasWidth = with(LocalDensity.current) { (tableSize.width).toDp() }
    val canvasHeight = with(LocalDensity.current) { (tableSize.height).toDp() }

    var clueCardWidth by remember { mutableStateOf(0) }

    val wordStartIndexX = indexController.getStartIndexX(word)
    val wordStartIndexY = indexController.getStartIndexY(word)
    val wordEndIndexY = indexController.getEndIndexY(word)
    val wordEndIndexX = indexController.getEndIndexX(word)

    val startX = itemWidth * wordStartIndexX
    val startY = itemHeight * wordStartIndexY

    val transition = updateTransition(word, label = "Word transition")

    val topLeftOffset by transition.animateOffset(
        transitionSpec = {
            tween(durationMillis = 250, easing = LinearOutSlowInEasing)
        },
        label = "TopLeft Offset Anim"
    ) {
        when (word.direction) {
            Direction.ACROSS -> Offset(startX, startY)
            Direction.DOWN -> Offset(startX, startY)
        }
    }

    val rectSize by transition.animateSize(
        transitionSpec = {
            tween(durationMillis = 250, easing = LinearOutSlowInEasing)
        },
        label = "Rect Size Anim"
    ) {
        when (word.direction) {
            Direction.ACROSS ->
                Size(itemWidth * (wordEndIndexX - wordStartIndexX + 1), itemHeight)
            Direction.DOWN ->
                Size(itemWidth, itemHeight * (wordEndIndexY - wordStartIndexY + 1))
        }
    }

    val popUpOffSet by transition.animateIntOffset(
        transitionSpec = {
            tween(durationMillis = 250, easing = LinearOutSlowInEasing)
        },
        label = "Popup Anim"
    ) {
        val isPopupOverflow = startX + clueCardWidth > tableSize.width
        val x = if (isPopupOverflow) tableSize.width - clueCardWidth else startX
        val y = (itemHeight * (wordStartIndexY - 1))

        IntOffset(x.toInt(), y.toInt())
    }

    Box {
        Canvas(modifier = Modifier.size(canvasWidth, canvasHeight)) {
            drawRoundRect(
                color = if (word.isRevealed) SeaGreen else Mariner,
                topLeft = topLeftOffset,
                size = rectSize,
                cornerRadius = CornerRadius(14f, 14f),
                style = Stroke(width = 8f)
            )
        }

        Box(
            Modifier
                .offset { popUpOffSet }
                .clickable {
                    // do nothing
                }
        ) {
            Card(
                modifier = Modifier.onGloballyPositioned {
                    clueCardWidth = it.size.width
                },
                shape = RoundedCornerShape(4.dp),
                backgroundColor = if (word.isRevealed) SeaGreen else Mariner
            ) {
                AndroidText(
                    text = "${word.index}. ${word.clue}",
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 6.dp),
                    color = Color.White,
                    maxLines = 1,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }

}

@Preview
@Composable
fun CrosswordTableOverlayPreview() {

}