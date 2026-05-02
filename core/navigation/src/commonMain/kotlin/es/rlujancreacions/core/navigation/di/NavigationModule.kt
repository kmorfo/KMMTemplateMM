package es.rlujancreacions.core.navigation.di

import es.rlujancreacions.core.navigation.deeplinks.AppDeepLinks
import org.koin.dsl.module

val navigationModule = module {
    single { AppDeepLinks }
}
