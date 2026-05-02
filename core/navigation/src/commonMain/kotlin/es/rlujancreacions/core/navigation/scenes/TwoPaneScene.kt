package es.rlujancreacions.core.navigation.scenes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.scene.Scene
import androidx.navigation3.scene.SceneStrategy
import androidx.navigation3.scene.SceneStrategyScope
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND

/**
 * A [Scene] that renders two [NavEntry]s side-by-side in a 50/50 split.
 *
 * Activated when the window is at least [WIDTH_DP_MEDIUM_LOWER_BOUND] wide and the
 * top two entries both carry [TwoPaneScene.twoPane] metadata.
 */
data class TwoPaneScene<T : Any>(
    override val key: Any,
    override val previousEntries: List<NavEntry<T>>,
    val firstEntry: NavEntry<T>,
    val secondEntry: NavEntry<T>,
) : Scene<T> {
    override val entries: List<NavEntry<T>> = listOf(firstEntry, secondEntry)
    override val content: @Composable () -> Unit = {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.weight(0.5f)) {
                firstEntry.Content()
            }
            Column(modifier = Modifier.weight(0.5f)) {
                secondEntry.Content()
            }
        }
    }

    companion object {
        const val TWO_PANE_KEY = "twoPaneScene"
        fun twoPane(): Map<String, Any> = mapOf(TWO_PANE_KEY to true)
    }
}

@Composable
fun <T : Any> rememberTwoPaneSceneStrategy(): TwoPaneSceneStrategy<T> {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    return remember(windowSizeClass) { TwoPaneSceneStrategy(windowSizeClass) }
}

class TwoPaneSceneStrategy<T : Any>(
    private val windowSizeClass: WindowSizeClass,
) : SceneStrategy<T> {

    override fun SceneStrategyScope<T>.calculateScene(entries: List<NavEntry<T>>): Scene<T>? {
        if (!windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)) return null

        val lastTwo = entries.takeLast(2)
        if (lastTwo.size != 2) return null
        if (!lastTwo.all { it.metadata.containsKey(TwoPaneScene.TWO_PANE_KEY) }) return null

        return TwoPaneScene(
            key = Pair(lastTwo.first().contentKey, lastTwo.last().contentKey),
            previousEntries = entries.dropLast(1),
            firstEntry = lastTwo.first(),
            secondEntry = lastTwo.last(),
        )
    }
}
