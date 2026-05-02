package es.rlujancreacions.core.navigation

import androidx.compose.runtime.staticCompositionLocalOf

val LocalNavigator = staticCompositionLocalOf<Navigator> {
    error("No Navigator provided. Wrap your content with NavigationRoot.")
}

class Navigator(val state: NavigationState) {

    fun navigate(route: AppRoute) {
        if (state.backStacks.containsKey(route)) {
            state.topLevelRoute = route
        } else {
            state.backStacks[state.currentTopLevelRoute]?.add(route)
        }
    }

    fun goBack() {
        val currentStack = state.backStacks[state.currentTopLevelRoute] ?: return
        if (currentStack.size <= 1) {
            if (state.currentTopLevelRoute != state.startRoute) {
                state.topLevelRoute = state.startRoute
            }
        } else {
            currentStack.removeLastOrNull()
        }
    }

    fun navigateUp() = goBack()

    fun popTo(route: AppRoute, inclusive: Boolean = false) {
        val currentStack = state.backStacks[state.currentTopLevelRoute] ?: return
        val index = currentStack.indexOfLast { it == route }
        if (index < 0) return
        val cutAt = if (inclusive) index else index + 1
        while (currentStack.size > cutAt) {
            currentStack.removeLastOrNull()
        }
    }

    fun popToRoot() {
        val currentStack = state.backStacks[state.currentTopLevelRoute] ?: return
        while (currentStack.size > 1) {
            currentStack.removeLastOrNull()
        }
    }
}
