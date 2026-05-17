package es.rlujancreations.core.data.di

import es.rlujancreations.core.data.BuildKonfig.API_KEY
import es.rlujancreations.core.data.BuildKonfig.BASE_URL
import es.rlujancreations.core.data.networking.HttpClientFactory
import es.rlujancreations.core.data.networking.HttpsClients
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

expect val platformCoreDataModule: Module

val coreDataModule =
    module {

        single(named(HttpsClients.CO_SERVER)) {
            HttpClientFactory(
                apiKey = API_KEY,
                baseUrl = BASE_URL,
                engine = get(),
            ).build()
        }
    }
