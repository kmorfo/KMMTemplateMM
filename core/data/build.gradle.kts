plugins {
    alias(libs.plugins.convention.kmp.library)
    alias(libs.plugins.convention.buildkonfig)
    alias(libs.plugins.convention.ktor)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.domain)
            }
        }

        val jvmCommonMain by creating {
            dependsOn(commonMain.get())
        }

        androidMain {
            dependsOn(jvmCommonMain)
            dependencies { }
        }

        iosMain {
            dependencies { }
        }
    }
}
