package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
  primary = BloodRedPrimary,
  onPrimary = OnBloodRed,
  primaryContainer = BloodRedContainer,
  onPrimaryContainer = BloodRedDark,
  secondary = DarkNavy,
  onSecondary = OnDarkNavy,
  secondaryContainer = DarkNavyContainer,
  onSecondaryContainer = DarkNavy,
  tertiary = BloodRedLight,
  onTertiary = PureWhite,
  background = LightBackground,
  onBackground = TextPrimary,
  surface = PureWhite,
  onSurface = TextPrimary,
  surfaceVariant = Color(0xFFF1F5F9),
  onSurfaceVariant = TextSecondary,
  outline = CardBorder
)

private val DarkColorScheme = darkColorScheme(
  primary = BloodRedLight,
  onPrimary = PureWhite,
  primaryContainer = BloodRedDark,
  onPrimaryContainer = BloodRedContainer,
  secondary = Color(0xFF94A3B8),
  onSecondary = DarkBackground,
  secondaryContainer = DarkNavyLight,
  onSecondaryContainer = PureWhite,
  background = DarkBackground,
  onBackground = DarkTextPrimary,
  surface = DarkSurface,
  onSurface = DarkTextPrimary,
  surfaceVariant = Color(0xFF1E293B),
  onSurfaceVariant = DarkTextSecondary,
  outline = DarkCardBorder
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
