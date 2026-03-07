import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

/**
 * Created by Raúl L.C. on 19/1/25.
 */

class KmpFeatureUiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                pluginManager.apply("kmmtemplatemm.multiplatform.library.compose")
            }

            kotlinExtension.sourceSets.getByName("commonMain") {
                dependencies {
                    implementation(project(":core:presentation"))
                }
            }
        }
    }
}