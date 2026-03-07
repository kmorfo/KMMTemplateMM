package es.rlujancreations.mmkmptemplate

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
