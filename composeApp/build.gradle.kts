import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kmmtemplatemm.multiplatform.application.compose)
}

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies { }
        commonMain.dependencies {
            implementation(libs.napier)

            implementation(projects.core.presentation)
            implementation(projects.core.data)
            implementation(projects.core.domain)
            implementation(projects.core.database)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "es.rlujancreations.kmmtemplatemm"
}

compose.desktop {
    application {
        mainClass = "es.rlujancreations.kmmtemplatemm.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "es.rlujancreations.kmmtemplatemm"
            packageVersion = "1.0.0"

//            macOS {
//                iconFile.set(project.file("resources/icon.icns"))
//            }
//            windows {
//                iconFile.set(project.file("resources/icon.ico"))
//            }
//            linux {
//                iconFile.set(project.file("resources/icon.icon"))
//            }
        }
    }
}
