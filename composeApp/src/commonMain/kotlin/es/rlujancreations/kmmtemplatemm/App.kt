package es.rlujancreations.kmmtemplatemm

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import es.rlujancreations.kmmtemplatemm.navigation.NavigationRoot
import org.koin.compose.KoinContext

@Composable
fun App() {
    KoinContext {
        MaterialTheme {
            val navController = rememberNavController()

            NavigationRoot(
                navController = navController,
            )
        }
    }
}
