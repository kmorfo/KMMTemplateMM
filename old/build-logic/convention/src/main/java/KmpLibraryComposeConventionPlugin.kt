import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by Raúl L.C. on 19/1/25.
 */
class KmpLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("kmmtemplatemm.multiplatform.library")
                apply("org.jetbrains.compose")
                apply("org.jetbrains.kotlin.plugin.compose")
            }
        }
    }
}
