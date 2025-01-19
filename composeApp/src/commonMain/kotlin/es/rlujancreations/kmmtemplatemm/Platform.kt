package es.rlujancreations.kmmtemplatemm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform