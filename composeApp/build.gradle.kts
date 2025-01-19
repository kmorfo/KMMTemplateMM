plugins {
    alias(libs.plugins.kmmtemplatemm.multiplatform.application.compose)
}

kotlin {

    sourceSets {
        androidMain.dependencies { }
        commonMain.dependencies {
            implementation(projects.core.presentation)
            implementation(projects.core.domain)
            implementation(projects.core.database)
        }
    }
}

android {
    namespace = "es.rlujancreations.kmmtemplatemm"
}
