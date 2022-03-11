package com.yasincidem.composecrossword.ui.clue

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.yasincidem.composecrossword.data.Word
import com.yasincidem.composecrossword.ui.theme.SeaGreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClueItem(
    word: Word,
    onClueClicked: (Word) -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val textColor = if (word.isRevealed) SeaGreen else Color.Unspecified

    ConstraintLayout(
        modifier = modifier
            .padding(start = 8.dp)
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                onClueClicked(word)
            },
    ) {
        val (number, card) = createRefs()

        val verticalGuideline = createGuidelineFromStart(16.dp)

        Text(
            text = "${word.index}.",
            modifier = Modifier.constrainAs(number) {
                linkTo(
                    start = parent.start,
                    end = verticalGuideline,
                    top = parent.top,
                    bottom = parent.bottom
                )
            },
            color = textColor,
            style = MaterialTheme.typography.body2.merge(TextStyle(fontWeight = FontWeight.SemiBold))
        )

        Card(
            onClick = {
                onClueClicked(word)
            },
            modifier = Modifier
                .padding(start = 2.dp, end = 16.dp)
                .constrainAs(card) {
                    linkTo(
                        start = verticalGuideline,
                        end = parent.end,
                        bias = 0f
                    )
                },
            elevation = 0.dp,
            interactionSource = interactionSource,
        ) {
            Text(
                text = word.clue,
                modifier = Modifier.padding(6.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = textColor,
                style = MaterialTheme.typography.body2.merge(TextStyle(fontWeight = FontWeight.SemiBold))
            )
        }
    }
}