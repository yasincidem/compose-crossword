package com.yasincidem.composecrossword.data

sealed interface Result {
    object Loading : Result
    object Success : Result
}