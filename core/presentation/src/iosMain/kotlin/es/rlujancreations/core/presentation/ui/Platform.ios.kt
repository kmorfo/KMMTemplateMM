package es.rlujancreations.core.presentation.ui

import es.rlujancreations.core.presentation.ui.Platform.Companion.IOS
import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val platformName: String = IOS
    override val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    override val versionCode: Int = 11
}

actual fun getPlatform(): Platform = IOSPlatform()
