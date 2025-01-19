plugins {
    alias(libs.plugins.kmmtemplatemm.multiplatform.library)
    alias(libs.plugins.kmmtemplatemm.multiplatform.koin)
    alias(libs.plugins.kmmtemplatemm.multiplatform.room)
    alias(libs.plugins.kmmtemplatemm.multiplatform.kotlin.test)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)

            implementation(libs.kotlinx.datetime)
        }
    }
}
android {
    namespace = "es.rlujancreations.core.database"
}
