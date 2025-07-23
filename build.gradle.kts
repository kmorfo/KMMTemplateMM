import org.gradle.kotlin.dsl.ktlint
import org.gradle.kotlin.dsl.libs
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.jetbrainsKotlinJvm) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinxSerialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.ktlint) apply true
}

ktlint {
    android = true
    reporters {
        reporter(ReporterType.CHECKSTYLE)
//        reporter(ReporterType.HTML)
//        reporter(ReporterType.PLAIN)
    }
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}
