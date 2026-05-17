import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class TestUnitConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("es.rlujancreations.convention.kmp.library")
            }

            dependencies {
                // TODO register unit test libraries
            }
        }
    }
}
