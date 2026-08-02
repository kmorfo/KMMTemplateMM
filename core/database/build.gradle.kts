plugins {
    alias(libs.plugins.convention.kmp.library)
    alias(libs.plugins.convention.room)
    alias(libs.plugins.convention.buildkonfig)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.domain)

                implementation(libs.datastore)
                implementation(libs.datastore.preferences)

            }
        }

        val jvmCommonMain by creating {
            dependsOn(commonMain.get())
        }

        androidMain {
            dependsOn(jvmCommonMain)
            dependencies {}
        }

        iosMain {
            dependencies {}
        }
    }
}
