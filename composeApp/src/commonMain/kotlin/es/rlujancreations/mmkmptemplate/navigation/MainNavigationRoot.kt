package es.rlujancreations.mmkmptemplate.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import es.rlujancreacions.core.navigation.AppRoute
import es.rlujancreacions.core.navigation.LocalNavigator
import es.rlujancreacions.core.navigation.NavBarItem
import es.rlujancreacions.core.navigation.NavigationRoot

@Composable
fun MainNavigationRoot(deepLinkRoute: AppRoute? = null) {
    NavigationRoot(
        startRoute = AppRoute.Home,
        deepLinkRoute = deepLinkRoute,
        navBarItems = listOf(
            NavBarItem(AppRoute.Home, Icons.Default.Home, "Home"),
            NavBarItem(AppRoute.Explore, Icons.Filled.Place, "Explore"),
            NavBarItem(AppRoute.Favorites, Icons.Default.Favorite, "Favorites"),
            NavBarItem(AppRoute.Profile, Icons.Default.Person, "Profile"),
        ),
    ) {
        // Feature entries are registered here as the app grows.
        // Example:
        entry<AppRoute.Home> {
            val navigator = LocalNavigator.current
            Scaffold {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text("Hello my KMP DEVS")
                    Button(
                        onClick = {
                            navigator.navigate(AppRoute.Detail("PePe detail"))
                        },
                    ) {
                        Text("Go to detail")
                    }
                }
            }
        }
        entry<AppRoute.Detail> { key ->
            val navigator = LocalNavigator.current
            Scaffold {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text("Detail ${key.id}")
                    Button(
                        onClick = {
                            navigator.goBack()
                        },
                    ) {
                        Text("Go to back home")
                    }
                }
            }
        }
    }
}
