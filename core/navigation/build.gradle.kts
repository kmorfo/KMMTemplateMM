plugins {
    alias(libs.plugins.convention.cmp.library)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.jetbrains.navigation3.ui)
            implementation(libs.jetbrains.lifecycle.viewmodel.nav3)
            implementation(libs.material3.adaptive)
            implementation(libs.material3.adaptive.layout)
        }
        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
        }
    }
}
