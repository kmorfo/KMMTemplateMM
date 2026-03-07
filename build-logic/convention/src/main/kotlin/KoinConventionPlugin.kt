import es.rlujancreations.convention.KmpSourceSets
import es.rlujancreations.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KoinConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {

            dependencies {
                val koinBom = platform(libs.findLibrary("koin-bom").get())
                val koinCompose = libs.findLibrary("koin-compose").get()
                val koinComposeViewModel = libs.findLibrary("koin-compose-viewmodel").get()
                val koinAndroid = libs.findLibrary("koin-android").get()
                val koinAndroidxCompose = libs.findLibrary("koin-androidx-compose").get()
                val koinAndroidxNavigation = libs.findLibrary("koin-androidx-navigation").get()
                val koinCoreViewModel = libs.findLibrary("koin-core-viewmodel").get()

                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, koinBom)
                add(KmpSourceSets.ANDROID_MAIN_IMPLEMENTATION, koinBom)

                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, koinCompose)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, koinComposeViewModel)

                add(KmpSourceSets.ANDROID_MAIN_IMPLEMENTATION, koinAndroid)
                add(KmpSourceSets.ANDROID_MAIN_IMPLEMENTATION, koinAndroidxCompose)
                add(KmpSourceSets.ANDROID_MAIN_IMPLEMENTATION, koinAndroidxNavigation)
                add(KmpSourceSets.ANDROID_MAIN_IMPLEMENTATION, koinCoreViewModel)
            }
        }
    }
}