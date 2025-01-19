import es.rlujancreations.convention.configureAndroidLibraryTarget
import es.rlujancreations.convention.configureIosFrameworks
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Created by Raúl L.C. on 19/1/25.
 */

class KmpLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.multiplatform")
            }
            extensions.configure(KotlinMultiplatformExtension::class.java) {
                androidTarget {
                    configureAndroidLibraryTarget()
                }
                iosX64()
                iosArm64()
                iosSimulatorArm64()
            }
            configureIosFrameworks()
        }
    }
}