package es.rlujancreations.mmkmptemplate.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {
    @Serializable
    data object Home : Route, NavKey

    @Serializable
    data class Detail(val exampleID: String) : Route, NavKey
}
