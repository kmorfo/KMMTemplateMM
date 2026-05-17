plugins {
    alias(libs.plugins.convention.kmp.library)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                // Add KMP dependencies here
            }
        }

        androidMain {
            dependencies { }
        }

        iosMain {
            dependencies { }
        }
    }
}
