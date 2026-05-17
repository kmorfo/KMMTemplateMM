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
                val coreDesignSystem = project(":core:designsystem")
                val coreDomain = project(":core:domain")
                val corePresentation = project(":core:presentation")

                val material3Adaptative = libs.findLibrary("material3-adaptive").get()
                val composeUi = libs.findLibrary("compose-ui").get()
                val composeFoundation = libs.findLibrary("compose-foundation").get()
                val composeMaterial3 = libs.findLibrary("compose-material3").get()
                val composeMaterialIconsCore = libs.findLibrary("compose-material-icons-core").get()
                val composeComponentsResources = libs.findLibrary("compose-components-resources").get()
                val androidxLifecycleRuntimeCompose = libs.findLibrary("androidx-lifecycle-runtime-compose").get()
                val composeUiToolingPreview = libs.findLibrary("compose-uiToolingPreview").get()
                val composeUiTooling = libs.findLibrary("compose-uiTooling").get()
                val androidxComposeUiTooling = libs.findLibrary("androidx-compose-ui-tooling").get()

                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, coreDesignSystem)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, coreDomain)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, corePresentation)

                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, composeUi)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, composeFoundation)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, composeMaterial3)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, composeMaterialIconsCore)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, composeComponentsResources)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, androidxLifecycleRuntimeCompose)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, composeUiToolingPreview)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, material3Adaptative)

                add(KmpSourceSets.ANDROID_MAIN_IMPLEMENTATION, composeUiTooling)
                add(KmpSourceSets.ANDROID_MAIN_IMPLEMENTATION, androidxComposeUiTooling)
            }
        }
    }
}
