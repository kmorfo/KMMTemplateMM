import es.rlujancreations.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

/**
 * Created by Raúl L.C. on 19/1/25.
 */
class KmpKotlinTestConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            task("testClasses")
            kotlinExtension.sourceSets.getByName("androidUnitTest") {
                dependencies {
                    implementation(libs.findLibrary("androidx.test.junit").get())
                }
            }
            kotlinExtension.sourceSets.getByName("androidInstrumentedTest") {
                dependencies {
                }
            }
            kotlinExtension.sourceSets.getByName("commonMain") {
                dependencies {
                }
            }

            kotlinExtension.sourceSets.getByName("commonTest") {
                kotlin.srcDir("src/commonTest/kotlin")
                dependencies {
                    implementation(libs.findLibrary("kotlin.test").get())
                    implementation(libs.findLibrary("kotlinx.coroutines.test").get())
                    implementation(libs.findLibrary("koin.test").get())
                }
            }
        }
    }
}