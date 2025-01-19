package es.rlujancreations.kmmtemplatemm.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

/**
 * Created by Raúl L.C. on 19/1/25.
 */

expect val commonNativeModules: List<Module>

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            *commonNativeModules.toTypedArray(),
        )
    }
}
