@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    `kotlin-dsl`
}

group = "es.rlujancreations.kmmtemplatemm.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}
gradlePlugin {
    plugins {
        register("multiplatformApplication") {
            id = "kmmtemplatemm.multiplatform.application"
            implementationClass = "KmpApplicationConventionPlugin"
        }
        register("multiplatformApplicationCompose") {
            id = "kmmtemplatemm.multiplatform.application.compose"
            implementationClass = "KmpApplicationComposeConventionPlugin"
        }
        register("multiplatformLibraryCompose") {
            id = "kmmtemplatemm.multiplatform.library"
            implementationClass = "KmpLibraryConventionPlugin"
        }
        register("multiplatformLibraryCompose") {
            id = "kmmtemplatemm.multiplatform.library.compose"
            implementationClass = "KmpLibraryComposeConventionPlugin"
        }
    }
}