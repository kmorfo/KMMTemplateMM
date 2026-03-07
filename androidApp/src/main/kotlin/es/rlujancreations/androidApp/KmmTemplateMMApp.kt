package es.rlujancreations.androidApp

import android.app.Application
import es.rlujancreations.mmkmptemplate.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class KmmTemplateMMApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@KmmTemplateMMApp)
        }
    }
}
