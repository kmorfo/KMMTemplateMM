import es.rlujancreations.convention.KmpSourceSets
import es.rlujancreations.convention.configureKotlinMultiplatform
import es.rlujancreations.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KmpLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.kotlin.multiplatform.library")
                apply("org.jetbrains.kotlin.multiplatform")
                apply("org.jetbrains.kotlin.plugin.serialization")
                apply("es.rlujancreations.convention.koin")
            }

            configureKotlinMultiplatform()

            val coroutinesCore = libs.findLibrary("kotlinx-coroutines-core").get()
            val kotlinxSerializationJson = libs.findLibrary("kotlinx-serialization-json").get()
            val stdlib = libs.findLibrary("kotlin-stdlib").get()

            dependencies {
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, coroutinesCore)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, kotlinxSerializationJson)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, stdlib)
            }
        }
    }
}
