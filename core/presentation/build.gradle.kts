plugins {
    alias(libs.plugins.convention.cmp.library)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.domain)
                implementation(projects.core.designsystem)

                implementation(libs.material3.adaptive)
                implementation(libs.androidx.lifecycle.runtime.compose)
                implementation(libs.bundles.koin.common)

                implementation(libs.compose.components.resources)
            }
        }

        val mobileMain by getting {
            dependencies { }
        }
        androidMain {
            dependsOn(mobileMain)
        }
    }
}

compose.resources {
    publicResClass = true
}
