import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.convention.cmp.application)
    alias(libs.plugins.convention.koin)
    alias(libs.plugins.composeHotReload)
}

kotlin {
    androidLibrary {
        compileSdk =
            libs.versions.projectCompileSdkVersion
                .get()
                .toInt()
        minSdk =
            libs.versions.projectMinSdkVersion
                .get()
                .toInt()
        namespace = libs.versions.projectApplicationId.get()
        experimentalProperties["android.experimental.kmp.enableAndroidResources"] = true
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(projects.core.data)
            implementation(projects.core.database)
            implementation(projects.core.domain)
            implementation(projects.core.designsystem)
            implementation(projects.core.presentation)

            implementation(libs.napier)
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation(libs.androidx.navigation.compose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

compose.desktop {
    application {
        mainClass = "es.rlujancreations.mmkmptemplate.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "es.rlujancreations.mmkmptemplate"
            packageVersion = "1.0.0"
        }
    }
}
