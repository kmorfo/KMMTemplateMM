import es.rlujancreations.convention.KmpSourceSets
import es.rlujancreations.convention.applyHierarchyTemplate
import es.rlujancreations.convention.configureAndroidLibraryTarget
import es.rlujancreations.convention.configureDesktopTarget
import es.rlujancreations.convention.configureIosTargets
import es.rlujancreations.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class CmpApplicationConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.kotlin.multiplatform.library")
                apply("org.jetbrains.kotlin.multiplatform")
                apply("org.jetbrains.compose")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            configureAndroidLibraryTarget()
            configureIosTargets()
            configureDesktopTarget()

            extensions.configure<KotlinMultiplatformExtension> {
                applyHierarchyTemplate()
            }

            dependencies {
                val composeUiTooling = libs.findLibrary("compose-uiTooling").get()

                add(
                    KmpSourceSets.ANDROID_MAIN_IMPLEMENTATION,
                    composeUiTooling
                )
            }
        }
    }
}