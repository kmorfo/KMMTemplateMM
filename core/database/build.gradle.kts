plugins {
    alias(libs.plugins.convention.kmp.library)
    alias(libs.plugins.convention.buildkonfig)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.domain)

                implementation(libs.kotlin.stdlib)

                implementation(libs.datastore)
                implementation(libs.datastore.preferences)

                implementation(libs.androidx.room.runtime)
                implementation(libs.sqlite.bundled)
            }
        }

        val jvmCommonMain by creating {
            dependsOn(commonMain.get())
        }

        desktopMain {
            dependsOn(jvmCommonMain)
            dependencies {}
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
