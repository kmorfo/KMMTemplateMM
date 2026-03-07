package es.rlujancreations.convention

object KmpSourceSets {
    const val COMMON_MAIN_IMPLEMENTATION = "commonMainImplementation"
    const val COMMON_MAIN_API = "commonMainApi"

    const val ANDROID_MAIN_IMPLEMENTATION = "androidMainImplementation"

    const val COMMON_TEST_IMPLEMENTATION = "commonTestImplementation"

    const val KSP_ANDROID = "kspAndroid"
    const val KSP_IOS_SIMULATOR_ARM64 = "kspIosSimulatorArm64"
    const val KSP_IOS_ARM64 = "kspIosArm64"
    const val KSP_IOS_X64 = "kspIosX64"
    const val KSP_DESKTOP = "kspDesktop"
}

object AndroidConfigurations {
    const val IMPLEMENTATION = "implementation"
    const val DEBUG_IMPLEMENTATION = "debugImplementation"
    const val CORE_LIBRARY_DESUGARING = "coreLibraryDesugaring"
}