---
name: android-di-koin
description: |
  Koin dependency injection setup for Android/KMP - module definitions per core/feature module, expect/actual platform bindings, the shared initKoin() entry point, and koinViewModel() in composables. Use this skill whenever setting up Koin, defining a DI module, providing a repository or ViewModel, injecting a dependency, or wiring modules for a new platform entry point. Trigger on phrases like "set up Koin", "add a Koin module", "inject a dependency", "DI module", "koinViewModel", "provide a ViewModel", "initKoin", or "single/viewModel/factory".
---

# Android / KMP Dependency Injection (Koin)

## Principles

- One Koin module per module/layer that has dependencies to provide — create it only if there's something to bind.
- All modules are assembled in **one shared place**: `initKoin()` in `composeApp`'s `di/CommonMainDi.kt` (`commonMain`) — never in an Android-only `Application` class.
- Platform-specific bindings (an engine, a database driver, a Context-backed provider) are expressed with `expect`/`actual` Koin modules, one per source set, not with `if (platform == ...)` branching inside a single module.
- In Compose root composables, always inject ViewModels via `koinViewModel()`.

---

## Module Definitions

Prefer the constructor-reference overloads (`singleOf`, `viewModelOf`, `factoryOf`) — they are more concise and let Koin resolve parameters automatically. Only fall back to the lambda overloads (`single { ... }`, `viewModel { ... }`, `factory { ... }`) when constructor injection alone is not enough, e.g. when you need to call a factory method, pass a named/qualified dependency, or do post-construction setup — like `coreDataModule` does below for the API-keyed `HttpClient`.

### A `core:*` module (today's reality — no `:feature:*` modules exist yet)

```kotlin
// core:data — core/data/src/commonMain/.../di/CoreDataModule.kt
expect val platformCoreDataModule: Module

val coreDataModule = module {
    single(named(HttpsClients.CO_SERVER)) {
        HttpClientFactory(apiKey = API_KEY, baseUrl = BASE_URL, engine = get()).build()
    }
}
```

### Expect/actual platform binding

Anything that needs a platform-specific implementation (an HTTP engine, a DB driver, a
`Context`-backed provider) is its own `expect val`/`actual val` Koin module, bound in
per-platform source sets:

```kotlin
// core/data/src/commonMain/.../di/CoreDataModule.kt
expect val platformCoreDataModule: Module

// core/data/src/androidMain/.../di/CoreDataModule.android.kt
actual val platformCoreDataModule = module {
    single<HttpClientEngine> { OkHttp.create() }
}

// core/data/src/iosMain/.../di/CoreDataModule.ios.kt
actual val platformCoreDataModule = module {
    single<HttpClientEngine> { Darwin.create() }
}
```

### Future feature module (once one exists)

```kotlin
// feature:notes:data
val notesDataModule = module {
    singleOf(::RoomNoteRepository) { bind<NoteRepository>() }
}

// feature:notes:presentation
val notesPresentationModule = module {
    viewModelOf(::NoteListViewModel)
    viewModelOf(::NoteDetailViewModel)
}
```

---

## Assembly: `initKoin()` in `composeApp` (`commonMain`)

All modules — core and, later, feature — are registered in one shared function, not
in an Android `Application`:

```kotlin
// composeApp/src/commonMain/.../di/CommonMainDi.kt
expect val commonNativeModules: List<Module>

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            *commonNativeModules.toTypedArray(),
            coreDataModule,
            platformCoreDataModule,
            coreDatabaseModule,
            databaseNativeModule,
            mainViewModelModule,
            navigationModule,
            coreUiModule,
            // notesDataModule, notesPresentationModule, ... as features are added
        )
    }
}
```

Each platform entry point just calls `initKoin`, passing platform-only Koin config
through the optional `KoinAppDeclaration` lambda — it never wraps `startKoin` itself:

```kotlin
// androidApp — Application.onCreate()
class KmmTemplateMMApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@KmmTemplateMMApp)
        }
    }
}
```

```kotlin
// composeApp iosMain — MainViewController.kt
fun MainViewController() = ComposeUIViewController(configure = { initKoin() }) { App() }
```

Desktop's `main.kt` calls `initKoin()` the same way, with no extra platform config.

---

## Injecting in Composables

Always use `koinViewModel()` (from `org.koin.compose.viewmodel.koinViewModel`, the
Compose Multiplatform package — not `org.koin.androidx.compose`) in Root composables:

```kotlin
@Composable
fun NoteListRoot(
    onNavigateToDetail: (String) -> Unit,
    viewModel: NoteListViewModel = koinViewModel()
) { ... }
```

Never pass ViewModels down the composable tree — inject at the Root level only.

---

## Scoping Rules

| Scope | Preferred form | Fallback form | When to use |
|---|---|---|---|
| Singleton | `singleOf(::Impl) { bind<Interface>() }` | `single { ... }` | One instance for the app lifetime (repositories, HttpClient, DB) |
| ViewModel | `viewModelOf(::MyViewModel)` | `viewModel { ... }` | ViewModel instances scoped to their lifecycle |
| Factory | `factoryOf(::Impl)` | `factory { ... }` | New instance on every injection (rare — prefer singleton or ViewModel) |
| Platform-specific | `expect val platformXModule: Module` + one `actual` per source set | — | Anything that needs a different implementation per target |

Use the `*Of` constructor-reference form by default. Only use the lambda form when you cannot express the binding with a constructor reference (factory methods, named qualifiers, manual setup).

---

## Naming Conventions

| Thing | Convention | Example |
|---|---|---|
| Koin module (core) | `core<Concern>Module` | `coreDataModule`, `coreDatabaseModule`, `coreUiModule` |
| Koin module (platform binding) | `platform<Concern>Module` / `<concern>NativeModule` | `platformCoreDataModule`, `databaseNativeModule` |
| Koin module (future feature) | `<feature><Layer>Module` | `notesDataModule`, `notesPresentationModule` |

---

## Checklist: Adding DI for a New Module

- [ ] Define `val core<Concern>Module = module { ... }` (or `<feature><Layer>Module` once feature modules exist)
- [ ] If a dependency needs a platform-specific implementation, add `expect val platform<Concern>Module: Module` in `commonMain` and one `actual val` per source set that needs it
- [ ] Register the new module(s) in `initKoin()` (`composeApp/.../di/CommonMainDi.kt`)
- [ ] Use `koinViewModel()` in all Root composables
