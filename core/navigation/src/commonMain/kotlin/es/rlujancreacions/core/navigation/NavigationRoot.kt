package es.rlujancreacions.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import es.rlujancreacions.core.navigation.animations.defaultEnterTransition
import es.rlujancreacions.core.navigation.animations.defaultPopTransition
import es.rlujancreacions.core.navigation.scenes.rememberTwoPaneSceneStrategy

/**
 * Root navigation composable for the app.
 *
 * Features:
 * - Multi-backstack: each bottom-nav tab retains its own history
 * - Two-pane layout: side-by-side panels on medium/large screens (≥ 600 dp)
 * - Custom slide animations for forward/back navigation
 * - Deep link support via [deepLinkRoute]
 * - Modular: feature entries are contributed via [featureEntries]
 *
 * @param startRoute First destination shown when no deep link is active.
 * @param deepLinkRoute Optional route resolved from an incoming deep link.
 * @param navBarItems Bottom-navigation items; omit to hide the bar entirely.
 * @param featureEntries Lambda where each feature module registers its screens
 *   using the [EntryProviderScope] DSL.
 */
@Composable
fun NavigationRoot(
    startRoute: AppRoute = AppRoute.Home,
    deepLinkRoute: AppRoute? = null,
    navBarItems: List<NavBarItem> = emptyList(),
    featureEntries: EntryProviderScope<NavKey>.() -> Unit,
) {
    val navState = rememberNavigationState(
        startRoute = startRoute,
        deepLinkRoute = deepLinkRoute,
    )
    val navigator = remember(navState) { Navigator(navState) }
    val twoPaneStrategy = rememberTwoPaneSceneStrategy<NavKey>()

    val dslProvider = entryProvider<NavKey> { featureEntries() }
    val builtEntryProvider: (NavKey) -> NavEntry<NavKey> = remember(dslProvider) {
        { key ->
            runCatching { dslProvider(key) }.getOrElse {
                NavEntry(key) {
                    Box(Modifier.fillMaxSize(), Alignment.Center) {
                        Text(
                            text = "No screen registered for: ${key::class.simpleName}",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }

    CompositionLocalProvider(LocalNavigator provides navigator) {
        Scaffold(
            bottomBar = {
                if (navBarItems.isNotEmpty()) {
                    NavigationBar {
                        navBarItems.forEach { item ->
                            NavigationBarItem(
                                selected = navState.currentTopLevelRoute::class == item.route::class,
                                onClick = { navigator.navigate(item.route) },
                                icon = { Icon(item.icon, contentDescription = item.label) },
                                label = { Text(item.label) },
                            )
                        }
                    }
                }
            },
        ) { innerPadding ->
            NavDisplay(
                entries = navState.toEntries(builtEntryProvider),
                onBack = { navigator.goBack() },
                sceneStrategy = twoPaneStrategy,
                modifier = Modifier.padding(innerPadding),
                transitionSpec = { defaultEnterTransition },
                popTransitionSpec = { defaultPopTransition },
                predictivePopTransitionSpec = { _ -> defaultPopTransition },
            )
        }
    }
}

/**
 * Represents a bottom-navigation bar tab.
 */
data class NavBarItem(
    val route: AppRoute,
    val icon: ImageVector,
    val label: String,
)
