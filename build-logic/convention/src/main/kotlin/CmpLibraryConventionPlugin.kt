import es.rlujancreations.convention.KmpSourceSets
import es.rlujancreations.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class CmpLibraryConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("es.rlujancreations.convention.kmp.library")
                apply("es.rlujancreations.convention.koin")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.compose")
            }

            dependencies {
                val composeUi = libs.findLibrary("compose-ui").get()
                val composeFoundation = libs.findLibrary("compose-foundation").get()
                val composeMaterial3 = libs.findLibrary("compose-material3").get()
                val composeMaterialIconsCore = libs.findLibrary("compose-material-icons-core").get()

                val composeUiTooling = libs.findLibrary("androidx-compose-ui-tooling").get()

                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, composeUi)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, composeFoundation)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, composeMaterial3)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, composeMaterialIconsCore)

                add(KmpSourceSets.ANDROID_MAIN_IMPLEMENTATION, composeUiTooling)
            }
        }
    }
}