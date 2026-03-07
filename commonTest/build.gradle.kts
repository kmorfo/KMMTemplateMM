plugins {
    alias(libs.plugins.convention.kmp.library)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                // Import modules to test


                // Add KMP dependencies here
                implementation(libs.kotlin.stdlib)
                implementation(libs.kotlinx.coroutines.core)

                implementation(projects.core.database)

                implementation(libs.kotlinx.datetime)

                implementation(libs.kotlinx.serialization.json)


                implementation(libs.kotlin.test)
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.androidx.test.junit)
            }
        }

        iosMain {
            dependencies { }
        }
    }
}
