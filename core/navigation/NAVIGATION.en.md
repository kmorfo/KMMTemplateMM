# `:core:navigation` Module

KMP module (Android + iOS) that centralises all app navigation using
**JetBrains Navigation 3** (`org.jetbrains.androidx.navigation3:navigation3-ui:1.0.0-alpha06`).

---

## Table of contents

1. [Architecture](#architecture)
2. [Routes — AppRoute](#routes--approute)
3. [Adding a new screen](#adding-a-new-screen)
4. [Navigating from a Composable](#navigating-from-a-composable)
5. [Adding a new top-level route (tab)](#adding-a-new-top-level-route-tab)
6. [Per-destination custom animations](#per-destination-custom-animations)
7. [Two-pane layout (TwoPaneScene)](#two-pane-layout-twopanescene)
8. [Deep links](#deep-links)
9. [Koin DI](#koin-di)
10. [File reference](#file-reference)

---

## Architecture

```
NavigationRoot            ← Composable entry point
   │
   ├── NavigationState   ← persistent state; one NavBackStack per tab
   ├── Navigator         ← navigation API; exposed via LocalNavigator
   ├── TwoPaneSceneStrategy ← adaptive layout on screens ≥ 600 dp
   └── NavDisplay        ← entry rendering + animations
```

**Multi-backstack**: each bottom-bar tab keeps its own independent history.
The start tab (`startRoute`) is always active underneath; a second tab is layered
on top of it following the "exit through home" pattern.

State survives configuration changes and process death via `rememberSaveable`
+ `rememberNavBackStack` with `SavedStateConfiguration`.

---

## Routes — AppRoute

All app routes are declared in `AppRoute.kt` as subclasses of `AppRoute : NavKey`.
Adding or renaming routes happens **only in that file**.

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

Top-level routes (tabs) are listed in `topLevelRoutes`:

```kotlin
val topLevelRoutes = listOf(
    AppRoute.Home,
    AppRoute.Explore,
    AppRoute.Favorites,
    AppRoute.Profile,
)
```

---

## Adding a new screen

### 1. Declare the route

In `AppRoute.kt`, add a new subclass. Use `data class` if it carries parameters:

```kotlin
@Serializable
data class ArticleDetail(val articleId: String, val source: String? = null) : AppRoute
```

If the route may arrive via deep link or needs to survive process death, also
register its serializer in `appRouteSerializersModule()` in the same file:

```kotlin
subclass(AppRoute.ArticleDetail::class, AppRoute.ArticleDetail.serializer())
```

### 2. Register the screen in NavigationRoot

In `MainNavigationRoot.kt` (or wherever `NavigationRoot` is used), add an `entry`
inside the `featureEntries` block:

```kotlin
NavigationRoot(...) {
    entry<AppRoute.Home> {
        HomeScreen()
    }
    entry<AppRoute.Detail> { key ->
        // key is AppRoute.Detail; parameters are accessed directly
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

> The `entry` lambda type is already `@Composable (K) -> Unit`, so `key` inside
> the body is the typed route instance — no casting required.

### 3. Navigate to the new screen

```kotlin
val navigator = LocalNavigator.current
navigator.navigate(AppRoute.ArticleDetail(articleId = "abc-123"))
```

---

## Navigating from a Composable

`Navigator` is exposed to the whole Compose tree via `LocalNavigator`:

```kotlin
val navigator = LocalNavigator.current
```

### Available methods

| Method | Effect |
|---|---|
| `navigate(route)` | Navigates to `route`. If top-level, switches tab; otherwise pushes onto the current tab's stack. |
| `goBack()` / `navigateUp()` | Goes back one screen. If the stack has only one entry, returns to the start tab. |
| `popTo(route, inclusive)` | Removes entries until `route` is reached. `inclusive = true` removes `route` too. |
| `popToRoot()` | Clears the current tab's stack, leaving only the root screen. |

```kotlin
// navigate forward
navigator.navigate(AppRoute.Detail(id = "42"))

// go back
navigator.goBack()

// pop back to Home without removing it
navigator.popTo(AppRoute.Home, inclusive = false)

// clear the current tab
navigator.popToRoot()
```

---

## Adding a new top-level route (tab)

1. **Declare the `data object`** in `AppRoute.kt`:

```kotlin
@Serializable data object Library : AppRoute
```

2. **Add it to `topLevelRoutes`**:

```kotlin
val topLevelRoutes = listOf(
    AppRoute.Home,
    AppRoute.Explore,
    AppRoute.Favorites,
    AppRoute.Profile,
    AppRoute.Library,   // ← new
)
```

3. **Add a `NavBarItem`** in `MainNavigationRoot.kt`:

```kotlin
NavBarItem(AppRoute.Library, Icons.Default.Book, "Library"),
```

4. **Register its screen** in the `featureEntries` block (see previous section).

---

## Per-destination custom animations

Global animations (horizontal slide) are applied automatically to every
destination. To override them for a specific screen, pass the `metadata`
parameter when registering the `entry`:

### Vertical slide (modal style)

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

### Fully custom transition

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

Reusable transitions are defined in `animations/NavTransitions.kt`:
`defaultEnterTransition`, `defaultPopTransition`, `verticalSlideInTransition`,
`verticalSlideOutTransition`, `fadeTransition`.

---

## Two-pane layout (TwoPaneScene)

On tablets or windows at least **600 dp wide**, two consecutive destinations
that both carry `TwoPaneScene.twoPane()` metadata will be rendered side-by-side
in a 50/50 split.

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

On small screens the behaviour is unchanged (full screen). Detection is done
via `WindowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)`.

---

## Deep links

### Base URL

```
https://example.com/<path>
```

| Path | Resulting AppRoute |
|---|---|
| `/home` | `AppRoute.Home` |
| `/explore` | `AppRoute.Explore` |
| `/favorites` | `AppRoute.Favorites` |
| `/profile` | `AppRoute.Profile` |
| `/detail/{id}` | `AppRoute.Detail(id)` |
| `/search?query=x&category=y` | `AppRoute.Search(query, category)` |

### Adding a new deep link

In `AppDeepLinks.kt`, inside `parseInternal()`, add a `when` branch:

```kotlin
segments.first() == "library" -> AppRoute.Library
```

And in `buildUri()`:

```kotlin
is AppRoute.Library -> "library"
```

### Android — receiving a deep link from an Intent

In `MainActivity`:

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
        // For foreground deep links, reload the activity or
        // update state with the new route.
    }
}
```

Also declare the intent-filter in `AndroidManifest.xml`:

```xml
<intent-filter android:autoVerify="true">
    <action android:name="android.intent.action.VIEW" />
    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.BROWSABLE" />
    <data android:scheme="https" android:host="example.com" />
</intent-filter>
```

### Generating a test Intent (Android)

```kotlin
import es.rlujancreacions.core.navigation.deeplinks.toDeepLinkIntent

val intent = AppRoute.Detail(id = "42").toDeepLinkIntent()
startActivity(intent)
```

---

## Koin DI

`navigationModule` is registered in `initKoin()` inside `CommonMainDi.kt` and
exposes `AppDeepLinks` as a singleton:

```kotlin
val navigationModule = module {
    single { AppDeepLinks }
}
```

To inject `AppDeepLinks` into a ViewModel:

```kotlin
class SearchViewModel(
    private val deepLinks: AppDeepLinks,
) : ViewModel()

// in the Koin module:
viewModelOf(::SearchViewModel)
```

---

## File reference

| File | Responsibility |
|---|---|
| `AppRoute.kt` | All route definitions + serialization module |
| `NavigationRoot.kt` | Root Composable; bottom bar + `NavDisplay` |
| `NavigationState.kt` | Persistent state; one `NavBackStack` per tab |
| `Navigator.kt` | Public navigation API + `LocalNavigator` |
| `animations/NavTransitions.kt` | Reusable transitions and metadata helpers |
| `scenes/TwoPaneScene.kt` | Two-pane scene + adaptive strategy |
| `deeplinks/AppDeepLinks.kt` | URI parser and builder (KMP) |
| `deeplinks/IntentDeepLinkHandler.kt` | Android `Intent` extensions (androidMain) |
| `di/NavigationModule.kt` | Navigation Koin module |
