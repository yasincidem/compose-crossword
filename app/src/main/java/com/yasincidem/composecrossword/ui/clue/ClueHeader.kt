package com.yasincidem.composecrossword.ui.clue

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yasincidem.composecrossword.R
import com.yasincidem.composecrossword.data.ClueHeaderData
import com.yasincidem.composecrossword.data.Direction
import com.yasincidem.composecrossword.ui.theme.Gallery

@Composable
fun ClueHeader(direction: Direction) {

    val (iconResId, iconDescription, title) = when (direction) {
        Direction.ACROSS -> ClueHeaderData(R.drawable.ic_across, "Across Icon", "Across")
        Direction.DOWN -> ClueHeaderData(R.drawable.ic_down, "Down Icon", "Down")
    }

    Row(
        Modifier
            .background(color = Gallery, shape = RoundedCornerShape(8.dp))
            .padding(4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = iconDescription,
            tint = Color.Black
        )

        Text(
            text = title,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
    }
}