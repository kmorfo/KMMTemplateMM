package es.rlujancreations.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

@Composable
fun KmmTemplateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
//    val colorScheme = if(darkTheme) DarkColorScheme else LightColorScheme
//    val extendedScheme = if(darkTheme) DarkExtendedColors else LightExtendedColors

//    CompositionLocalProvider(LocalExtendedColors provides extendedScheme) {
//        MaterialTheme(
//            colorScheme = colorScheme,
//            typography = Typography,
//            content = content
//        )
//    }
}
