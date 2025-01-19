package es.rlujancreations.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Created by Raúl L.C. on 19/1/25.
 */

internal fun Project.configureBuildTypes(
    extensionType: ExtensionType,
) {

    (extensions.getByName("android") as? BaseAppModuleExtension)?.apply {
        buildFeatures {
            buildConfig = true
        }

        sourceSets.getByName("main").apply {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/res")
            resources.srcDirs("src/commonMain/resources")
        }

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }

        when (extensionType) {
            ExtensionType.APPLICATION -> {
                extensions.configure<ApplicationExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType()
                        }
                        release {
                            configureReleaseBuildType()
                        }
                    }
                }
            }

            ExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType()
                        }
                        release {
                            configureReleaseBuildType()
                        }
                    }
                }
            }
        }
    }
}

private fun BuildType.configureDebugBuildType() {
//    buildConfigField("String", "BASE_URL", "\"http://192.168.1.217:3080/api/v1\"")
}

private fun BuildType.configureReleaseBuildType() {
//    buildConfigField("String", "BASE_URL", "\"http://192.168.1.217:3081/api/v1\"")
    //TODO Configure R8
//    isMinifyEnabled = true
}