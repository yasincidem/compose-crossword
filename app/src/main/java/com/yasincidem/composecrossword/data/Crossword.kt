package com.yasincidem.composecrossword.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Parcelize
@Serializable
data class Crossword(
    val selectedIndex: Int = -1,
    val selectedWord: Word? = null,
    val gridCount: Int = 0,
    val words: MutableList<Word> = mutableListOf(),
    @Transient
    val cells: List<Cell> = listOf(),
    @Transient
    val cellLetters: Array<CellLetter> = Array(gridCount * gridCount) { CellLetter(false, ' ') },
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Crossword

        if (!cellLetters.contentEquals(other.cellLetters)) return false

        return true
    }

    override fun hashCode(): Int {
        return cellLetters.contentHashCode()
    }

    fun getNextWord(): Word? = if (selectedWord != null) {
        val nextId = if (words.last().id == selectedWord.id) 0 else selectedWord.id + 1
        words[nextId]
    } else null

    fun getPreviousWord(): Word? = if (selectedWord != null) {
        val previousId = if (0 == selectedWord.id) words.last().id else selectedWord.id - 1
        words[previousId]
    } else null

    fun revealWord() {
        selectedWord ?: return
        selectedWord.isRevealed = true
        words[selectedWord.id] = selectedWord
    }
}
