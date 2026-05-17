plugins {
    alias(libs.plugins.convention.test.unit)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
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
