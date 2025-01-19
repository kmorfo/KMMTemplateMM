plugins {
    alias(libs.plugins.kmmtemplatemm.multiplatform.library)
    alias(libs.plugins.kmmtemplatemm.multiplatform.room)
    alias(libs.plugins.kmmtemplatemm.multiplatform.koin)
    alias(libs.plugins.kmmtemplatemm.multiplatform.kotlin.test)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Import modules to test
            implementation(projects.core.database)

            implementation(libs.kotlinx.datetime)

            implementation(libs.kotlinx.serialization.json)

            implementation(libs.kotlinx.coroutines.core)
        }
        commonTest.dependencies {
            implementation(projects.commonTest)
        }
    }
}

android {
    namespace = "es.rlujancreations.commonTest"
}
