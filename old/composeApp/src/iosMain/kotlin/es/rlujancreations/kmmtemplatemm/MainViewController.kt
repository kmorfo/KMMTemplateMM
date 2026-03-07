package es.rlujancreations.kmmtemplatemm

import androidx.compose.ui.window.ComposeUIViewController
import es.rlujancreations.kmmtemplatemm.di.initKoin
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

@Suppress("ktlint")
fun MainViewController() =
    ComposeUIViewController(
        configure = { initKoin() },
    ) {
        debugBuild()
        App()
    }

fun debugBuild() {
    Napier.base(DebugAntilog())
}
