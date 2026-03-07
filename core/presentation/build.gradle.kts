plugins {
    alias(libs.plugins.convention.cmp.library)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.domain)

                implementation(libs.kotlin.stdlib)

                implementation(libs.material3.adaptive)
                implementation(libs.androidx.lifecycle.runtime.compose)
                implementation(libs.bundles.koin.common)

                implementation(libs.compose.components.resources)
            }
        }

        val mobileMain by getting {
            dependencies {
                implementation(libs.moko.permissions)
                implementation(libs.moko.permissions.compose)
                implementation(libs.moko.permissions.notifications)
            }
        }
        androidMain {
            dependsOn(mobileMain)
        }
    }
}
