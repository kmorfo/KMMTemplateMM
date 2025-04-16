package es.rlujancreations.kmmtemplatemm.di

import es.rlujancreations.core.data.di.coreDataModule
import es.rlujancreations.database.di.coreDatabaseModule
import es.rlujancreations.database.di.databaseNativeModule
import es.rlujancreations.kmmtemplatemm.MainViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

/**
 * Created by Raúl L.C. on 19/1/25.
 */

expect val commonNativeModules: List<Module>

val mainViewModelModule =
    module {
        viewModelOf(::MainViewModel)
    }

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            coreDatabaseModule,
            coreDataModule,
            databaseNativeModule,
            mainViewModelModule,
            *commonNativeModules.toTypedArray(),
        )
    }
}
