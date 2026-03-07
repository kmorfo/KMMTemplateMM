import androidx.room.gradle.RoomExtension
import es.rlujancreations.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * Created by Raúl L.C. on 19/1/25.
 */
class KmpRoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.google.devtools.ksp")
                apply("androidx.room")
            }

            extensions.configure<RoomExtension> {
                schemaDirectory("$projectDir/schemas")
            }

            dependencies {
                "commonMainApi"(libs.findLibrary("androidx.room.runtime").get())
                "commonMainApi"(libs.findLibrary("androidx.sqlite.bundled").get())
                "kspAndroid"(libs.findLibrary("androidx.room.compiler").get())
                "kspIosSimulatorArm64"(libs.findLibrary("androidx.room.compiler").get())
                "kspIosArm64"(libs.findLibrary("androidx.room.compiler").get())
                "kspIosX64"(libs.findLibrary("androidx.room.compiler").get())
                "kspDesktop"(libs.findLibrary("androidx.room.compiler").get())
            }
        }
    }
}
