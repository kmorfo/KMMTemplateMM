package es.rlujancreations.mmkmptemplate.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationRoot(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home,
    ) {
        homeGraph(navController)
    }
}

private fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    navigation<Screen.Home>(startDestination = Screen.Home.Scaffold) {
        composable<Screen.Home.Scaffold> {
            Text("Hello my KMP DEVS")
        }
    }
}
