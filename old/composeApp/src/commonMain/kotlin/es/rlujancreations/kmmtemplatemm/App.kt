package es.rlujancreations.kmmtemplatemm

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import es.rlujancreations.kmmtemplatemm.navigation.NavigationRoot
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
        MaterialTheme {
            val navController = rememberNavController()
            val viewModel = koinViewModel<MainViewModel>()

            NavigationRoot(
                navController = navController,
            )
        }
    }
