package es.rlujancreations.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

/**
 * Created by Raúl L.C. on 19/1/25.
 */

val Project.libs get() = extensions.getByType<VersionCatalogsExtension>().named("libs")