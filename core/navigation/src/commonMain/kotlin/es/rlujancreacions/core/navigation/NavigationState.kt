package es.rlujancreacions.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.savedstate.serialization.SavedStateConfiguration

@Composable
fun rememberNavigationState(
    startRoute: AppRoute = AppRoute.Home,
    deepLinkRoute: AppRoute? = null,
): NavigationState {
    val savedStateConfig = SavedStateConfiguration {
        serializersModule = appRouteSerializersModule()
    }

    // Index into topLevelRoutes survives process death; doesn't need the savedstate serialization APIs.
    val startIndex = topLevelRoutes.indexOfFirst { it::class == startRoute::class }.coerceAtLeast(0)
    val topLevelIndexState: MutableState<Int> = rememberSaveable { mutableStateOf(startIndex) }

    val backStacks: Map<AppRoute, NavBackStack<NavKey>> = topLevelRoutes.associateWith { route ->
        rememberNavBackStack(configuration = savedStateConfig, route)
    }

    val state = remember(startRoute) {
        NavigationState(
            startRoute = startRoute,
            topLevelIndexState = topLevelIndexState,
            backStacks = backStacks,
        )
    }

    // Navigate to deep link destination on first composition
    deepLinkRoute?.let { target ->
        remember(target) {
            state.handleDeepLink(target)
            true
        }
    }

    return state
}

class NavigationState(
    val startRoute: AppRoute,
    private val topLevelIndexState: MutableState<Int>,
    val backStacks: Map<AppRoute, NavBackStack<NavKey>>,
) {
    private var topLevelIndex: Int by topLevelIndexState

    var topLevelRoute: AppRoute
        get() = topLevelRoutes.getOrElse(topLevelIndex) { startRoute }
        set(value) {
            topLevelIndex = topLevelRoutes.indexOfFirst { it::class == value::class }.coerceAtLeast(0)
        }

    val currentTopLevelRoute: AppRoute get() = topLevelRoute

    @Composable
    fun toEntries(
        entryProvider: (NavKey) -> NavEntry<NavKey>,
    ): List<NavEntry<NavKey>> =
        activeRoutes().flatMap { route ->
            backStacks[route]?.map { key -> entryProvider(key) } ?: emptyList()
        }

    // The start route stack is always active ("exit through home" pattern).
    // A second tab can be layered on top of it.
    private fun activeRoutes(): List<AppRoute> =
        if (currentTopLevelRoute == startRoute) {
            listOf(startRoute)
        } else {
            listOf(startRoute, currentTopLevelRoute)
        }

    internal fun handleDeepLink(target: AppRoute) {
        if (target.isTopLevel()) {
            topLevelRoute = target
        } else {
            backStacks[startRoute]?.add(target)
        }
    }
}
