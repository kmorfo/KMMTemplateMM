package es.rlujancreacions.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Serializable
sealed interface AppRoute : NavKey {

    @Serializable
    data object Home : AppRoute

    @Serializable
    data object Explore : AppRoute

    @Serializable
    data object Favorites : AppRoute

    @Serializable
    data object Profile : AppRoute

    @Serializable
    data class Detail(val id: String) : AppRoute

    @Serializable
    data class Search(
        val query: String? = null,
        val category: String? = null,
    ) : AppRoute
}

val topLevelRoutes: List<AppRoute> = listOf(
    AppRoute.Home,
    AppRoute.Explore,
    AppRoute.Favorites,
    AppRoute.Profile,
)

fun AppRoute.isTopLevel(): Boolean = topLevelRoutes.any { it::class == this::class }

fun appRouteSerializersModule() = SerializersModule {
    polymorphic(NavKey::class) {
        subclass(AppRoute.Home::class, AppRoute.Home.serializer())
        subclass(AppRoute.Explore::class, AppRoute.Explore.serializer())
        subclass(AppRoute.Favorites::class, AppRoute.Favorites.serializer())
        subclass(AppRoute.Profile::class, AppRoute.Profile.serializer())
        subclass(AppRoute.Detail::class, AppRoute.Detail.serializer())
        subclass(AppRoute.Search::class, AppRoute.Search.serializer())
    }
}
