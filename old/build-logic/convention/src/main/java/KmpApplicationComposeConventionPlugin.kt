import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by Raúl L.C. on 19/1/25.
 */

class KmpApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.run {
            pluginManager.run {
                apply("kmmtemplatemm.multiplatform.application")
                apply("org.jetbrains.compose")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }
        }
    }
}
