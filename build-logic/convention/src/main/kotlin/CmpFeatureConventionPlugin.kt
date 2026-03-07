import es.rlujancreations.convention.KmpSourceSets
import es.rlujancreations.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class CmpFeatureConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("es.rlujancreations.convention.cmp.library")
                apply("es.rlujancreations.convention.koin")
            }

            dependencies {
                val corePresentation = project(":core:presentation")
                val coreDesignSystem = project(":core:designsystem")

                val composeRuntime = libs.findLibrary("compose-runtime").get()
                val composeViewModel = libs.findLibrary("compose-viewmodel").get()
                val lifecycleViewModel = libs.findLibrary("androidx-lifecycle-viewmodel").get()
                val lifecycleRuntimeCompose = libs.findLibrary("androidx-lifecycle-runtime-compose").get()

                val jetbrainsLifecycleViewModelSavedState =
                    libs.findLibrary("jetbrains-lifecycle-viewmodel-savedstate").get()
                val jetbrainsSavedState = libs.findLibrary("jetbrains-savedstate").get()
                val jetbrainsBundle = libs.findLibrary("jetbrains-bundle").get()
                val navigationCompose = libs.findLibrary("androidx-navigation-compose").get()

                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, corePresentation)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, coreDesignSystem)

                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, composeRuntime)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, composeViewModel)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, lifecycleViewModel)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, lifecycleRuntimeCompose)

                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, jetbrainsLifecycleViewModelSavedState)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, jetbrainsSavedState)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, jetbrainsBundle)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, navigationCompose)
            }
        }
    }
}