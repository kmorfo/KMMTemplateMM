import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec
import com.codingfeline.buildkonfig.gradle.BuildKonfigExtension
import es.rlujancreations.convention.pathToPackageName
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class BuildKonfigConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.codingfeline.buildkonfig")
            }

            extensions.configure<BuildKonfigExtension> {
                packageName = target.pathToPackageName()
                defaultConfigs {
                    val apiKey = gradleLocalProperties(rootDir, rootProject.providers)
                        .getProperty(API_KEY_PROPERTY)
                        ?: throw IllegalStateException("$ERROR_MESSAGE $API_KEY_PROPERTY")
                    val baseUrl = gradleLocalProperties(rootDir, rootProject.providers)
                        .getProperty(BASE_URL_PROPERTY)
                        ?: throw IllegalStateException("$ERROR_MESSAGE $BASE_URL_PROPERTY")
                    buildConfigField(FieldSpec.Type.STRING, API_KEY_PROPERTY, apiKey)
                    buildConfigField(FieldSpec.Type.STRING, BASE_URL_PROPERTY, baseUrl)
                }
            }
        }
    }
}

private const val API_KEY_PROPERTY = "API_KEY"
private const val BASE_URL_PROPERTY = "BASE_URL"
private const val ERROR_MESSAGE = "Missing property in local.properties:"