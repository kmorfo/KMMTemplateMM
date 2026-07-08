---
name: android-navigation
description: |
  JetBrains Navigation 3 for Compose Multiplatform (Android/iOS/Desktop) - centralized AppRoute definitions, the Navigator/LocalNavigator API, registering screens with entry<Route>, multi-backstack tabs, per-destination animations, two-pane scenes, and deep links, all living in :core:navigation. Use this skill whenever setting up navigation, adding a route, registering a screen, navigating between screens, adding a bottom-nav tab, or wiring a deep link. Trigger on phrases like "add a route", "navigate to", "AppRoute", "Navigator", "NavigationRoot", "entry provider", "bottom nav tab", "deep link", or "Navigation3".
---

# Navigation (JetBrains Navigation 3)

This project centralizes navigation in `:core:navigation` using **JetBrains Navigation 3**
(`org.jetbrains.androidx.navigation3`), not the classic `androidx.navigation`
NavController/NavGraphBuilder/NavHost API. See `core/navigation/NAVIGATION.md` for the
full reference; this skill covers the day-to-day patterns.

## Principles

- **All routes live in one place**: `AppRoute.kt` in `:core:navigation`, as a single
  `sealed interface AppRoute : NavKey`. There is no per-feature route file.
- **No NavController.** Navigation is done through `Navigator`, obtained via
  `LocalNavigator.current`, which operates on a `NavigationState` of per-tab back stacks.
- **Screens are registered with `entry<Route> { key -> ... }`**, not `composable<Route>`,
  inside the `featureEntries` lambda passed to `NavigationRoot`.
- **Multi-backstack tabs**: each top-level route (`topLevelRoutes`) keeps its own
  independent history; the start tab stays active underneath the others
  ("exit through home").
- Because routes are already centralized and shared, features call
  `LocalNavigator.current.navigate(AppRoute.X)` directly — there is no callback
  indirection to keep features decoupled from each other's routes like in a
  per-feature-route setup.

---

## Adding a New Route

Add the subclass in `core/navigation/src/commonMain/.../AppRoute.kt` — this is the
**only** file where routes are declared:

```kotlin
@Serializable
sealed interface AppRoute : NavKey {
    // ...existing routes

    @Serializable
    data class ArticleDetail(val articleId: String, val source: String? = null) : AppRoute
}
```

If the route can arrive via deep link or must survive process death, register its
serializer in `appRouteSerializersModule()` in the same file:

```kotlin
subclass(AppRoute.ArticleDetail::class, AppRoute.ArticleDetail.serializer())
```

Use `data object` for parameterless screens, `data class` when the route carries data.

---

## Registering a Screen

Screens are registered inside the `featureEntries` lambda passed to `NavigationRoot`
(today assembled directly in `composeApp`'s `MainNavigationRoot.kt`; larger features
should expose their own `EntryProviderScope<NavKey>.() -> Unit` extension function and
be composed there, mirroring the old "one nav graph per feature" idea):

```kotlin
// feature-owned entry registration, composed into NavigationRoot's featureEntries
fun EntryProviderScope<NavKey>.articleEntries() {
    entry<AppRoute.ArticleDetail> { key ->
        // key is AppRoute.ArticleDetail; parameters are accessed directly, no casting
        ArticleDetailScreen(articleId = key.articleId, source = key.source)
    }
}
```

```kotlin
// composeApp
NavigationRoot(startRoute = AppRoute.Home, navBarItems = navBarItems) {
    articleEntries()
    // ...other feature entries
}
```

---

## Navigating from a Composable

`Navigator` is exposed via `LocalNavigator`:

```kotlin
val navigator = LocalNavigator.current
```

| Method | Effect |
|---|---|
| `navigate(route)` | Switches tab if `route` is top-level; otherwise pushes onto the current tab's stack |
| `goBack()` / `navigateUp()` | Pops one screen; returns to the start tab if the current stack has only one entry |
| `popTo(route, inclusive)` | Removes entries until `route`; `inclusive = true` also removes `route` |
| `popToRoot()` | Clears the current tab's stack down to its root |

```kotlin
navigator.navigate(AppRoute.Detail(id = "42"))
navigator.goBack()
navigator.popTo(AppRoute.Home, inclusive = false)
navigator.popToRoot()
```

Avoid passing complex objects through a route — pass IDs and load data in the
destination's ViewModel.

---

## Adding a Bottom-Nav Tab (Top-Level Route)

1. Add the `data object` to `AppRoute.kt` and to `topLevelRoutes`.
2. Add a `NavBarItem` where `navBarItems` is built (`MainNavigationRoot.kt`):

```kotlin
NavBarItem(AppRoute.Library, Icons.Default.Book, "Library"),
```

3. Register its screen via `entry<AppRoute.Library> { ... }`.

---

## Per-Destination Animations

The default horizontal slide applies to every destination automatically. Override it
per entry with the `metadata` parameter, using helpers from
`animations/NavTransitions.kt`:

```kotlin
entry<AppRoute.Detail>(metadata = verticalSlideMetadata()) { key ->
    DetailScreen(id = key.id)
}

entry<AppRoute.Search>(metadata = fadeMetadata()) { key ->
    SearchScreen(query = key.query)
}
```

## Two-Pane Layout

Two consecutive destinations that both carry `TwoPaneScene.twoPane()` metadata render
side-by-side (50/50) on windows ≥ 600 dp:

```kotlin
entry<AppRoute.Explore>(metadata = TwoPaneScene.twoPane()) { ExploreListScreen() }
entry<AppRoute.Detail>(metadata = TwoPaneScene.twoPane() + verticalSlideMetadata()) { key ->
    DetailScreen(id = key.id)
}
```

---

## Deep Links

`AppDeepLinks` (KMP, `deeplinks/AppDeepLinks.kt`) parses/builds URIs into `AppRoute`.
Adding a new one means a branch in `parseInternal()` and `buildUri()`. On Android,
`Intent.toDeepLinkRoute()` and `AppRoute.toDeepLinkIntent()`
(`deeplinks/IntentDeepLinkHandler.kt`, androidMain-only) bridge to/from `Intent`.
`AppDeepLinks` is exposed as a Koin singleton via `navigationModule`
(`di/NavigationModule.kt`) for injection into ViewModels.

---

## Naming Conventions

| Thing | Convention | Example |
|---|---|---|
| Route | `AppRoute.<Screen>` | `AppRoute.Detail`, `AppRoute.Search` |
| Feature entry registration | `<feature>Entries()` on `EntryProviderScope<NavKey>` | `articleEntries()` |

---

## Checklist: Adding a New Screen

- [ ] Declare the route in `core/navigation/AppRoute.kt` (`data object`/`data class`)
- [ ] Register its serializer in `appRouteSerializersModule()` if it needs deep links or process-death survival
- [ ] Register the screen with `entry<AppRoute.X> { ... }`, in a feature-owned `EntryProviderScope<NavKey>` extension when the feature is non-trivial
- [ ] Wire that entry function into `NavigationRoot`'s `featureEntries` block
- [ ] If it's a new tab: add to `topLevelRoutes` and add a `NavBarItem`
- [ ] Navigate to it with `LocalNavigator.current.navigate(AppRoute.X(...))`
