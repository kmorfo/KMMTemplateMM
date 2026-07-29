plugins {
    alias(libs.plugins.convention.cmp.library)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.coil.compose)

                implementation(libs.compose.components.resources)
                implementation(libs.compose.uiToolingPreview)
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

compose.resources {
    publicResClass = true
}
