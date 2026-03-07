plugins {
    alias(libs.plugins.convention.cmp.library)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                // Add KMP dependencies here

                implementation(projects.core.presentation)

                implementation(libs.coil.compose)
                implementation(libs.coil.network.ktor)

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
