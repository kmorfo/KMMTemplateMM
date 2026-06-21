package es.rlujancreations.core.presentation.ui.di

import es.rlujancreations.core.presentation.ui.GetStringProvider
import es.rlujancreations.core.presentation.ui.StringProvider
import org.koin.dsl.module

actual val coreUiModule =
    module {
        single<StringProvider> { GetStringProvider() }
    }
