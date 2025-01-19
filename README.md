<p align="center">
  <a href="https://rlujancreations.es/" target="blank"><img src="./githubimages/logo.png" width="300px" alt="RLujanCreations Logo" /></a>
</p>

> [!NOTE]
> 🌐 This README is also available in [English](README.en.md).

---

# Kotlin Multiplatform Mobile Template Project

## Descripción

Este repositorio es una plantilla para el desarrollo de aplicaciones multiplataforma utilizando
Kotlin Multiplatform (KMP). Ha sido diseñado para acelerar el inicio de nuevos proyectos,
proporcionando una configuración inicial robusta y modular que incluye librerías y herramientas
esenciales ya preconfiguradas.

### Características principales

- **Multiplataforma:** Diseñado para Android e iOS.

- **Estructura modularizada:** Los módulos están organizados en capas para una mejor separación de
  responsabilidades y escalabilidad.

- **Plugins de convenciones (Convention Plugins):** Utiliza un enfoque declarativo y centralizado
  para la configuración de Gradle.

- **Librerías preconfiguradas:**

    - `ktlint` para el formateo del código.

    - `Room` para la gestión de bases de datos.

    - `Ktor` para el manejo de peticiones HTTP.

    - `Koin` para la inyección de dependencias.

    - `Compose Navigation` para la navegación en interfaces declarativas.

    - `Moko Permissions` para la gestión de permisos multiplataforma.

    - `JUnit` para pruebas unitarias.

### Módulos incluidos

1. **composeApp**: Módulo común y punto de entrada de la aplicación.

2. **core**: Dividido en submódulos:

    - `data`: Capa de acceso a datos y repositorios.

    - `database`: Configuración y gestión de la base de datos.

    - `domain`: Capa de lógica de negocio y casos de uso.

    - `presentation`: Lógica de presentación compartida.

3. **build-logic**: Plugins de convenciones para centralizar la configuración de Gradle.

4. **commonTest**: Módulo común para la escritura de pruebas unitarias compartidas.

## Beneficios de los Convention Plugins

Los Convention Plugins son una forma de centralizar y simplificar la configuración de los proyectos
Gradle, mejorando la consistencia y reduciendo la redundancia. Entre sus beneficios destacan:

- **Consistencia:** Garantizan que todas las configuraciones comunes (como la versión de las
  dependencias o las configuraciones de compilación) se definan en un único lugar.

- **Escalabilidad:** Facilitan la adición de nuevos módulos sin duplicar configuraciones.

- **Mantenibilidad:** Hacen que el proyecto sea más fácil de mantener, ya que los cambios en la
  configuración solo se deben realizar una vez.

## Beneficios de una Arquitectura Modular

La modularización es un enfoque clave para el desarrollo de software escalable y mantenible. Este
proyecto está organizado en módulos que ofrecen los siguientes beneficios:

1. **Separación de responsabilidades:** Cada módulo tiene un área de responsabilidad claramente
   definida, lo que facilita la comprensión y el desarrollo.

2. **Reutilización de código:** Los módulos comúnmente utilizados pueden ser compartidos entre
   diferentes plataformas y proyectos.

3. **Pruebas más simples:** Las pruebas se pueden realizar de forma aislada en módulos específicos.

4. **Escalabilidad:** Permite a equipos grandes trabajar en diferentes módulos de manera
   independiente.

## Requisitos

- Kotlin Multiplatform configurado en tu entorno de desarrollo.

- Android Studio (preferiblemente la última versión estable).

- Xcode para desarrollo en iOS.

## Configuración

1. Clona este repositorio:

   ```
   git clone https://github.com/kmorfo/KMMTemplateMM
   ```

2. Sincroniza el proyecto con Gradle para descargar las dependencias.

3. Configura las plataformas objetivo (Android e iOS) según tus necesidades.

## Estructura del Proyecto

```
project-root/
├── composeApp/       # Punto de entrada de la aplicación
├── core/
│   ├── data/        # Repositorios y acceso a datos
│   ├── database/    # Configuración de Room
│   ├── domain/      # Casos de uso y lógica de negocio
│   └── presentation/ # Lógica de presentación
├── build-logic/     # Plugins de convenciones
├── commonTest/      # Pruebas unitarias comunes
└── gradle/         # Configuraciones de Gradle
```

## Contribución

Las contribuciones son bienvenidas. Por favor, abre un issue o envía un pull request con tus
mejoras.

## Licencia

Este proyecto está bajo la licencia MIT. Consulta el archivo `LICENSE` para más detalles.
