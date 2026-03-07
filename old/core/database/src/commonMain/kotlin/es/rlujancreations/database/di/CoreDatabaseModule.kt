package es.rlujancreations.database.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import es.rlujancreations.database.DatabaseFactory
import es.rlujancreations.database.SampleDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Created by Raúl L.C. on 19/1/25.
 */

expect val databaseNativeModule: Module

val coreDatabaseModule =
    module {
        single {
            get<DatabaseFactory>().create()
                .setDriver(BundledSQLiteDriver())
                .build()
        }
        single { get<SampleDatabase>().sampleDAO }
    }
