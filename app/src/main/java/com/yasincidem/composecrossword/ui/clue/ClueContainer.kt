package com.yasincidem.composecrossword.ui.clue

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.yasincidem.composecrossword.data.Word

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ClueContainer(
    modifier: Modifier,
    words: List<Word>,
    onClueClicked: (Word) -> Unit,
) {
    val grouped = words.groupBy { it.direction }

    LazyColumn(
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures {
                // do nothing
            }
        },
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        grouped.forEach { (direction, words) ->
            stickyHeader {
                ClueHeader(direction)
            }

            items(words) { word ->
                ClueItem(
                    word,
                    onClueClicked
                )
            }
        }
    }
}