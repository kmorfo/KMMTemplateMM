---
name: android-error-handling
description: |
  Generic Result wrapper, error types, and extension helpers for Android/KMP - Result<T, E>, DataError, EmptyResult, map, onSuccess, onError, asEmptyDataResult. Use this skill whenever defining error types, creating a Result wrapper, handling success/failure flows, mapping errors, or working with typed errors anywhere in the app (not just data layer — also validation, auth, domain logic). Trigger on phrases like "Result wrapper", "error handling", "DataError", "onSuccess", "onError", "asEmptyDataResult", "map result", "error type", "validation error", or "typed errors".
---

# Android / KMP Error Handling

## Result Wrapper (`core:domain`, `util` package)

A generic, typed Result that works across all layers — data, domain, presentation, validation, anywhere a function can succeed or fail with a typed error. This is the real definition in `core/domain/src/commonMain/.../util/Result.kt` — use these exact names:

```kotlin
interface Error

sealed interface Result<out D, out E : Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : es.rlujancreations.core.domain.util.Error>(val error: E) : Result<Nothing, E>
}

typealias EmptyResult<E> = Result<Unit, E>
```

The nested `Result.Error` class shadows the top-level `Error` interface, so its type
parameter bound needs the fully-qualified name — this is not a typo to "fix".

---

## Extension Helpers (`core:domain`)

These live alongside the `Result` definition. **Names matter — use exactly these, not
`onFailure`/`asEmptyResult`, which don't exist in this codebase:**

```kotlin
inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> =
    when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }

inline fun <T, E : Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> =
    when (this) {
        is Result.Error -> this
        is Result.Success -> { action(data); this }
    }

inline fun <T, E : Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> =
    when (this) {
        is Result.Error -> { action(error); this }
        is Result.Success -> this
    }

fun <T, E : Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> = map { }
```

All helpers return `Result` so they can be chained:
```kotlin
repository.saveNote(note)
    .onSuccess { /* update UI */ }
    .onError { /* show error */ }
    .asEmptyDataResult()
```

---

## Shared Error Types (`core:domain`, `util` package)

### DataError

`DataError` is a `sealed interface DataError : Error` with `Network` and `Local` enums
(`core/domain/src/commonMain/.../util/DataError.kt`). Add new HTTP/local failure cases
there as the app needs them — do not invent parallel error types for cases that already
fit `DataError`.

### Feature-Specific Errors

Non-network/local domain errors define their own type by implementing `Error`:

```kotlin
enum class PasswordValidationError : Error {
    TOO_SHORT,
    NO_UPPERCASE,
    NO_DIGIT
}

// Used with Result — always a single error, not a list:
fun validatePassword(pw: String): EmptyResult<PasswordValidationError>
```

Multiple validation errors are not supported — always return a single error type per Result.

---

## Exception Handling Philosophy

Never throw exceptions for expected failures — always return `Result.Error`. Catch exceptions at the layer that is responsible for the exception:

| Exception origin | Catch in | Example |
|---|---|---|
| HTTP / network | Data layer | `io.ktor.util.network.UnresolvedAddressException` → `DataError.Network.NO_INTERNET` |
| Database / disk | Data layer | Room/SQLite exception → `DataError.Local.DISK_FULL` |
| Business logic | Domain layer | Invalid input → `Result.Error(ValidationError.TOO_SHORT)` |
| Presentation | Presentation layer | Catch and map to `Result.Error` at that layer |

The layer that owns the exception catches it and converts it to a typed `Result.Error`. Upper layers never see raw exceptions for expected failures.

Note: use Ktor's own `io.ktor.util.network.UnresolvedAddressException`, not
`java.nio.channels.UnresolvedAddressException` — the JVM one doesn't exist on
Kotlin/Native (iOS), so importing it breaks the iOS build.

---

## Mapping Errors to UiText

`UiText` (`core:presentation`, `ui` package) wraps a Compose Multiplatform string
resource, not an Android resource `Int` id — this project has no `R.string`:

