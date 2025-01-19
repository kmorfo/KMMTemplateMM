plugins {
    alias(libs.plugins.kmmtemplatemm.multiplatform.library)
    alias(libs.plugins.kmmtemplatemm.multiplatform.koin)
}

kotlin {
    sourceSets {
        commonMain.dependencies {

        }
    }
}

android {
    namespace = "es.rlujancreations.core.domain"
}
