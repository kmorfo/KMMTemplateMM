plugins {
    alias(libs.plugins.kmmtemplatemm.multiplatform.library)
    alias(libs.plugins.kmmtemplatemm.multiplatform.koin)
}

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
        }
        desktopMain.dependencies {}
    }
}

android {
    namespace = "es.rlujancreations.core.domain"
}
