import com.android.build.api.dsl.ApplicationExtension
import es.rlujancreations.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

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
//            extensions.configure<ApplicationExtension> {
//                buildFeatures {
//                    compose = true
//                }
//            }
            dependencies {
//                "commonMainImplementation"(libs.findLibrary("compose-ui").get())
//                "commonMainImplementation"(libs.findLibrary("compose-foundation").get())
//                "commonMainImplementation"(libs.findLibrary("compose-material").get())
//                "commonMainImplementation"(libs.findLibrary("compose-material-icons-core").get())
//
//                "androidMainImplementation"(libs.findLibrary("androidx-compose-ui-tooling").get())
            }
        }
    }
}
