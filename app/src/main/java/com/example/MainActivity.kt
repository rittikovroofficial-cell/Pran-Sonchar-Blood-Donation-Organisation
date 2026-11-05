package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.ui.components.PranBottomNav
import com.example.ui.components.PranTopBar
import com.example.ui.screens.AdminDashboardScreen
import com.example.ui.screens.CampaignsAndNoticesScreen
import com.example.ui.screens.CreateRequestScreen
import com.example.ui.screens.EmergencyRequestsScreen
import com.example.ui.screens.FindDonorsScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.RegisterDonorScreen
import com.example.ui.screens.UserDashboardScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.Screen

class MainActivity : ComponentActivity() {
  private val viewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    setContent {
      MyApplicationTheme {
        PranSancharApp(viewModel = viewModel)
      }
    }
  }
}

@Composable
fun PranSancharApp(viewModel: MainViewModel) {
  val currentScreen by viewModel.currentScreen.collectAsState()
  val language by viewModel.language.collectAsState()
  val toastMessage by viewModel.toastMessage.collectAsState()
  val isAdminUnlocked by viewModel.isAdminUnlocked.collectAsState()

  val snackbarHostState = remember { SnackbarHostState() }
  var showAdminScreen by remember { mutableStateOf(false) }

  // Handle toast notifications
  LaunchedEffect(toastMessage) {
    toastMessage?.let {
      snackbarHostState.showSnackbar(it)
      viewModel.clearToast()
    }
  }

  val isSubScreen = showAdminScreen || currentScreen == Screen.REGISTER_DONOR || currentScreen == Screen.CREATE_REQUEST || currentScreen == Screen.LOGIN

  // Handle back navigation
  BackHandler(enabled = isSubScreen || currentScreen != Screen.HOME) {
    if (showAdminScreen) {
      showAdminScreen = false
    } else if (currentScreen == Screen.REGISTER_DONOR || currentScreen == Screen.CREATE_REQUEST || currentScreen == Screen.LOGIN) {
      viewModel.navigateTo(Screen.HOME)
    } else {
      viewModel.navigateTo(Screen.HOME)
    }
  }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
      PranTopBar(
        language = language,
        canNavigateBack = isSubScreen,
        onNavigateBack = {
          if (showAdminScreen) {
            showAdminScreen = false
          } else {
            viewModel.navigateTo(Screen.HOME)
          }
        },
        onToggleLanguage = { viewModel.toggleLanguage() },
        isAdminActive = showAdminScreen && isAdminUnlocked
      )
    },
    bottomBar = {
      if (!showAdminScreen && currentScreen != Screen.REGISTER_DONOR && currentScreen != Screen.CREATE_REQUEST && currentScreen != Screen.LOGIN) {
        PranBottomNav(
          currentScreen = currentScreen,
          language = language,
          onScreenSelected = { screen -> viewModel.navigateTo(screen) }
        )
      }
    },
    snackbarHost = { SnackbarHost(snackbarHostState) }
  ) { innerPadding ->
    Box(modifier = Modifier.padding(innerPadding)) {
      if (showAdminScreen) {
        AdminDashboardScreen(
          viewModel = viewModel,
          onExitAdmin = { showAdminScreen = false }
        )
      } else {
        AnimatedContent(
          targetState = currentScreen,
          transitionSpec = { fadeIn() togetherWith fadeOut() },
          label = "screen_transition"
        ) { targetScreen ->
          when (targetScreen) {
            Screen.HOME -> HomeScreen(
              viewModel = viewModel,
              onNavigateToScreen = { screen -> viewModel.navigateTo(screen) },
              onSelectBloodGroup = { group ->
                viewModel.selectedBloodGroupFilter.value = group
              }
            )

            Screen.DONORS -> FindDonorsScreen(
              viewModel = viewModel,
              onNavigateToScreen = { screen -> viewModel.navigateTo(screen) }
            )

            Screen.REQUESTS -> EmergencyRequestsScreen(
              viewModel = viewModel,
              onNavigateToScreen = { screen -> viewModel.navigateTo(screen) }
            )

            Screen.CAMPAIGNS -> CampaignsAndNoticesScreen(
              viewModel = viewModel
            )

            Screen.DASHBOARD -> UserDashboardScreen(
              viewModel = viewModel,
              onNavigateToScreen = { screen -> viewModel.navigateTo(screen) },
              onOpenAdmin = { showAdminScreen = true }
            )

            Screen.REGISTER_DONOR -> RegisterDonorScreen(
              viewModel = viewModel,
              onNavigateBack = { viewModel.navigateTo(Screen.DONORS) }
            )

            Screen.CREATE_REQUEST -> CreateRequestScreen(
              viewModel = viewModel,
              onNavigateBack = { viewModel.navigateTo(Screen.REQUESTS) }
            )

            Screen.LOGIN -> LoginScreen(
              viewModel = viewModel,
              onLoginSuccess = { viewModel.navigateTo(Screen.DASHBOARD) },
              onNavigateToRegister = { viewModel.navigateTo(Screen.REGISTER_DONOR) }
            )
          }
        }
      }
    }
  }
}
