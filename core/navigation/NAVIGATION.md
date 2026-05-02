# Módulo `:core:navigation`

Módulo KMP (Android + iOS) que centraliza toda la navegación de la app usando
**JetBrains Navigation 3** (`org.jetbrains.androidx.navigation3:navigation3-ui:1.0.0-alpha06`).

---

## Índice

1. [Arquitectura](#arquitectura)
2. [Rutas — AppRoute](#rutas--approute)
3. [Añadir una nueva pantalla](#añadir-una-nueva-pantalla)
4. [Navegar desde un Composable](#navegar-desde-un-composable)
5. [Añadir una nueva ruta de nivel superior (tab)](#añadir-una-nueva-ruta-de-nivel-superior-tab)
6. [Animaciones personalizadas por destino](#animaciones-personalizadas-por-destino)
7. [Layout de dos paneles (TwoPaneScene)](#layout-de-dos-paneles-twopanescene)
8. [Deep links](#deep-links)
9. [Koin DI](#koin-di)
10. [Referencia de archivos](#referencia-de-archivos)

---

## Arquitectura

```
NavigationRoot            ← punto de entrada Composable
   │
   ├── NavigationState   ← estado persistente; un NavBackStack por tab
   ├── Navigator         ← API de navegación; se expone vía LocalNavigator
   ├── TwoPaneSceneStrategy ← layout adaptativo en pantallas ≥ 600 dp
   └── NavDisplay        ← renderizado de entradas + animaciones
```

**Multi-backstack**: cada tab de la barra inferior mantiene su propio historial
independiente. El tab de inicio (`startRoute`) siempre está activo por debajo;
el segundo tab se apila encima siguiendo el patrón "salir por Home".

El estado sobrevive cambios de configuración y muerte del proceso gracias a
`rememberSaveable` + `rememberNavBackStack` con `SavedStateConfiguration`.

---

## Rutas — AppRoute

Todas las rutas de la app se declaran en `AppRoute.kt` como subclases de
`AppRoute : NavKey`. Añadir o renombrar rutas se hace **solo en ese archivo**.

```kotlin
@Serializable
sealed interface AppRoute : NavKey {

    @Serializable data object Home      : AppRoute
    @Serializable data object Explore   : AppRoute
    @Serializable data object Favorites : AppRoute
    @Serializable data object Profile   : AppRoute

    @Serializable data class Detail(val id: String) : AppRoute

    @Serializable data class Search(
        val query: String? = null,
        val category: String? = null,
    ) : AppRoute
}
```

Las rutas de nivel superior (tabs) se listan en `topLevelRoutes`:

```kotlin
val topLevelRoutes = listOf(
    AppRoute.Home,
    AppRoute.Explore,
    AppRoute.Favorites,
    AppRoute.Profile,
)
```

---

## Añadir una nueva pantalla

### 1. Declarar la ruta

En `AppRoute.kt`, añade una nueva subclase. Si lleva parámetros, usa `data class`:

```kotlin
@Serializable
data class ArticleDetail(val articleId: String, val source: String? = null) : AppRoute
```

Si la ruta puede recibirse como deep link o necesita sobrevivir a muerte del proceso,
registra también su serializador en `appRouteSerializersModule()` del mismo archivo:

```kotlin
subclass(AppRoute.ArticleDetail::class, AppRoute.ArticleDetail.serializer())
```

### 2. Registrar la pantalla en NavigationRoot

En `MainNavigationRoot.kt` (o donde se use `NavigationRoot`), dentro del bloque
`featureEntries`, añade un `entry` para la nueva ruta:

```kotlin
NavigationRoot(...) {
    entry<AppRoute.Home> {
        HomeScreen()
    }
    entry<AppRoute.Detail> { key ->
        // key es AppRoute.Detail; los parámetros se acceden directamente
        DetailScreen(id = key.id)
    }
    entry<AppRoute.ArticleDetail> { key ->
        ArticleDetailScreen(
            articleId = key.articleId,
            source = key.source,
        )
    }
}
```

> El tipo lambda de `entry` ya es `@Composable (K) -> Unit`, por lo que `key`
> en el cuerpo es la instancia tipada de la ruta — no hace falta casting.

### 3. Navegar hacia la nueva pantalla

```kotlin
val navigator = LocalNavigator.current
navigator.navigate(AppRoute.ArticleDetail(articleId = "abc-123"))
```

---

## Navegar desde un Composable

El `Navigator` se expone a todo el árbol Compose a través de `LocalNavigator`:

```kotlin
val navigator = LocalNavigator.current
```

### Métodos disponibles

| Método | Efecto |
|---|---|
| `navigate(route)` | Navega a `route`. Si es top-level, cambia de tab; si no, lo apila en el tab actual. |
| `goBack()` / `navigateUp()` | Retrocede una pantalla. Si el stack tiene solo una entrada, vuelve al tab inicio. |
| `popTo(route, inclusive)` | Elimina entradas hasta llegar a `route`. `inclusive = true` elimina también `route`. |
| `popToRoot()` | Limpia el stack del tab actual dejando solo la pantalla raíz. |

```kotlin
// navegar hacia adelante
navigator.navigate(AppRoute.Detail(id = "42"))

// volver
navigator.goBack()

// volver hasta Home sin incluirlo
navigator.popTo(AppRoute.Home, inclusive = false)

// limpiar el tab actual
navigator.popToRoot()
```

---

## Añadir una nueva ruta de nivel superior (tab)

1. **Declara el `data object`** en `AppRoute.kt`:

```kotlin
@Serializable data object Library : AppRoute
```

2. **Agrégalo a `topLevelRoutes`**:

```kotlin
val topLevelRoutes = listOf(
    AppRoute.Home,
    AppRoute.Explore,
    AppRoute.Favorites,
    AppRoute.Profile,
    AppRoute.Library,   // ← nuevo
)
```

3. **Añade un `NavBarItem`** en `MainNavigationRoot.kt`:

```kotlin
NavBarItem(AppRoute.Library, Icons.Default.Book, "Library"),
```

4. **Registra su pantalla** en el bloque `featureEntries` (ver sección anterior).

---

## Animaciones personalizadas por destino

Las animaciones globales (slide horizontal) se aplican automáticamente a todos
los destinos. Para sobreescribirlas en una pantalla concreta, pasa el parámetro
`metadata` al registrar el `entry`:

### Slide vertical (estilo modal)

```kotlin
import es.rlujancreacions.core.navigation.animations.verticalSlideMetadata

entry<AppRoute.Detail>(metadata = verticalSlideMetadata()) { key ->
    DetailScreen(id = key.id)
}
```

### Fade

```kotlin
import es.rlujancreacions.core.navigation.animations.fadeMetadata

entry<AppRoute.Search>(metadata = fadeMetadata()) { key ->
    SearchScreen(query = key.query)
}
```

### Transición completamente personalizada

```kotlin
import androidx.navigation3.ui.NavDisplay

entry<AppRoute.Profile>(
    metadata = NavDisplay.transitionSpec { myEnterTransition } +
               NavDisplay.popTransitionSpec { myExitTransition } +
               NavDisplay.predictivePopTransitionSpec { _ -> myExitTransition },
) {
    ProfileScreen()
}
```

Las transiciones reutilizables están en
`animations/NavTransitions.kt`: `defaultEnterTransition`, `defaultPopTransition`,
`verticalSlideInTransition`, `verticalSlideOutTransition`, `fadeTransition`.

---

## Layout de dos paneles (TwoPaneScene)

En tablets o ventanas de al menos **600 dp de ancho**, dos destinos consecutivos
que lleven el metadata `TwoPaneScene.twoPane()` se renderizarán en paralelo
en un split 50/50.

```kotlin
entry<AppRoute.Explore>(metadata = TwoPaneScene.twoPane()) {
    ExploreListScreen()
}
entry<AppRoute.Detail>(
    metadata = TwoPaneScene.twoPane() + verticalSlideMetadata(),
) { key ->
    DetailScreen(id = key.id)
}
```

En pantallas pequeñas el comportamiento es el habitual (pantalla completa).
La detección se hace con `WindowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)`.

---

## Deep links

### URL base

```
https://example.com/<ruta>
```

| Ruta | AppRoute resultante |
|---|---|
| `/home` | `AppRoute.Home` |
| `/explore` | `AppRoute.Explore` |
| `/favorites` | `AppRoute.Favorites` |
| `/profile` | `AppRoute.Profile` |
| `/detail/{id}` | `AppRoute.Detail(id)` |
| `/search?query=x&category=y` | `AppRoute.Search(query, category)` |

### Añadir un nuevo deep link

En `AppDeepLinks.kt`, dentro de `parseInternal()`, añade un `when` branch:

```kotlin
segments.first() == "library" -> AppRoute.Library
```

Y en `buildUri()`:

```kotlin
is AppRoute.Library -> "library"
```

### Android — recibir el deep link desde un Intent

En `MainActivity`:

```kotlin
import es.rlujancreacions.core.navigation.deeplinks.toDeepLinkRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val deepLinkRoute = intent.toDeepLinkRoute()
        setContent {
            App(deepLinkRoute = deepLinkRoute)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // Para deep links en foreground recarga la actividad o
        // actualiza el estado con el nuevo route.
    }
}
```

También declara el intent-filter en `AndroidManifest.xml`:

```xml
<intent-filter android:autoVerify="true">
    <action android:name="android.intent.action.VIEW" />
    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.BROWSABLE" />
    <data android:scheme="https" android:host="example.com" />
</intent-filter>
```

### Generar un Intent de prueba (Android)

```kotlin
import es.rlujancreacions.core.navigation.deeplinks.toDeepLinkIntent

val intent = AppRoute.Detail(id = "42").toDeepLinkIntent()
startActivity(intent)
```

---

## Koin DI

`navigationModule` está registrado en `initKoin()` de `CommonMainDi.kt` y expone
`AppDeepLinks` como singleton:

```kotlin
val navigationModule = module {
    single { AppDeepLinks }
}
```

Para inyectar `AppDeepLinks` en un ViewModel:

```kotlin
class SearchViewModel(
    private val deepLinks: AppDeepLinks,
) : ViewModel()

// en el módulo Koin:
viewModelOf(::SearchViewModel)
```

---

## Referencia de archivos

| Archivo | Responsabilidad |
|---|---|
| `AppRoute.kt` | Definición de todas las rutas + módulo de serialización |
| `NavigationRoot.kt` | Composable raíz; barra inferior + `NavDisplay` |
| `NavigationState.kt` | Estado persistente; un `NavBackStack` por tab |
| `Navigator.kt` | API pública de navegación + `LocalNavigator` |
| `animations/NavTransitions.kt` | Transiciones reutilizables y helpers de metadata |
| `scenes/TwoPaneScene.kt` | Escena de dos paneles + estrategia adaptativa |
| `deeplinks/AppDeepLinks.kt` | Parser y builder de URIs (KMP) |
| `deeplinks/IntentDeepLinkHandler.kt` | Extensiones Android para `Intent` (androidMain) |
| `di/NavigationModule.kt` | Módulo Koin del navegador |
