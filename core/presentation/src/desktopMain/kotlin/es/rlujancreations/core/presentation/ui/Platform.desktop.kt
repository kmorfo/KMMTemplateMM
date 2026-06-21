package es.rlujancreations.core.presentation.ui

class DesktopPlatform : Platform {
    override val platformName: String = "Desktop"
    override val name: String = "Desktop ${System.getProperty("os.name")} ${System.getProperty("os.version")}"
    override val versionCode: Int = 0
}

actual fun getPlatform(): Platform = DesktopPlatform()
