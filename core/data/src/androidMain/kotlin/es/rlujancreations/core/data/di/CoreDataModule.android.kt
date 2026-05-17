package es.rlujancreations.core.data.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module

actual val platformCoreDataModule =
    module {
        single<HttpClientEngine> { OkHttp.create() }
    }
