package es.rlujancreations.kmmtemplatemm.navigation

import kotlinx.serialization.Serializable

/**
 * Created by Raúl L.C. on 19/1/25.
 */

@Serializable
sealed class Screen {
    @Serializable
    object Home {
        @Serializable
        object Scaffold
    }
}
