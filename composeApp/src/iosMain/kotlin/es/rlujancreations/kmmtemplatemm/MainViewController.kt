package es.rlujancreations.kmmtemplatemm

import androidx.compose.ui.window.ComposeUIViewController
import es.rlujancreations.kmmtemplatemm.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() },
) {
    App()
}