package com.yasincidem.composecrossword.utils

import com.yasincidem.composecrossword.data.Direction
import com.yasincidem.composecrossword.data.Word

class IndexController(private val gridCount: Int) {

    fun getEndIndex(word: Word) = with(word) {
        (0 until actualWordLength()).reduce { _, index ->
            when (direction) {
                Direction.ACROSS -> startIndex + index
                Direction.DOWN -> startIndex + index * gridCount
            }
        }
    }

    fun getIndexes(word: Word?): List<Int> = buildList {
        word?.apply {
            (0 until actualWordLength()).map { index ->
                add(when (direction) {
                    Direction.ACROSS -> startIndex + index
                    Direction.DOWN -> startIndex + index * gridCount
                })
            }
        }
    }

    fun getStartIndexX(word: Word) = word.startIndex % gridCount
    fun getStartIndexY(word: Word) = word.startIndex / gridCount
    fun getEndIndexX(word: Word) = getEndIndex(word) % gridCount
    fun getEndIndexY(word: Word) = getEndIndex(word) / gridCount

}