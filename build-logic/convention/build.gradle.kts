@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    `kotlin-dsl`
}

group = "es.rlujancreations.kmmtemplatemm.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}
gradlePlugin {
    plugins {
        register("multiplatformApplication") {
            id = "kmmtemplatemm.multiplatform.application"
            implementationClass = "KmpApplicationConventionPlugin"
        }
        register("multiplatformApplicationCompose") {
            id = "kmmtemplatemm.multiplatform.application.compose"
            implementationClass = "KmpApplicationComposeConventionPlugin"
        }
        register("multiplatformLibrary") {
            id = "kmmtemplatemm.multiplatform.library"
            implementationClass = "KmpLibraryConventionPlugin"
        }
        register("multiplatformLibraryCompose") {
            id = "kmmtemplatemm.multiplatform.library.compose"
            implementationClass = "KmpLibraryComposeConventionPlugin"
        }
        register("multiplatformFeatureUi") {
            id = "kmmtemplatemm.multiplatform.feature.ui"
            implementationClass = "KmpFeatureUiConventionPlugin"
        }
        register("kmpRoom") {
            id = "kmmtemplatemm.multiplatform.room"
            implementationClass = "KmpRoomConventionPlugin"
        }
        register("KmpKoin") {
            id = "kmmtemplatemm.multiplatform.koin"
            implementationClass = "KmpKoinConventionPlugin"
        }
        register("KmpKtor") {
            id = "kmmtemplatemm.multiplatform.ktor"
            implementationClass = "KmpKtorConventionPlugin"
        }
        register("KmpKotlinTest") {
            id = "kmmtemplatemm.multiplatform.kotlin.test"
            implementationClass = "KmpKotlinTestConventionPlugin"
        }
    }
}