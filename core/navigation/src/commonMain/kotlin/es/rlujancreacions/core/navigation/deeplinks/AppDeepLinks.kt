package es.rlujancreacions.core.navigation.deeplinks

import es.rlujancreacions.core.navigation.AppRoute

const val DEEP_LINK_SCHEME = "https"
const val DEEP_LINK_HOST = "example.com"

/**
 * Registered deep link patterns and their mapping to [AppRoute].
 *
 * Pattern examples:
 *   https://example.com/home
 *   https://example.com/explore
 *   https://example.com/detail/{id}
 *   https://example.com/search?query={query}&category={category}
 */
object AppDeepLinks {

    fun parse(uriString: String?): AppRoute? {
        if (uriString.isNullOrBlank()) return null
        return try {
            parseInternal(uriString)
        } catch (_: Exception) {
            null
        }
    }

    private fun parseInternal(uriString: String): AppRoute? {
        // Basic URI parsing without platform-specific libraries
        val withoutScheme = uriString
            .removePrefix("$DEEP_LINK_SCHEME://")
            .removePrefix("$DEEP_LINK_SCHEME://")

        if (!withoutScheme.startsWith(DEEP_LINK_HOST)) return null

        val pathAndQuery = withoutScheme.removePrefix(DEEP_LINK_HOST)
        val queryStart = pathAndQuery.indexOf('?')
        val path = if (queryStart >= 0) pathAndQuery.substring(0, queryStart) else pathAndQuery
        val queryString = if (queryStart >= 0) pathAndQuery.substring(queryStart + 1) else ""

        val segments = path.trim('/').split('/').filter { it.isNotEmpty() }
        val queryParams = parseQueryParams(queryString)

        return when {
            segments.isEmpty() || segments.first() == "home" -> AppRoute.Home
            segments.first() == "explore" -> AppRoute.Explore
            segments.first() == "favorites" -> AppRoute.Favorites
            segments.first() == "profile" -> AppRoute.Profile
            segments.first() == "detail" && segments.size >= 2 ->
                AppRoute.Detail(id = segments[1])
            segments.first() == "search" ->
                AppRoute.Search(
                    query = queryParams["query"],
                    category = queryParams["category"],
                )
            else -> null
        }
    }

    private fun parseQueryParams(queryString: String): Map<String, String> {
        if (queryString.isBlank()) return emptyMap()
        return queryString.split('&')
            .mapNotNull { param ->
                val eq = param.indexOf('=')
                if (eq > 0) param.substring(0, eq) to param.substring(eq + 1)
                else null
            }
            .toMap()
    }

    /** Build a deep link URI for the given [route]. */
    fun buildUri(route: AppRoute): String {
        val path = when (route) {
            is AppRoute.Home -> "home"
            is AppRoute.Explore -> "explore"
            is AppRoute.Favorites -> "favorites"
            is AppRoute.Profile -> "profile"
            is AppRoute.Detail -> "detail/${route.id}"
            is AppRoute.Search -> {
                val params = listOfNotNull(
                    route.query?.let { "query=$it" },
                    route.category?.let { "category=$it" },
                ).joinToString("&")
                if (params.isNotEmpty()) "search?$params" else "search"
            }
        }
        return "$DEEP_LINK_SCHEME://$DEEP_LINK_HOST/$path"
    }
}
