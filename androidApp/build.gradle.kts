plugins {
    alias(libs.plugins.kmmtemplatemm.android.application.compose)
    alias(libs.plugins.composeCompiler)
}

dependencies {
    implementation(projects.composeApp)

//    debugImplementation(libs.compose.uiToolingPreview)
//    debugImplementation(libs.compose.uiTooling)
//    implementation(libs.androidx.activity.compose)
//
//    //koin bom
    implementation(libs.koin.core)
    implementation(libs.koin.android)
//    implementation(libs.core.splashscreen)

//    implementation(libs.bundles.compose)
//
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat)
}
