<p align="center">
  <a href="https://rlujancreations.es/" target="blank"><img src="./githubimages/logo.png" width="300px" alt="RLujanCreations Logo" /></a>
</p>

> [!NOTE]
> 🌐 Este README esta disponible [Español](README.md).

---


# Kotlin Multiplatform Project Template

## Description

This repository is a template for developing multiplatform applications using Kotlin Multiplatform (KMP). It is designed to accelerate the start of new projects by providing a robust and modular initial setup with preconfigured libraries and essential tools.

### Key Features

-   **Multiplatform:** Designed for Android and iOS.
-   **Modular Structure:** Modules are organized in layers for better separation of concerns and scalability.
-   **Convention Plugins:** Uses a declarative and centralized approach for Gradle configuration.
-   **Preconfigured Libraries:**
    -   `ktlint` for code formatting.
    -   `Room` for database management.
    -   `Ktor` for HTTP requests.
    -   `Koin` for dependency injection.
    -   `Compose Navigation` for navigation in declarative interfaces.
    -   `Moko Permissions` for multiplatform permission handling.
    -   `JUnit` for unit testing.
    -   `Napier` is a logger library for Kotlin Multiplatform.

### Included Modules

1.  **composeApp**: Common module and the application entry point.
2.  **core**: Divided into submodules:
    -   `data`: Data access and repositories.
    -   `database`: Database configuration and management.
    -   `domain`: Business logic and use cases.
    -   `presentation`: Shared presentation logic.
3.  **build-logic**: Convention plugins to centralize Gradle configuration.
4.  **commonTest**: Common module for shared unit tests.

## Benefits of Convention Plugins

Convention Plugins are a way to centralize and simplify Gradle project configuration, improving consistency and reducing redundancy. Key benefits include:

-   **Consistency:** Ensures that all common configurations (such as dependency versions or build settings) are defined in a single place.
-   **Scalability:** Facilitates the addition of new modules without duplicating configurations.
-   **Maintainability:** Makes the project easier to maintain as configuration changes need to be made only once.

## Benefits of a Modular Architecture

Modularization is a key approach to building scalable and maintainable software. This project is organized into modules that offer the following benefits:

1.  **Separation of Concerns:** Each module has a clearly defined responsibility, making it easier to understand and develop.
2.  **Code Reusability:** Commonly used modules can be shared across different platforms and projects.
3.  **Simplified Testing:** Tests can be performed in isolation on specific modules.
4.  **Scalability:** Allows large teams to work independently on different modules.

## Requirements

-   Kotlin Multiplatform configured in your development environment.
-   Android Studio (preferably the latest stable version).
-   Xcode for iOS development.

## Setup

1.  Clone this repository:

    ```bash
    git clone https://github.com/kmorfo/KMMTemplateMM

    ```

2.  Sync the project with Gradle to download dependencies.

3.  Configure the target platforms (Android and iOS) as needed.


## Project Structure

```plaintext
project-root/
├── composeApp/       # Application entry point
├── core/
│   ├── data/        # Repositories and data access
│   ├── database/    # Room configuration
│   ├── domain/      # Business logic and use cases
│   └── presentation/ # Presentation logic
├── build-logic/     # Convention plugins
├── commonTest/      # Shared unit tests
└── gradle/         # Gradle configurations

```

## Contribution

Contributions are welcome. Please open an issue or submit a pull request with your improvements.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.
