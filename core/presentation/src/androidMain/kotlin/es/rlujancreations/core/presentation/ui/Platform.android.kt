package es.rlujancreations.core.presentation.ui

import android.os.Build
import es.rlujancreations.core.presentation.ui.Platform.Companion.ANDROID

class AndroidPlatform : Platform {
    override val platformName: String = ANDROID
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override val versionCode: Int = Build.VERSION.SDK_INT
}

actual fun getPlatform(): Platform = AndroidPlatform()
