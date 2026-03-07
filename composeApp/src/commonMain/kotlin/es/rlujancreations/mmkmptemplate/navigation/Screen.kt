package es.rlujancreations.mmkmptemplate.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    object Home {
        @Serializable
        object Scaffold
    }
}
