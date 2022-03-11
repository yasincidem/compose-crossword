package com.yasincidem.composecrossword.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yasincidem.composecrossword.data.Result
import com.yasincidem.composecrossword.data.Result.Loading
import com.yasincidem.composecrossword.data.Result.Success

@Composable
fun ResultUi(
    modifier: Modifier = Modifier,
    result: Result,
    onLoading: @Composable (modifier: Modifier) -> Unit,
    onSuccess: @Composable (modifier: Modifier) -> Unit,
) {
    when (result) {
        is Loading -> onLoading(modifier = modifier)
        is Success -> onSuccess(modifier = modifier)
    }
}
