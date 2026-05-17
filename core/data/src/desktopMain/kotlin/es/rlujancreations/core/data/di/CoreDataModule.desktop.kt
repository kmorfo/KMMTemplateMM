package es.rlujancreations.core.data.di

import io.ktor.client.engine.HttpClientEngine
import org.koin.dsl.module

actual val platformCoreDataModule =
    module {
        single<HttpClientEngine> { get() }
    }

