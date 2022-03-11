package com.yasincidem.composecrossword.ui.keyboard

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.yasincidem.composecrossword.ui.keyboard.Key.Action
import com.yasincidem.composecrossword.ui.keyboard.Key.Letter
import com.yasincidem.composecrossword.ui.theme.icon.BackspaceIcon

enum class Command {
    BACK_SPACE,
    NEXT,
    PREVIOUS
}

sealed interface Key {
    class Letter(val char: Char) : Key
    class Action(
        val command: Command,
        val icon: ImageVector,
        val contentDescription: String,
    ) : Key
}

val topKeys = listOf('Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P').map(::Letter)
val centerKeys = buildList {
    addAll(listOf('A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L').map(::Letter))
    add(Action(Command.BACK_SPACE, BackspaceIcon, "Backspace action"))
}
val bottomKeys = buildList {
    add(Action(Command.PREVIOUS, Icons.Default.ArrowBack, "Previous word action"))
    addAll(listOf('Z', 'X', 'C', 'V', 'B', 'N', 'M').map(::Letter))
    add(Action(Command.NEXT, Icons.Default.ArrowForward, "Next word action"))
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CrosswordKeyboard(
    modifier: Modifier = Modifier,
    onKeyClicked: (Key) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(bottom = 10.dp)
            .pointerInput(Unit) {
                detectTapGestures {
                    // do nothing
                }
            },
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        KeysRow(topKeys, onKeyClicked)
        KeysRow(centerKeys, onKeyClicked)
        KeysRow(bottomKeys, onKeyClicked)
    }
}
