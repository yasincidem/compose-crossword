package com.yasincidem.composecrossword.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.yasincidem.composecrossword.ui.clue.ClueContainer
import com.yasincidem.composecrossword.ui.keyboard.Command
import com.yasincidem.composecrossword.ui.keyboard.CrosswordKeyboard
import com.yasincidem.composecrossword.ui.keyboard.Key
import com.yasincidem.composecrossword.ui.table.CrosswordTable
import com.yasincidem.composecrossword.utils.disableMultiTouch

@Composable
fun CrosswordScreenContent(
    viewModel: MainViewModel,
    modifier: Modifier,
) = with(viewModel) {

    val letterState by remember { letter }
    val indexController = remember { indexController }
    val crossword by remember { crosswordState }

    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
            .disableMultiTouch()
            .then(modifier)
    ) {

        CircleShape

        val (table, clues, keyboard) = createRefs()

        CrosswordTable(
            cells = crossword.cells,
            gridCellCount = crossword.gridCount,
            letterByUser = letterState.char,
            selectedIndex = crossword.selectedIndex,
            selectedWord = crossword.selectedWord,
            onIndexChanged = ::setIndex,
            onWordChanged = ::setWord,
            swapWordsIfPossible = ::swapWordsIfPossible,
            indexController = indexController,
            lettersState = crossword.cellLetters,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .wrapContentSize()
                .constrainAs(table) {
                    top.linkTo(parent.top, margin = 16.dp)
                }
        )

        ClueContainer(
            Modifier
                .constrainAs(clues) {
                    height = Dimension.preferredWrapContent
                    linkTo(
                        top = table.bottom,
                        bottom = keyboard.top,
                        topMargin = 12.dp,
                        bottomMargin = 12.dp
                    )
                },
            words = crossword.words,
            onClueClicked = ::changeWord
        )

        CrosswordKeyboard(
            Modifier.constrainAs(keyboard) {
                bottom.linkTo(parent.bottom)
            },
            onKeyClicked = { key ->
                when (key) {
                    is Key.Action -> {
                        when (key.command) {
                            Command.BACK_SPACE -> { deleteLetter() }
                            Command.NEXT -> { switchNextWord() }
                            Command.PREVIOUS -> { switchPreviousWord() }
                        }
                    }
                    is Key.Letter -> setLetter(key)
                }
            }
        )
    }
}