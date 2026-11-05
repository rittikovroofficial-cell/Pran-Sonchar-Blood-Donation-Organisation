package com.example.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.localization.AppLanguage
import com.example.ui.localization.AppStrings
import com.example.ui.theme.BloodRedContainer
import com.example.ui.theme.BloodRedPrimary
import com.example.ui.theme.DarkNavy
import com.example.ui.theme.TextMuted
import com.example.ui.viewmodel.Screen

@Composable
fun PranBottomNav(
  currentScreen: Screen,
  language: AppLanguage,
  onScreenSelected: (Screen) -> Unit
) {
  NavigationBar(
    modifier = Modifier
      .windowInsetsPadding(WindowInsets.navigationBars)
      .testTag("pran_bottom_navigation"),
    containerColor = Color.White,
    tonalElevation = 8.dp
  ) {
    NavigationBarItem(
      selected = currentScreen == Screen.HOME,
      onClick = { onScreenSelected(Screen.HOME) },
      icon = {
        Icon(
          imageVector = Icons.Default.Home,
          contentDescription = AppStrings.navHome(language),
          modifier = Modifier.size(24.dp)
        )
      },
      label = {
        Text(
          text = AppStrings.navHome(language),
          fontSize = 11.sp,
          fontWeight = if (currentScreen == Screen.HOME) FontWeight.Bold else FontWeight.Normal
        )
      },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = BloodRedPrimary,
        selectedTextColor = BloodRedPrimary,
        indicatorColor = BloodRedContainer,
        unselectedIconColor = TextMuted,
        unselectedTextColor = TextMuted
      )
    )

    NavigationBarItem(
      selected = currentScreen == Screen.DONORS,
      onClick = { onScreenSelected(Screen.DONORS) },
      icon = {
        Icon(
          imageVector = Icons.Default.People,
          contentDescription = AppStrings.navDonors(language),
          modifier = Modifier.size(24.dp)
        )
      },
      label = {
        Text(
          text = AppStrings.navDonors(language),
          fontSize = 11.sp,
          fontWeight = if (currentScreen == Screen.DONORS) FontWeight.Bold else FontWeight.Normal
        )
      },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = BloodRedPrimary,
        selectedTextColor = BloodRedPrimary,
        indicatorColor = BloodRedContainer,
        unselectedIconColor = TextMuted,
        unselectedTextColor = TextMuted
      )
    )

    NavigationBarItem(
      selected = currentScreen == Screen.REQUESTS,
      onClick = { onScreenSelected(Screen.REQUESTS) },
      icon = {
        Icon(
          imageVector = Icons.Default.WaterDrop,
          contentDescription = AppStrings.navRequests(language),
          modifier = Modifier.size(24.dp)
        )
      },
      label = {
        Text(
          text = AppStrings.navRequests(language),
          fontSize = 11.sp,
          fontWeight = if (currentScreen == Screen.REQUESTS) FontWeight.Bold else FontWeight.Normal
        )
      },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = BloodRedPrimary,
        selectedTextColor = BloodRedPrimary,
        indicatorColor = BloodRedContainer,
        unselectedIconColor = TextMuted,
        unselectedTextColor = TextMuted
      )
    )

    NavigationBarItem(
      selected = currentScreen == Screen.CAMPAIGNS,
      onClick = { onScreenSelected(Screen.CAMPAIGNS) },
      icon = {
        Icon(
          imageVector = Icons.Default.Campaign,
          contentDescription = AppStrings.navCampaigns(language),
          modifier = Modifier.size(24.dp)
        )
      },
      label = {
        Text(
          text = AppStrings.navCampaigns(language),
          fontSize = 11.sp,
          fontWeight = if (currentScreen == Screen.CAMPAIGNS) FontWeight.Bold else FontWeight.Normal
        )
      },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = BloodRedPrimary,
        selectedTextColor = BloodRedPrimary,
        indicatorColor = BloodRedContainer,
        unselectedIconColor = TextMuted,
        unselectedTextColor = TextMuted
      )
    )

    NavigationBarItem(
      selected = currentScreen == Screen.DASHBOARD,
      onClick = { onScreenSelected(Screen.DASHBOARD) },
      icon = {
        Icon(
          imageVector = Icons.Default.Person,
          contentDescription = AppStrings.navDashboard(language),
          modifier = Modifier.size(24.dp)
        )
      },
      label = {
        Text(
          text = AppStrings.navDashboard(language),
          fontSize = 11.sp,
          fontWeight = if (currentScreen == Screen.DASHBOARD) FontWeight.Bold else FontWeight.Normal
        )
      },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = BloodRedPrimary,
        selectedTextColor = BloodRedPrimary,
        indicatorColor = BloodRedContainer,
        unselectedIconColor = TextMuted,
        unselectedTextColor = TextMuted
      )
    )
  }
}
