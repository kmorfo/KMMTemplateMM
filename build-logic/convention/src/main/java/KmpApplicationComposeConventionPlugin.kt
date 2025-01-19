import com.android.build.api.dsl.ApplicationExtension
import es.rlujancreations.convention.configureKmpCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

/**
 * Created by Raúl L.C. on 19/1/25.
 */

class KmpApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.run {
            pluginManager.run {
                pluginManager.apply("kmmtemplatemm.multiplatform.application")
                pluginManager.apply("org.jetbrains.compose")
                pluginManager.apply("org.jetbrains.kotlin.plugin.compose")
            }

            val extension = extensions.getByType<ApplicationExtension>()
            configureKmpCompose(extension, project)
        }
    }
}