import org.jetbrains.kotlin.gradle.dsl.JvmTarget

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    `kotlin-dsl`
}

group = "es.rlujancreations.convention.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
    implementation(libs.buildkonfig.gradlePlugin)
    implementation(libs.buildkonfig.compiler)
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
            id = "es.rlujancreations.convention.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidComposeApplication") {
            id = "es.rlujancreations.convention.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("cmpApplication") {
            id = "es.rlujancreations.convention.cmp.application"
            implementationClass = "CmpApplicationConventionPlugin"
        }
        register("kmpLibrary") {
            id = "es.rlujancreations.convention.kmp.library"
            implementationClass = "KmpLibraryConventionPlugin"
        }
        register("cmpLibrary") {
            id = "es.rlujancreations.convention.cmp.library"
            implementationClass = "CmpLibraryConventionPlugin"
        }
        register("cmpFeature") {
            id = "es.rlujancreations.convention.cmp.feature"
            implementationClass = "CmpFeatureConventionPlugin"
        }
        register("buildKonfig") {
            id = "es.rlujancreations.convention.buildkonfig"
            implementationClass = "BuildKonfigConventionPlugin"
        }
        register("room") {
            id = "es.rlujancreations.convention.room"
            implementationClass = "RoomConventionPlugin"
        }
        register("KoinConventionPlugin") {
            id = "es.rlujancreations.convention.koin"
            implementationClass = "KoinConventionPlugin"
        }
    }
}