package es.rlujancreations.kmmtemplatemm

import androidx.compose.ui.window.ComposeUIViewController
import es.rlujancreations.kmmtemplatemm.di.initKoin

@Suppress("ktlint")
fun MainViewController() =
    ComposeUIViewController(
        configure = { initKoin() },
    ) {
        App()
    }
