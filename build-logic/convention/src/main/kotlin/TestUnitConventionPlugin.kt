import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import es.rlujancreations.convention.KmpSourceSets
import es.rlujancreations.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class TestUnitConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("es.rlujancreations.convention.kmp.library")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                extensions.configure<KotlinMultiplatformAndroidLibraryExtension> {
                    withHostTest {
                        isReturnDefaultValues = true
                    }
                }
            }

            val kotlinTest = libs.findLibrary("kotlin-test").get()
            val coroutinesTest = libs.findLibrary("kotlinx-coroutines-test").get()
            val assertk = libs.findLibrary("assertk").get()
            val turbine = libs.findLibrary("turbine").get()

            val kotlinTestJunit5 = libs.findLibrary("kotlin-test-junit5").get()
            val junitJupiterEngine = libs.findLibrary("junit-jupiter-engine").get()
            val junitPlatformLauncher = libs.findLibrary("junit-platform-launcher").get()

            dependencies {
                add(KmpSourceSets.COMMON_TEST_IMPLEMENTATION, kotlinTest)
                add(KmpSourceSets.COMMON_TEST_IMPLEMENTATION, coroutinesTest)
                add(KmpSourceSets.COMMON_TEST_IMPLEMENTATION, assertk)
                add(KmpSourceSets.COMMON_TEST_IMPLEMENTATION, turbine)

                add(KmpSourceSets.ANDROID_HOST_TEST_IMPLEMENTATION, kotlinTestJunit5)
                add(KmpSourceSets.ANDROID_HOST_TEST_IMPLEMENTATION, junitJupiterEngine)
                add(KmpSourceSets.ANDROID_HOST_TEST_RUNTIME_ONLY, junitPlatformLauncher)
            }

            tasks.withType<Test>().configureEach {
                useJUnitPlatform()
            }
        }
    }
}
