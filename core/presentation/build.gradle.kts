plugins {
    alias(libs.plugins.kmmtemplatemm.multiplatform.library.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.components.resources)
        }
    }
}

android {
    namespace = "es.rlujancreations.core.presentation"
}
