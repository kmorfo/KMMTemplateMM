package es.rlujancreations.convention

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    applicationExtension: ApplicationExtension
) {
    with(applicationExtension) {
        buildFeatures {
            compose = true
        }

        dependencies {
            "implementation"(project.libs.findBundle("compose").get())
            "debugImplementation"(libs.findLibrary("compose-uiToolingPreview").get())
            "debugImplementation"(libs.findLibrary("compose-uiTooling").get())
        }
    }
}
