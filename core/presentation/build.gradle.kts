plugins {
    alias(libs.plugins.kmmtemplatemm.multiplatform.feature.ui)
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