```kotlin
sealed interface UiText {
    data class DynamicString(val value: String) : UiText

    class StringResource(
        val resId: org.jetbrains.compose.resources.StringResource,
        val args: Array<Any> = arrayOf(),
    ) : UiText

    @Composable
    fun asString(): String  // uses org.jetbrains.compose.resources.stringResource(resId, *args)

    fun asString(stringProvider: StringProvider): String  // for non-composable contexts
}
```

`asString(stringProvider)` exists for the rare case where you need the resolved string
outside composition (e.g. a background notification); it goes through the
`StringProvider`/`GetStringProvider` `expect`/`actual` pair in `core:presentation`, one
implementation per platform. Prefer the `@Composable asString()` overload whenever
you're already in a composable.

Every error type that is displayed to the user should have a `.toUiText()`/`.asUiText()`
extension function. Place it in:

- **Feature's `presentation` module** — if the error is feature-specific
- **`core:presentation`** — if the error is shared across features (this is where
  `DataError.asUiText()` already lives, in `DataErrorToText.kt`)

If an error is purely internal and never shown to the user (e.g., a retry signal, an internal state marker), it does not need a UI text mapping.

```kotlin
// core:presentation — abridged, see DataErrorToText.kt for the full mapping
fun DataError.asUiText(): UiText = when (this) {
    DataError.Network.NO_INTERNET -> UiText.StringResource(Res.string.error_no_internet)
    DataError.Network.SERVER_ERROR -> UiText.StringResource(Res.string.error_server_error)
    DataError.Local.DISK_FULL -> UiText.StringResource(Res.string.error_disk_full)
    // ... map all user-facing cases
    else -> UiText.StringResource(Res.string.error_unknown)
}
```

`Res.string.*` comes from the Compose Multiplatform resources plugin (generated from
`composeResources/values/strings.xml` in the module that owns the string) — add new
strings there, not to an Android-only `res/values/strings.xml`.

---

## Safe Call Helpers (`core:data`, `networking` package)

Typed extension functions on `HttpClient` wrap Ktor calls and map responses to
`Result<T, DataError.Network>` — see `HttpClientExt.kt` for the exact implementation:

```kotlin
suspend inline fun <reified Response : Any> HttpClient.get(
    route: String,
    queryParameters: Map<String, String> = mapOf(),
): Result<Response, DataError.Network> = safeCall {
    get(route) {
        queryParameters.forEach { (key, value) -> parameter(key, value) }
    }
}

// post, patch, delete follow the same shape
```

There is no manual base-URL prefixing (`constructRoute`) — `route` is a relative path,
and the base URL is installed once via `defaultRequest { url(baseUrl) }` in
`HttpClientFactory` (`core:data`); Ktor resolves relative paths against it.

```kotlin
suspend inline fun <reified T> safeCall(execute: () -> HttpResponse): Result<T, DataError.Network> {
    val response = try {
        execute()
    } catch (e: io.ktor.util.network.UnresolvedAddressException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.NO_INTERNET)
    } catch (e: SerializationException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.SERIALIZATION)
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        e.printStackTrace()
        return Result.Error(DataError.Network.UNKNOWN)
    }

    return responseToResult(response)
}
```

Usage in a data source is clean and uniform:
```kotlin
suspend fun getNotes(): Result<List<NoteDto>, DataError.Network> {
    return httpClient.get(route = "notes")
}
```

---

## When to Use What

| Scenario | Error type | Example return |
|---|---|---|
| Network call | `DataError.Network` | `Result<List<NoteDto>, DataError.Network>` |
| Local DB access | `DataError.Local` | `Result<Note, DataError.Local>` |
| Repository (multi-source) | `DataError` (supertype) | `Result<List<Note>, DataError>` |
| Domain validation | Custom `Error` enum | `EmptyResult<PasswordValidationError>` |
| Auth logic | Custom `Error` enum | `Result<User, AuthError>` |

The `Result` wrapper is not limited to the data layer — use it anywhere a function has typed success and failure outcomes.
