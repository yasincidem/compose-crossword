package com.yasincidem.composecrossword.ui.table

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yasincidem.composecrossword.data.Cell
import com.yasincidem.composecrossword.data.CellLetter
import com.yasincidem.composecrossword.ui.theme.LinkWater

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CellBox(
    cell: Cell,
    cellLetterByUser: CellLetter,
    index: Int,
    swapWordsIfPossible: (Cell) -> Unit,
    cornerShape: RoundedCornerShape,
    selectedIndex: Int,
    onIndexChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val selectionModifier = if (cell.isAvailable) {
        Modifier
            .clickable {
                swapWordsIfPossible(cell)
                onIndexChanged(index)
            }
            .background(color = if (selectedIndex == index) LinkWater else Color.Unspecified,
                shape = cornerShape)
    } else {
        Modifier.background(color = Color.LightGray, shape = cornerShape)
    }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .then(selectionModifier)
    ) {
        if (cell.isAvailable) {
            Text(
                text = cellLetterByUser.char.toString(),
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.SemiBold
            )
        }

        cell.wordStartCornerText.takeIf { it.isNotEmpty() && cellLetterByUser.char == ' ' }?.let {
            Text(
                text = it,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 4.dp, top = 2.dp),
                color = Color.Gray,
                fontSize = 9.sp
            )
        }
    }
}

@Preview
@Composable
fun CellBoxPreview() {

}