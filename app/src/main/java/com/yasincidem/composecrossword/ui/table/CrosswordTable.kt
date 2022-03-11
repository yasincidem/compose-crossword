package com.yasincidem.composecrossword.ui.table

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.yasincidem.composecrossword.data.Cell
import com.yasincidem.composecrossword.data.CellLetter
import com.yasincidem.composecrossword.data.Word
import com.yasincidem.composecrossword.ui.getGridRoundedCornerShape
import com.yasincidem.composecrossword.utils.IndexController

@Composable
fun CrosswordTable(
    gridCellCount: Int,
    cells: List<Cell>,
    letterByUser: Char,
    selectedIndex: Int,
    selectedWord: Word?,
    onIndexChanged: (Int) -> Unit,
    onWordChanged: (Word) -> Unit,
    swapWordsIfPossible: (Cell) -> Unit,
    indexController: IndexController,
    modifier: Modifier = Modifier,
    lettersState: Array<CellLetter>,
) {
    val strokeWidthPx = with(LocalDensity.current) { 1.dp.toPx() }

    var itemWidth by remember { mutableStateOf(0f) }
    var itemHeight by remember { mutableStateOf(0f) }

    var tableSize by remember {
        mutableStateOf(IntSize(0, 0))
    }

    Box(
        modifier = modifier
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .onGloballyPositioned {
                    tableSize = it.size
                }
                .border(
                    border = BorderStroke(1.dp, Color.LightGray),
                    shape = RoundedCornerShape(8.dp)
                )
                .drawBehind {
                    itemWidth = size.width / gridCellCount
                    itemHeight = size.height / gridCellCount

                    (1 until gridCellCount).forEach { index ->

                        val offsetWidth = (itemWidth * index) + (strokeWidthPx / index)
                        val offsetHeight = (itemHeight * index)

                        drawLine(
                            Color.LightGray,
                            Offset(0f, offsetHeight),
                            Offset(size.width, offsetHeight),
                            strokeWidthPx
                        )

                        drawLine(
                            Color.LightGray,
                            Offset(offsetWidth, 0f),
                            Offset(offsetWidth, size.height),
                            strokeWidthPx
                        )
                    }
                },
            columns = GridCells.Fixed(gridCellCount),
            content = {
                itemsIndexed(
                    items = cells,
                ) { index, cell ->

                    val cornerShape = getGridRoundedCornerShape(
                        index = index,
                        cellCount = gridCellCount
                    )

                    CellBox(
                        index = index,
                        cell = cell,
                        cellLetterByUser = lettersState[index],
                        cornerShape = cornerShape,
                        selectedIndex = selectedIndex,
                        onIndexChanged = onIndexChanged,
                        swapWordsIfPossible = swapWordsIfPossible,
                    )
                }
            },
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
            userScrollEnabled = false
        )

        if (tableSize.width != 0
            && tableSize.height != 0
            && selectedWord != null
        ) {
            CrosswordTableOverlay(
                tableSize,
                itemWidth,
                itemHeight,
                selectedWord,
                indexController
            )
        }
    }
}

@Preview
@Composable
fun CrosswordTablePreview() {

}