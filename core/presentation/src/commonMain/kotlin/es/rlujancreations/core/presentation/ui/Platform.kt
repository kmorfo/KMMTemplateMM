package es.rlujancreations.core.presentation.ui

interface Platform {
    companion object {
        const val ANDROID = "Android"
        const val IOS = "IOS"
    }

    val platformName: String
    val name: String
    val versionCode: Int
}

expect fun getPlatform(): Platform
