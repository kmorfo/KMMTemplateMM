package es.rlujancreations.database.di

import es.rlujancreations.database.DatabaseFactory
import org.koin.dsl.module

actual val databaseNativeModule =
    module {
        single { DatabaseFactory() }
    }
