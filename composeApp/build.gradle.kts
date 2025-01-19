plugins {
    alias(libs.plugins.kmmtemplatemm.multiplatform.application.compose)
}

kotlin {

    sourceSets {
        
        androidMain.dependencies {

        }
        commonMain.dependencies {

        }
    }
}

android {
    namespace = "es.rlujancreations.kmmtemplatemm"
}

