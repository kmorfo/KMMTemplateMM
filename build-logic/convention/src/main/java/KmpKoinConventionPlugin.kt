import es.rlujancreations.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

/**
 * Created by Raúl L.C. on 19/1/25.
 */
class KmpKoinConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            kotlinExtension.sourceSets.getByName("androidMain") {
                dependencies {
                    implementation(libs.findLibrary("koin.android").get())
                }
            }
            kotlinExtension.sourceSets.getByName("commonMain") {
                dependencies {
                    implementation(
                        project.dependencies.platform(
                            libs.findLibrary("koin.bom").get(),
                        ),
                    )
                    implementation(libs.findLibrary("koin.core").get())
                }
            }
        }
    }
}