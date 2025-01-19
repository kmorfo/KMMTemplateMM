import com.android.build.api.dsl.LibraryExtension
import es.rlujancreations.convention.configureKmpCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

/**
 * Created by Raúl L.C. on 19/1/25.
 */
class KmpLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                pluginManager.apply("kmmtemplatemm.multiplatform.library")
                pluginManager.apply("org.jetbrains.compose")
                pluginManager.apply("org.jetbrains.kotlin.plugin.compose")
            }
            val extension = extensions.getByType<LibraryExtension>()
            configureKmpCompose(extension, project)
        }
    }
}