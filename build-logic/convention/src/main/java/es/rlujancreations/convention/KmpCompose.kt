package es.rlujancreations.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Created by Raúl L.C. on 19/1/25.
 */

internal fun Project.configureKmpCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    project: Project,
) {
    commonExtension.run {
        dependencies {
            add("debugImplementation", libs.findLibrary("androidx.compose.ui.tooling").get())
        }
        composeDepencencies(project)
    }

}

private fun composeDepencencies(project: Project) {
    val libs = project.libs
    val kotlinExtension = project.extensions.getByType(KotlinMultiplatformExtension::class.java)

    kotlinExtension.sourceSets.getByName("androidMain") {
        dependencies {
            implementation(libs.findLibrary("compose.components.uiToolingPreview").get())
            implementation(libs.findLibrary("androidx.compose.ui.tooling").get())
            implementation(libs.findLibrary("androidx.activity.compose").get())
            implementation(libs.findLibrary("koin.android").get())
        }
    }
    kotlinExtension.sourceSets.getByName("androidUnitTest") {
        dependencies {
        }
    }
    kotlinExtension.sourceSets.getByName("androidInstrumentedTest") {
        dependencies {
        }
    }

    kotlinExtension.sourceSets.getByName("commonMain") {
        dependencies {
            implementation(libs.findBundle("compose").get())

            implementation(project.dependencies.platform(libs.findLibrary("koin.bom").get()))
            implementation(libs.findLibrary("koin.core").get())
            implementation(libs.findLibrary("koin.compose").get())
            implementation(libs.findLibrary("koin.compose.viewmodel").get())
        }
    }

}