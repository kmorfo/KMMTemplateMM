package es.rlujancreations.database.di

import es.rlujancreations.database.DatabaseFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Created by Raúl L.C. on 19/1/25.
 */
actual val databaseNativeModule =
    module {
        single { DatabaseFactory(androidApplication()) }
    }
