package com.yasincidem.composecrossword.ui.keyboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yasincidem.composecrossword.ui.theme.Gallery

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun KeyCard(
    key: Key,
    onKeyClicked: (Key) -> Unit,
) {

    val cardRatio = when (key) {
        is Key.Action -> 1f
        is Key.Letter -> 0.6f
    }

    Card(
        onClick = {
            onKeyClicked(key)
        },
        modifier = Modifier
            .aspectRatio(ratio = cardRatio, matchHeightConstraintsFirst = true)
            .padding(horizontal = 2.dp),
        elevation = 0.dp,
        backgroundColor = Gallery
    ) {
        when (key) {
            is Key.Action -> {
                Box(
                    modifier = Modifier.size(20.dp)
                ) {
                    Icon(
                        imageVector = key.icon,
                        contentDescription = key.contentDescription,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            is Key.Letter -> {
                Box {
                    Text(
                        text = key.char.toString(),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}