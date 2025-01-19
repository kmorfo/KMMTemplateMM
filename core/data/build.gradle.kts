import org.gradle.declarative.dsl.schema.FqName.Empty.packageName
import java.util.Properties

plugins {
    alias(libs.plugins.kmmtemplatemm.multiplatform.library)
    alias(libs.plugins.kmmtemplatemm.multiplatform.ktor)
    alias(libs.plugins.kmmtemplatemm.multiplatform.koin)
    alias(libs.plugins.gradleBuildConfig)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
        }
    }
}

buildConfig {
    packageName("es.rlujancreations.core.data")
    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").reader())
    val baseUrl = properties.getProperty("BASE_URL")

    buildConfigField("BASE_URL", baseUrl)
}

android {
    namespace = "es.rlujancreations.core.data"
}
