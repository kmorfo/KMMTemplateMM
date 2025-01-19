package es.rlujancreations.convention

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Created by Raúl L.C. on 19/1/25.
 */

internal fun Project.configureAndroidTarget() {
    (extensions.getByName("android") as? BaseAppModuleExtension)?.apply {
        compileSdk = libs.findVersion("projectCompileSdkVersion").get().toString().toInt()

        defaultConfig {
            applicationId = libs.findVersion("projectApplicationId").get().toString()
            minSdk = libs.findVersion("projectMinSdkVersion").get().toString().toInt()
            targetSdk = libs.findVersion("projectTargetSdkVersion").get().toString().toInt()
            versionCode = libs.findVersion("projectVersionCode").get().toString().toInt()
            versionName = libs.findVersion("projectVersionName").get().toString()
        }

        configureCompileOptions()
        configureKotlin()
        dependencies()
    }
}

internal fun Project.configureAndroidLibraryTarget() {
    (extensions.getByName("android") as? LibraryExtension)?.apply {
        compileSdk = libs.findVersion("projectCompileSdkVersion").get().toString().toInt()
        defaultConfig {
            minSdk = libs.findVersion("projectMinSdkVersion").get().toString().toInt()
        }
        configureCompileOptions()
        configureKotlin()
        dependencies()
    }
}

private fun CommonExtension<*, *, *, *, *, *>.configureCompileOptions() {
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

private fun Project.dependencies() {
    dependencies {
        add("coreLibraryDesugaring", libs.findLibrary("desugar.jdk.libs").get())
    }
}


private fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}

internal fun Project.configureIosFrameworks() {
    extensions.configure(KotlinMultiplatformExtension::class.java) {
        targets.withType(KotlinNativeTarget::class.java)
            .configureEach {
                binaries.framework {
                    baseName = "ComposeApp"
                    isStatic = true
                }
            }
    }
}