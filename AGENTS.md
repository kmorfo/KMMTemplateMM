# AGENTS.md

This document is the repository-wide source of truth for how AI coding agents
(Claude Code, Gemini, GitHub Copilot, and any other) should operate in this
repository.

The project is a Kotlin Multiplatform (KMP) application targeting Android, iOS, and
Desktop, sharing domain, data, and UI code across platforms. It follows modern KMP
architecture and development practices, aimed at experienced Kotlin/KMP engineers.
UI is built with Compose Multiplatform, shared in `commonMain` (`composeApp` module),
with thin platform entry points in `androidApp`, `iosApp`, and the desktop target.

## Role and communication

* Write all code in English
* Be clear, brief, and direct
* Prefer modern KMP and Kotlin best practices
* Prioritize maintainability, performance, readability, and strong architecture
* When proposing alternatives, recommend the most robust option first

## General rules

* Do not introduce new architectural patterns unless explicitly requested
* Prefer modifying existing code over introducing parallel implementations
* Keep changes minimal and focused
* Do not refactor unrelated code
* Do not add unnecessary dependencies

## Kotlin and KMP standards

* Use Kotlin as the default language
* Use Compose Multiplatform for UI unless the existing codebase clearly requires Views or a native platform UI
* Use KTS for Gradle files
* Use Version Catalogs for dependencies
* Avoid Groovy examples
* Prefer immutable data structures and `val` over `var`
* Use `data class` where it adds value and matches the domain model
* Prefer constructor injection
* Keep classes focused and small
* Prefer composition over inheritance
* Prefer explicitness over magic
* Avoid unnecessary abstractions
* Keep `commonMain` free of platform-specific imports (e.g. `android.*`, `UIKit`); use `expect`/`actual` for platform code
* Avoid deprecated platform APIs unless the project already depends on them and migration is not requested
* Platform-specific rules (e.g. Android API usage) only apply to code in the corresponding source set (`androidMain`, `iosMain`, `desktopMain`)

## Project architecture

The project follows a Clean Architecture style with clear separation between
layers.

Main layers:

* data
* domain
* presentation

Guidelines:

* Keep clear boundaries between `data`, `domain`, and `presentation`
* Presentation layer contains ViewModels and UI
* UI must not contain business logic
* Business logic belongs in UseCases
* Repositories abstract data sources and hide implementation details from the domain layer
* Prefer unidirectional data flow
* Model UI with explicit state objects
* Prefer sealed hierarchies for UI state and events when useful
* Keep side effects controlled and visible

## UI guidelines

* UI is implemented using Compose Multiplatform, shared in `commonMain`
* Prefer stateless composables when possible
* Hoist state out of composables
* Keep composables small and focused
* Do not place business logic inside composables
* Use `@Preview` only when it adds clear value
* Prefer stable models and avoid unnecessary recomposition
* Use `remember` and `derivedStateOf` only when justified
* Prefer accessibility-friendly APIs and semantics
* Follow existing design system components if available

## ViewModel rules

* ViewModels orchestrate UI state
* ViewModels call UseCases
* ViewModels must not contain heavy business logic
* Expose UI state through immutable models
* Prefer explicit state objects

## UseCase rules

* Each UseCase should represent a single business action
* Prefer `operator fun invoke`
* UseCases should be small and focused
* Avoid platform dependencies (Android, iOS, Desktop) in UseCases; keep them in `commonMain`

Example structure:

```kotlin
class GetUserProfileUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(userId: String): UserProfile {
        return repository.getProfile(userId)
    }
}
```

## Repository rules

* Repositories hide implementation details
* Domain depends on repository interfaces
* Data layer provides repository implementations
* Avoid leaking DTOs into domain models

## Dependency injection

* Prefer constructor injection
* This project uses Koin as its DI framework, with multiplatform modules defined via `expect`/`actual` per source set (see `core/*/di`)
* Do not introduce a new DI framework unless explicitly requested
* Keep modules explicit and easy to navigate

## Coroutines and Flow

* Use Kotlin Coroutines and Flow for asynchronous work
* Prefer `suspend` functions and `Flow` over callbacks
* Make threading decisions explicit
* Avoid blocking calls in coroutines
* Prefer structured concurrency
* Expose observable UI state clearly
* Handle cancellation correctly
* Use `CoroutineStart.UNDISPATCHED` in tests when callback capture requires it

## Error handling

* Handle errors explicitly
* Avoid swallowing exceptions
* Prefer domain-specific error models when appropriate
* Surface failures in a way that the UI can represent consistently
* Do not use exceptions for normal control flow

## Performance and quality

* Prefer efficient and simple solutions
* Avoid unnecessary allocations in hot paths
* Avoid premature optimization but call out relevant performance concerns
* Respect thread confinement rules
* Keep public APIs small and intentional

## Gradle and dependencies

* Use Kotlin DSL (KTS)
* Use Version Catalogs
* Do not use Groovy
* When adding a library show the `libs.versions.toml` entry and the module usage
* Keep dependency additions minimal

