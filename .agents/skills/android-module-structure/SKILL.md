---
name: android-module-structure
description: Module layout, dependency rules, and Gradle convention plugins for this Kotlin Multiplatform (Android/iOS/Desktop) project. Use this skill whenever setting up a new module, deciding where code should live, asking "how should I structure this", creating a new feature module, adding a core submodule, configuring Gradle convention plugins, working with version catalogs, or making any decision about project-level architecture. Trigger on phrases like "set up the project", "add a module", "create a feature", "how should I structure", "project structure", "convention plugin", "build-logic", or "where does X live".
---

# KMP Module Structure

## Core Philosophy

- **Platform apps are thin shells.** `:androidApp` and `:iosApp` only host the platform
  entry point (an `Application`/`Activity`, a SwiftUI wrapper) — real UI, navigation,
  and DI wiring live in `:composeApp` (`commonMain`, shared across Android/iOS/Desktop).
- **Clean Architecture layers**: `presentation` → `domain` ← `data`. Domain is innermost and depends on nothing.
- **No `:feature:*` modules exist yet** — this project currently has only `core`
  modules plus the three app shells. The layering rules below are what to follow once
  a concern grows large enough to become its own feature module; until then, new code
  goes in the matching `core` module or directly in `composeApp`.
- Anything shared by more than one future feature belongs in the appropriate `core:*` module, not duplicated.

---

## Module Layout (current)

```
:androidApp                     ← Application + MainActivity only
:composeApp                     ← Shared UI (commonMain/androidMain/iosMain/desktopMain), initKoin(), App.kt
:iosApp                         ← Xcode project; Swift wrapper around composeApp's MainViewController
:build-logic                    ← Gradle convention plugins
:core:domain                    ← Result/Error/DataError, domain models, repository interfaces
:core:data                      ← Ktor HttpClient factory + safe-call helpers, shared data logic
:core:database                  ← Room @Database, entities, DAOs, migrations (expect/actual DatabaseFactory)
:core:presentation              ← Shared UI utilities: UiText, ObserveAsEvents, StringProvider
:core:designsystem               ← Reusable Compose Multiplatform components, KmmTemplateTheme, typography
:core:navigation                ← JetBrains Navigation 3: AppRoute, Navigator, NavigationRoot (see android-navigation skill)
:commonTest                      ← Shared test fixtures/fakes (kotlin.test-based), mirrors core module packages
```

Once a concern is large enough to be its own feature (multiple classes, meaningful UI,
non-trivial domain logic), give it `:feature:<name>:domain`, `:feature:<name>:data`,
`:feature:<name>:presentation` modules following the same layering as `core`. Don't
create a feature module, or a new `core` submodule, for a single class or trivial
utility — that belongs in an existing module instead.

---

## Dependency Rules

| Layer | May depend on |
|---|---|
| `presentation` | `domain` (own feature), `core:domain`, `core:presentation`, `core:designsystem` |
| `data` | `domain` (own feature), `core:domain`, `core:data` |
| `domain` | `core:domain` only — never `data` or `presentation` |
| `composeApp` | everything (wires all modules, hosts `initKoin()` and the navigation root) |

**Every** layer and module may access `core:domain`. `androidApp`/`iosApp` depend on
`composeApp` only — they should never need to reach into `core:*` directly.

---

## Convention Plugins (`:build-logic`)

Real convention plugin IDs, aliased in `libs.versions.toml` under `[plugins]`:

| Plugin alias | Purpose |
|---|---|
| `convention.kmp.library` | Base KMP module: Android (`com.android.kotlin.multiplatform.library`) + iOS + desktop targets, serialization, Koin |
| `convention.cmp.library` | `kmp.library` + Compose Multiplatform (runtime/foundation/material3/resources) |
| `convention.cmp.feature` | `cmp.library` + `core:domain`/`core:designsystem`/`core:presentation` wired in, for feature UI modules |
| `convention.cmp.application` | Compose Multiplatform application module config (`composeApp`) |
| `convention.android.application` | Android application module config (`androidApp`) |
| `convention.android.application.compose` | `android.application` + Compose |
| `convention.koin` | Koin dependency block (`koin-core`/`koin-compose`/`koin-compose-viewmodel` common, `koin-android`/`koin-androidx-compose` on Android) |
| `convention.ktor` | Ktor client + serialization, `okhttp` on Android, `darwin` on iOS |
| `convention.room` | Room + KSP config across all targets |
| `convention.buildkonfig` | BuildKonfig (KMP-wide `BuildConfig` equivalent) |
| `convention.test.unit` | `kotlin.test`/AssertK/Turbine in `commonTest`; `kotlin-test-junit5`/JUnit5 engine + `withHostTest` in `androidHostTest`/`desktopTest` — see android-testing skill |

Use **version catalogs** (`gradle/libs.versions.toml`) for all dependency and version management. No hardcoded versions in build files.

---

## Key Libraries

| Concern | Library |
|---|---|
| DI | Koin (Compose Multiplatform artifacts: `koin-compose`, `koin-compose-viewmodel`) |
| Networking | Ktor Client (OkHttp/Darwin/CIO engines per platform) |
| Local DB | Room (KMP, `androidx.sqlite:sqlite-bundled`) |
| Navigation | JetBrains Navigation 3 (`org.jetbrains.androidx.navigation3`) — see android-navigation skill |
| Serialization | KotlinX Serialization (Ktor + `AppRoute`) |
| Image loading | Coil 3 (`io.coil-kt.coil3`, Ktor network integration) |
| Logging | Napier (`io.github.aakira:napier`) |
| Async | Coroutines + Flow |
| Config/secrets | BuildKonfig (KMP-wide, generates `BuildKonfig.*` per module) |
| Testing | `kotlin.test`, AssertK, Turbine, `kotlinx-coroutines-test`, JUnit5 as the JVM engine — see android-testing skill |
| UI testing | `ComposeTestRule` (Android instrumented/`androidDeviceTest`, JUnit4-based — separate stack from unit tests) |

---

## Checklist: Adding a New Feature Module

- [ ] Create `:feature:<name>:domain`, `:feature:<name>:data`, `:feature:<name>:presentation` modules
- [ ] Apply `convention.kmp.library` to `domain`/`data`, `convention.cmp.feature` to `presentation`
- [ ] Verify no cross-feature dependencies are introduced
- [ ] If logic is shared across 2+ features, extract to the appropriate `core:*` module instead
- [ ] Register the feature's Koin module(s) in `initKoin()` (`composeApp/.../di/CommonMainDi.kt`) — see android-di-koin skill
- [ ] Register the feature's screens/routes — see android-navigation skill
