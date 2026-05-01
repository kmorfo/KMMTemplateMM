package es.rlujancreations.mmkmptemplate.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun NavigationRoot() {
    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.Home::class, Route.Home.serializer())
                    subclass(Route.Detail::class, Route.Detail.serializer())
                }
            }
        },
        Route.Home,
    )
    NavDisplay(
        backStack = backStack,
        entryProvider = { key ->
            when (key) {
                is Route.Home -> {
                    NavEntry(key) {
                        Scaffold {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text("Hello my KMP DEVS")
                                Button(
                                    onClick = {
                                        backStack.add(Route.Detail("1"))
                                    },
                                ) {
                                    Text("Go to detail")
                                }
                            }
                        }
                    }
                }

                is Route.Detail -> {
                    NavEntry(key) {
                        Scaffold {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text("Detail")
                                Button(
                                    onClick = {
                                        backStack.removeLastOrNull()
                                    },
                                ) {
                                    Text("Go to back home")
                                }
                            }
                        }
                    }
                }

                else -> error("Unknown NavKey: $key")
            }
        },
    )
}
