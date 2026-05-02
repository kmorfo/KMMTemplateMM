package es.rlujancreacions.core.navigation.deeplinks

import android.content.Intent
import android.net.Uri
import es.rlujancreacions.core.navigation.AppRoute

/**
 * Extracts a deep link [AppRoute] from an Android [Intent].
 *
 * Usage in MainActivity:
 * ```kotlin
 * val deepLinkRoute = intent.toDeepLinkRoute()
 * setContent {
 *     App(deepLinkRoute = deepLinkRoute)
 * }
 * ```
 *
 * Handles:
 * - Intent.ACTION_VIEW with a URI matching [DEEP_LINK_HOST]
 * - onNewIntent for foreground deep links
 */
fun Intent.toDeepLinkRoute(): AppRoute? =
    if (action == Intent.ACTION_VIEW) AppDeepLinks.parse(data?.toString()) else null

/**
 * Build an Android deep link [Intent] for the given [AppRoute].
 * Useful for testing or creating shortcuts.
 */
fun AppRoute.toDeepLinkIntent(): Intent = Intent(Intent.ACTION_VIEW).apply {
    data = Uri.parse(AppDeepLinks.buildUri(this@toDeepLinkIntent))
}
