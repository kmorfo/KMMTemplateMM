package es.rlujancreations.mmkmptemplate.di

import es.rlujancreations.core.data.di.coreDataModule
import es.rlujancreations.database.di.coreDatabaseModule
import es.rlujancreations.database.di.databaseNativeModule
import es.rlujancreations.mmkmptemplate.MainViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

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
