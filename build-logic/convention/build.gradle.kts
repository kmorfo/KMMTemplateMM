import org.jetbrains.kotlin.gradle.dsl.JvmTarget

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

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_21
    }
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "kmmtemplatemm.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidComposeApplication") {
            id = "kmmtemplatemm.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
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
