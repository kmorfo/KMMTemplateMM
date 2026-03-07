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
            val composeBundle = libs.findBundle("compose").get()
            val composeUiToolingPreview = libs.findLibrary("compose-uiToolingPreview").get()
            val composeUiTooling = libs.findLibrary("compose-uiTooling").get()

            add(AndroidConfigurations.IMPLEMENTATION, composeBundle)
            add(AndroidConfigurations.DEBUG_IMPLEMENTATION, composeUiToolingPreview)
            add(AndroidConfigurations.DEBUG_IMPLEMENTATION, composeUiTooling)
        }
    }
}