## Code style

* Keep lines at 120 characters maximum
* Use meaningful names
* Avoid overly long functions
* When a function or constructor has more than one argument prefer multiline formatting
* Reuse repeated values by extracting them into `val` constants or local variables
* Do not repeat literals when they are used more than once
* Prefer expression clarity over compact clever code

## Testing principles

* Tests for shared code live in each module's `commonTest` source set and must use `kotlin.test` annotations (`@Test`, `@BeforeTest`, `@AfterTest`) so they compile and run on every target, including Kotlin/Native (iOS)
* JUnit5 (Jupiter) is only the JVM execution engine underneath `commonTest` (via the `kotlin-test-junit5` bridge, `androidHostTest`/`desktopTest`); do not import `org.junit.jupiter.api.*` directly in shared test code
* Use AssertK for assertions
* Use Turbine for `Flow`/`StateFlow` testing
* Use `kotlinx-coroutines-test` with `UnconfinedTestDispatcher` for coroutine testing
* Prefer fakes over mocking frameworks for repositories and other collaborators; a fake is a simple in-memory implementation of the interface under test
* Do not introduce a mocking library (e.g. MockK) for shared (`commonTest`) code — Kotlin/Native (iOS) targets cannot run JVM-only mocking frameworks
* Do not use Robolectric
* Do not use `@ExtendWith(RobolectricExtension::class)`
* Do not use `@Config`

## Test naming and structure

* Test names must follow `GIVEN ... WHEN ... THEN ...`
* `GIVEN`, `WHEN`, and `THEN` must appear only in the test name
* Do not write `GIVEN`, `WHEN`, or `THEN` inside the test body
* The test body must contain exactly 3 implicit blocks separated by exactly 2 blank lines
* First block is GIVEN
* Second block is WHEN
* Third block is THEN
* Do not add extra blank lines
* Do not add blank lines inside an implicit block

## Test WHEN block rules

* The WHEN block must assign the result to a variable
* Do not write inline assertions such as `assertThat(deferred.await()).isEqualTo(...)`
* For async tests prefer

    * `val deferred = async(start = CoroutineStart.UNDISPATCHED) { useCase(...) }`
    * `val result = deferred.await()`
* The WHEN block should end with one of these forms

    * `val result = useCase(...)`
    * `val deferred = async(start = CoroutineStart.UNDISPATCHED) { useCase(...) }`
    * `val result = deferred.await()`

## Test SUT rules

* Place the `lateinit var` SUT property immediately before `@BeforeTest`
* Simplify SUT names

    * `FooUseCase` becomes `useCase`
    * `FooRepository` becomes `repository`

## Test setup rules

* Every test class that needs shared fixtures (dispatchers, fakes, SUT) must have `@BeforeTest`
* Use `@BeforeTest` to set the test dispatcher (e.g. `Dispatchers.setMain(UnconfinedTestDispatcher())`) and construct fakes and the SUT
* Use `@AfterTest` to reset dispatchers (e.g. `Dispatchers.resetMain()`) when `@BeforeTest` sets them
* Keep `@BeforeTest` minimal
* Do not add test-specific stubbing in `@BeforeTest`
* Do not add a blank line between the first fake setup line and SUT creation

## Fakes and interaction verification

* Configure fake behavior directly on the fake instance (e.g. `fakeRepository.shouldReturnError = true`) rather than with a mocking DSL
* When verifying that a collaborator was called, expose a simple recorded state on the fake (e.g. a list of received calls, or a nullable `lastX` property) and assert on it with AssertK
* Group related assertions together; avoid scattering assertions on the same fake across the test

## Assertions and test data

* Do not create `val result =` if the result is never used
* Reuse repeated values such as `mac`, `UUID`, payloads, names, or ids by extracting them to `val`
* Prefer explicit expected values
* Keep test data deterministic and readable

## invoke usage

* Replace `.invoke()` with `()`
* In test names use `invoke` only as plain text when relevant

## Boolean test ordering

* When there are two tests for a boolean case write the enabled case first
* Write the disabled case second

## Code modifications

When modifying code:

* Prefer updating existing files instead of duplicating logic
* Keep method signatures stable unless required
* Preserve naming conventions
* Maintain backward compatibility where possible

## When generating new features

When creating a new feature:

* Place UI in the presentation layer
* Place business logic in a UseCase
* Place data access inside repositories
* Keep modules consistent with existing structure

## Output expectations

When generating code:

* Match the existing project architecture and naming if context is available
* Prefer production-ready code over illustrative pseudo-code
* Include only the necessary code
* Do not over-explain obvious Android or Kotlin concepts
* If project context is missing make the safest assumption and explain it briefly in Spanish

When generating tests:

* Follow all the testing rules in this document strictly
* Preserve the exact blank-line structure required by the tests
* Use `kotlin.test`, AssertK, and Turbine consistently
* Keep tests deterministic and easy to read

## When unsure

If project context is missing:

* Make the safest architectural assumption
* Prefer consistency with existing code
* Keep the change minimal
