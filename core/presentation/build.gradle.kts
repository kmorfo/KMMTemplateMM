plugins {
    alias(libs.plugins.kmmtemplatemm.multiplatform.library.compose)
}

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(compose.components.resources)
        }
        desktopMain.dependencies {}
    }
}

android {
    namespace = "es.rlujancreations.core.presentation"
}
