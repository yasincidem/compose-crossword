package com.yasincidem.composecrossword.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yasincidem.composecrossword.ui.theme.Mariner
import com.yasincidem.composecrossword.utils.ResultUi

@Composable
fun CrosswordScreen(
    viewModel: MainViewModel,
) = with(viewModel) {

    BackHandler(didWordSelected()) {
        invalidateWord()
    }

    ResultUi(
        result = status.value,
        onLoading = {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(color = Mariner,
                    modifier = Modifier.align(Alignment.Center))
            }
        }
    ) { modifier ->
        CrosswordScreenContent(
            viewModel = viewModel,
            modifier = modifier
        )
    }
}

@Preview
@Composable
fun CrosswordScreenPreview() {

}