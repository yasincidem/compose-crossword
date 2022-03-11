package com.yasincidem.composecrossword.ui

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasincidem.composecrossword.data.Cell
import com.yasincidem.composecrossword.data.Crossword
import com.yasincidem.composecrossword.data.Direction
import com.yasincidem.composecrossword.data.Result
import com.yasincidem.composecrossword.data.Word
import com.yasincidem.composecrossword.ui.keyboard.Key
import com.yasincidem.composecrossword.utils.IndexController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val json = Json { ignoreUnknownKeys = true }

    private var _letter = mutableStateOf(Key.Letter(' '))
    val letter: State<Key.Letter> = _letter

    private val _status = mutableStateOf<Result>(Result.Loading)
    val status: State<Result> = _status

    private val _crosswordState = mutableStateOf(Crossword(), policy = referentialEqualityPolicy())
    val crosswordState: State<Crossword> = _crosswordState

    val indexController by lazy { IndexController(_crosswordState.value.gridCount) }

    fun buildCrossword(context: Context) = viewModelScope.launch {
        try {
            val crosswordJsonData = parseCrosswordJson(context)
            val gridCount = crosswordJsonData.gridCount
            val words = crosswordJsonData.words
            val crossword = Crossword(
                gridCount = gridCount,
                words = words,
                cells = calculateCells(gridCount, words)
            )
            _crosswordState.value = crossword
            _status.value = Result.Success
        } catch (ex: Exception) {
            _status.value = Result.Loading
        }
    }

    fun setIndex(index: Int) {
        _crosswordState.value = _crosswordState.value.copy(selectedIndex = index)
    }

    fun setWord(word: Word?) {
        _crosswordState.value = _crosswordState.value.copy(selectedWord = word)
    }

    fun switchNextWord() = with(_crosswordState.value) {
        getNextWord()?.let { changeWord(it) }
    }

    fun switchPreviousWord() = with(_crosswordState.value) {
        getPreviousWord()?.let { changeWord(it) }
    }

    fun setLetter(letter: Key.Letter) = with(_crosswordState.value) {
        _letter.value = letter
        if (didWordSelected()) {
            val endIndex = indexController.getEndIndex(selectedWord!!)
            val wordIndexes = indexController.getIndexes(selectedWord)
            val selectedCellLetter = cellLetters[selectedIndex]

            if (!selectedCellLetter.isFrozen) {
                _crosswordState.value = copy(cellLetters = cellLetters.apply {
                    set(selectedIndex,
                        selectedCellLetter.copy(isFrozen = false, char = letter.char))
                })
            }

            if (endIndex != selectedIndex)
                incrementIndex()

            val correctLetters = wordIndexes.filterIndexed { index, cellIndex ->
                cellLetters[cellIndex].char == selectedWord.actualWordCharList()[index]
            }

            if (correctLetters.size == wordIndexes.size)
                freezeWord()
        }
    }

    fun deleteLetter() = with(_crosswordState.value) {
        if (didWordSelected()) {
            val selectedCellLetter = cellLetters[selectedIndex]

            if (!selectedCellLetter.isFrozen) {
                _crosswordState.value = copy(cellLetters = cellLetters.apply {
                    set(selectedIndex, selectedCellLetter.copy(isFrozen = false, char = ' '))
                })
            }
            if (selectedWord!!.startIndex != selectedIndex)
                decrementIndex()
        }
    }

    private fun freezeWord() = with(_crosswordState.value) {
        revealWord()
        indexController.getIndexes(selectedWord).forEach { cellIndex ->
            cellLetters.apply {
                set(cellIndex, get(cellIndex).copy(isFrozen = true))
            }
        }
    }

    private fun incrementIndex() = with(_crosswordState.value) {
        when (selectedWord!!.direction) {
            Direction.ACROSS -> {
                _crosswordState.value = copy(selectedIndex = selectedIndex.plus(1))
            }
            Direction.DOWN -> {
                _crosswordState.value = copy(selectedIndex = selectedIndex.plus(gridCount))
            }
        }
    }

    private fun decrementIndex() = with(_crosswordState.value) {
        when (selectedWord!!.direction) {
            Direction.ACROSS -> {
                _crosswordState.value = copy(selectedIndex = selectedIndex.minus(1))
            }
            Direction.DOWN -> {
                _crosswordState.value = copy(selectedIndex = selectedIndex.minus(gridCount))
            }
        }
    }

    fun swapWordsIfPossible(cell: Cell) {
        setWord(if (_crosswordState.value.selectedWord == cell.getFirstWord() && cell.getSecondWord() != null) cell.getSecondWord() else cell.getFirstWord())
    }

    fun invalidateWord() {
        setIndex(-1)
        setWord(null)
    }

    fun didWordSelected() =
        _crosswordState.value.selectedIndex != -1 && _crosswordState.value.selectedWord != null

    fun changeWord(word: Word) {
        setIndex(word.startIndex)
        setWord(word)
    }

    private fun calculateCells(gridCount: Int, words: List<Word>): List<Cell> {
        val cells = buildList {
            repeat(gridCount * gridCount) { index ->
                add(Cell(index = index))
            }
        }

        words.forEach { word ->
            with(word) {
                (0 until actualWordLength()).forEach { currentIndex ->
                    cells[when (direction) {
                        Direction.ACROSS -> startIndex + currentIndex
                        Direction.DOWN -> startIndex + currentIndex * gridCount
                    }].apply {
                        isAvailable = true
                        actualLetter = word.actualWordCharList()[currentIndex]
                        wordStartCornerText =
                            word.takeIf { currentIndex == 0 }?.index?.toString() ?: ""
                        wordPair = if (wordPair == null) {
                            word to null
                        } else {
                            wordPair?.first to word
                        }
                    }
                }
            }
        }

        return cells
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun parseCrosswordJson(context: Context): Crossword {
        val jsonStream = context.assets.open("crossword.json")
        return json.decodeFromStream(jsonStream)
    }
}