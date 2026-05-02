package es.rlujancreacions.core.navigation.animations

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.navigation3.ui.NavDisplay

private const val TRANSITION_DURATION = 350

/** Default horizontal slide used for forward navigation. */
val defaultEnterTransition: ContentTransform
    get() = slideInHorizontally(
        initialOffsetX = { it },
        animationSpec = tween(TRANSITION_DURATION),
    ) togetherWith slideOutHorizontally(
        targetOffsetX = { -it / 3 },
        animationSpec = tween(TRANSITION_DURATION),
    )

/** Default horizontal slide used for back navigation. */
val defaultPopTransition: ContentTransform
    get() = slideInHorizontally(
        initialOffsetX = { -it / 3 },
        animationSpec = tween(TRANSITION_DURATION),
    ) togetherWith slideOutHorizontally(
        targetOffsetX = { it },
        animationSpec = tween(TRANSITION_DURATION),
    )

/** Vertical slide-up used for modal-style destinations (dialogs, detail cards). */
val verticalSlideInTransition: ContentTransform
    get() = slideInVertically(
        initialOffsetY = { it },
        animationSpec = tween(TRANSITION_DURATION),
    ) togetherWith ExitTransition.None

/** Vertical slide-down used when popping modal-style destinations. */
val verticalSlideOutTransition: ContentTransform
    get() = EnterTransition.None togetherWith slideOutVertically(
        targetOffsetY = { it },
        animationSpec = tween(TRANSITION_DURATION),
    )

/** Fade transition for destinations that should appear in-place (e.g. overlays). */
val fadeTransition: ContentTransform
    get() = fadeIn(tween(TRANSITION_DURATION)) togetherWith fadeOut(tween(TRANSITION_DURATION))

/**
 * Apply vertical slide-up animation to a destination entry.
 *
 * Usage:
 * ```
 * entry<AppRoute.Detail>(metadata = verticalSlideMetadata()) { ... }
 * ```
 */
fun verticalSlideMetadata(): Map<String, Any> =
    NavDisplay.transitionSpec { verticalSlideInTransition } +
    NavDisplay.popTransitionSpec { verticalSlideOutTransition } +
    NavDisplay.predictivePopTransitionSpec { _ -> verticalSlideOutTransition }

/**
 * Apply fade animation to a destination entry.
 */
fun fadeMetadata(): Map<String, Any> =
    NavDisplay.transitionSpec { fadeTransition } +
    NavDisplay.popTransitionSpec { fadeTransition } +
    NavDisplay.predictivePopTransitionSpec { _ -> fadeTransition }
