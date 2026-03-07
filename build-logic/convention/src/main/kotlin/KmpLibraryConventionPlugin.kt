import es.rlujancreations.convention.KmpSourceSets
import es.rlujancreations.convention.configureKotlinMultiplatform
import es.rlujancreations.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KmpLibraryConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.kotlin.multiplatform.library")
                apply("org.jetbrains.kotlin.multiplatform")
                apply("es.rlujancreations.convention.koin")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            configureKotlinMultiplatform()

            dependencies {

                val kotlinxSerializationJson =
                    libs.findLibrary("kotlinx-serialization-json").get()

                val kotlinTest =
                    libs.findLibrary("kotlin-test").get()

                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, kotlinxSerializationJson)

                add(KmpSourceSets.COMMON_TEST_IMPLEMENTATION, kotlinTest)
            }
        }
    }
}