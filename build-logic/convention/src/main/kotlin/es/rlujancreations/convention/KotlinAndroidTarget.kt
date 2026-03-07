package es.rlujancreations.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidLibraryTarget() {
    dependencies {
        val desugarJdkLibs = libs.findLibrary("desugar-jdk-libs").get()

        add(
            AndroidConfigurations.CORE_LIBRARY_DESUGARING,
            desugarJdkLibs
        )
    }
}