package es.rlujancreations.kmmtemplatemm

import android.app.Application
import es.rlujancreations.kmmtemplatemm.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

/**
 * Created by Raúl L.C. on 19/1/25.
 */
class KmmTemplateMMApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@KmmTemplateMMApp)
        }
    }
}
