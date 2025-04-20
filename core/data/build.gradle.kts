import java.util.Properties

plugins {
    alias(libs.plugins.kmmtemplatemm.multiplatform.library)
    alias(libs.plugins.kmmtemplatemm.multiplatform.ktor)
    alias(libs.plugins.kmmtemplatemm.multiplatform.koin)
    alias(libs.plugins.gradleBuildConfig)
}

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(projects.core.domain)
        }
        desktopMain.dependencies {}
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
