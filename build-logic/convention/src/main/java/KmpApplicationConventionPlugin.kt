import es.rlujancreations.convention.ExtensionType
import es.rlujancreations.convention.configureAndroidTarget
import es.rlujancreations.convention.configureBuildTypes
import es.rlujancreations.convention.configureIosFrameworks
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Created by Raúl L.C. on 19/1/25.
 */


class KmpApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.multiplatform")
            }

            extensions.configure(KotlinMultiplatformExtension::class.java) {
                androidTarget {
                    configureAndroidTarget()

                    configureBuildTypes(
                        extensionType = ExtensionType.APPLICATION
                    )

                }

                iosX64()
                iosArm64()
                iosSimulatorArm64()

            }
            configureIosFrameworks()
        }
    }
}