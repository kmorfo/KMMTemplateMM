import androidx.room.gradle.RoomExtension
import es.rlujancreations.convention.KmpSourceSets
import es.rlujancreations.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import kotlin.jvm.java

class RoomConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
                apply("androidx.room")
            }

            extensions.configure(KotlinMultiplatformExtension::class.java) {
                sourceSets.commonMain {
                    kotlin.srcDir("build/generated/ksp/metadata")
                }
            }

            extensions.configure<RoomExtension> {
                schemaDirectory("$projectDir/schemas")
            }

            dependencies {
                val roomRuntime = libs.findLibrary("androidx-room-runtime").get()
                val sqliteBundled = libs.findLibrary("sqlite-bundled").get()
                val roomCompiler = libs.findLibrary("androidx-room-compiler").get()

                add(KmpSourceSets.COMMON_MAIN_API, roomRuntime)
                add(KmpSourceSets.COMMON_MAIN_API, sqliteBundled)

                add(KmpSourceSets.KSP_ANDROID, roomCompiler)
                add(KmpSourceSets.KSP_IOS_SIMULATOR_ARM64, roomCompiler)
                add(KmpSourceSets.KSP_IOS_ARM64, roomCompiler)
                add(KmpSourceSets.KSP_IOS_X64, roomCompiler)
                add(KmpSourceSets.KSP_DESKTOP, roomCompiler)
            }
        }
    }
}
