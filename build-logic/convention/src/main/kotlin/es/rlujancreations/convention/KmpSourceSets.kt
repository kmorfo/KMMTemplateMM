package es.rlujancreations.convention

object KmpSourceSets {
    const val COMMON_MAIN_IMPLEMENTATION = "commonMainImplementation"
    const val COMMON_MAIN_API = "commonMainApi"

    const val ANDROID_MAIN_IMPLEMENTATION = "androidMainImplementation"

    const val COMMON_TEST_IMPLEMENTATION = "commonTestImplementation"

    const val ANDROID_HOST_TEST_IMPLEMENTATION = "androidHostTestImplementation"
    const val ANDROID_HOST_TEST_RUNTIME_ONLY = "androidHostTestRuntimeOnly"

    const val KSP_ANDROID = "kspAndroid"
    const val KSP_IOS_MAIN = "iosMain"
    const val KSP_IOS_SIMULATOR_ARM64 = "kspIosSimulatorArm64"
    const val KSP_IOS_ARM64 = "kspIosArm64"
    const val KSP_IOS_X64 = "kspIosX64"
}

object AndroidConfigurations {
    const val IMPLEMENTATION = "implementation"
    const val DEBUG_IMPLEMENTATION = "debugImplementation"
    const val CORE_LIBRARY_DESUGARING = "coreLibraryDesugaring"
}
