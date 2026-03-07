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

/**
 * Created by Raúl L.C. on 19/1/25.
 */


class KmpApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
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
                "androidMainImplementation"(libs.findLibrary("compose-uiTooling").get())
            }
        }
    }
}
