import es.rlujancreations.convention.KmpSourceSets
import es.rlujancreations.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KtorConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {

            dependencies {
                val ktorCore = libs.findLibrary("ktor-client-core").get()
                val ktorContentNegotiation = libs.findLibrary("ktor-client-content-negotiation").get()
                val ktorSerialization = libs.findLibrary("ktor-serialization-json").get()
                val ktorAuth = libs.findLibrary("ktor-client-auth").get()
                val ktorLogging = libs.findLibrary("ktor-client-logging").get()

                val ktorCio = libs.findLibrary("ktor-client-cio").get()
                val ktorOkHttp = libs.findLibrary("ktor-client-okhttp").get()
                val ktorDarwin = libs.findLibrary("ktor-client-darwin").get()

                val kotlinStdlib = libs.findLibrary("kotlin-stdlib").get()


                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, ktorCore)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, ktorContentNegotiation)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, ktorCio)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, ktorSerialization)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, ktorAuth)
                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, ktorLogging)

                add(KmpSourceSets.ANDROID_MAIN_IMPLEMENTATION, ktorOkHttp)

                target.extensions.configure<KotlinMultiplatformExtension> {
                    sourceSets.getByName(KmpSourceSets.KSP_IOS_MAIN).dependencies {
                        implementation(ktorDarwin)
                    }
                }

                add(KmpSourceSets.COMMON_MAIN_IMPLEMENTATION, kotlinStdlib)
            }
        }
    }
}


