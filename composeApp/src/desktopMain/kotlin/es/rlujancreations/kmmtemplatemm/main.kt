package es.rlujancreations.kmmtemplatemm

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import es.rlujancreations.kmmtemplatemm.di.initKoin

@Suppress("ktlint")
fun main() =
    application {
        val state = rememberWindowState(width = 1200.dp, height = 900.dp)
        initKoin()
        Window(
            onCloseRequest = ::exitApplication,
            title = "kmmtemplatemm",
            state = state,
        ) {
            App()
        }
    }
