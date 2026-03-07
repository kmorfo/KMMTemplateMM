plugins {
    alias(libs.plugins.kmmtemplatemm.android.application.compose)
    alias(libs.plugins.composeCompiler)
}

dependencies {
    implementation(projects.composeApp)

    implementation(libs.bundles.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.napier)

    debugImplementation(libs.compose.uiToolingPreview)
    debugImplementation(libs.compose.uiTooling)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
}
