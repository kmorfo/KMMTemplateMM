import es.rlujancreations.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

/**
 * Created by Raúl L.C. on 19/1/25.
 */
class KmpKtorConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            kotlinExtension.sourceSets.getByName("androidMain") {
                dependencies {
                    implementation(libs.findLibrary("ktor.client.okhttp").get())
                }
            }
            kotlinExtension.sourceSets.getByName("commonMain") {
                dependencies {
                    implementation(libs.findLibrary("ktor.client.core").get())
                    implementation(libs.findLibrary("ktor.client.content.negotiation").get())
                    implementation(libs.findLibrary("ktor.client.auth").get())
                    implementation(libs.findLibrary("ktor.client.cio").get())
                    implementation(libs.findLibrary("ktor.client.logging").get())
                    implementation(libs.findLibrary("ktor.serialization.json").get())

                    implementation(libs.findLibrary("kotlinx.coroutines.core").get())
                }
            }
            kotlinExtension.sourceSets.getByName("iosArm64Main") {
                dependencies {
                    implementation(libs.findLibrary("ktor.client.darwin").get())
                }
            }

            kotlinExtension.sourceSets.getByName("iosX64Main") {
                dependencies {
                    implementation(libs.findLibrary("ktor.client.darwin").get())
                }
            }

            kotlinExtension.sourceSets.getByName("iosSimulatorArm64Main") {
                dependencies {
                    implementation(libs.findLibrary("ktor.client.darwin").get())
                }
            }
        }
    }
}