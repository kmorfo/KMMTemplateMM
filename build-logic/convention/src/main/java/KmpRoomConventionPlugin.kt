import com.google.devtools.ksp.gradle.KspExtension
import es.rlujancreations.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

/**
 * Created by Raúl L.C. on 19/1/25.
 */
class KmpRoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.google.devtools.ksp")
            }

            extensions.configure(KotlinMultiplatformExtension::class.java) {
                sourceSets.commonMain {
                    kotlin.srcDir("build/generated/ksp/metadata")
                }
            }
            kotlinExtension.sourceSets.getByName("commonMain") {
                dependencies {
                    implementation(libs.findLibrary("androidx.room.runtime").get())
                    implementation(libs.findLibrary("androidx.sqlite.bundled").get())
                }
            }

            extensions.configure<KspExtension> {
                arg("room.schemaLocation", "${projectDir}/schemas")
            }

            dependencies {
                add("kspCommonMainMetadata", libs.findLibrary("androidx.room.compiler").get())
                add("kspAndroid", libs.findLibrary("androidx.room.compiler").get())
                add("kspIosX64", libs.findLibrary("androidx.room.compiler").get())
                add("kspIosArm64", libs.findLibrary("androidx.room.compiler").get())
                add("kspIosSimulatorArm64", libs.findLibrary("androidx.room.compiler").get())
            }

        }
    }
}