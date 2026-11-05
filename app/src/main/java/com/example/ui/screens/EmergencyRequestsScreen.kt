package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BloodGroup
import com.example.ui.components.EmergencyRequestCard
import com.example.ui.localization.AppLanguage
import com.example.ui.localization.AppStrings
import com.example.ui.theme.BloodRedPrimary
import com.example.ui.theme.DarkNavy
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.Screen

@Composable
fun EmergencyRequestsScreen(
  viewModel: MainViewModel,
  onNavigateToScreen: (Screen) -> Unit
) {
  val language by viewModel.language.collectAsState()
  val allRequests by viewModel.filteredRequests.collectAsState()
  val selectedGroup by viewModel.requestGroupFilter.collectAsState()

  var selectedUrgency by remember { mutableStateOf<String?>(null) }

  val displayedRequests = allRequests.filter { req ->
    (selectedUrgency == null || req.urgencyLevel == selectedUrgency) &&
        (req.status == "ACTIVE" || req.status == "APPROVED")
  }

  Scaffold(
    floatingActionButton = {
      FloatingActionButton(
        onClick = { onNavigateToScreen(Screen.CREATE_REQUEST) },
        containerColor = BloodRedPrimary,
        contentColor = Color.White,
        modifier = Modifier.testTag("create_blood_request_fab")
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 14.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(imageVector = Icons.Default.Add, contentDescription = "Add Request")
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = if (language == AppLanguage.BENGALI) "রক্তের আবেদন" else "New Request",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
          )
        }
      }
    }
  ) { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF7F8FA))
        .padding(paddingValues)
        .testTag("emergency_requests_lazy_column"),
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
    ) {
      // Header Banner
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = DarkNavy)
        ) {
          Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Warning,
              contentDescription = null,
              tint = Color(0xFFFFCC00),
              modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = if (language == AppLanguage.BENGALI)
                  "জরুরি রক্তের প্রয়োজন"
                else
                  "Live Emergency Blood Requests",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color.White
              )
              Text(
                text = if (language == AppLanguage.BENGALI)
                  "যাচাইকৃত সক্রিয় আবেদনসমূহ। সরাসরি হাসপাতাল ও রোগীর স্বজনের সাথে যোগাযোগ করুন।"
                else
                  "Verified emergency patient requests. Connect directly with hospitals and patient families.",
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.85f),
                lineHeight = 16.sp
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))
      }

      // Filter by Urgency
      item {
        Row(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          FilterChip(
            selected = selectedUrgency == null,
            onClick = { selectedUrgency = null },
            label = { Text(if (language == AppLanguage.BENGALI) "সব আবেদন" else "All") },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = DarkNavy,
              selectedLabelColor = Color.White
            )
          )
          FilterChip(
            selected = selectedUrgency == "EMERGENCY",
            onClick = {
              selectedUrgency = if (selectedUrgency == "EMERGENCY") null else "EMERGENCY"
            },
            label = { Text(if (language == AppLanguage.BENGALI) "তাৎক্ষণিক জরুরি" else "Emergency") },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = BloodRedPrimary,
              selectedLabelColor = Color.White
            )
          )
          FilterChip(
            selected = selectedUrgency == "URGENT",
            onClick = {
              selectedUrgency = if (selectedUrgency == "URGENT") null else "URGENT"
            },
            label = { Text(if (language == AppLanguage.BENGALI) "জরুরি (২৪ ঘণ্টা)" else "Urgent") },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = Color(0xFFF59E0B),
              selectedLabelColor = Color.White
            )
          )
        }

        Spacer(modifier = Modifier.height(8.dp))
      }

      // Blood Group Filter Pills
      item {
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          item {
            FilterChip(
              selected = selectedGroup == null,
              onClick = { viewModel.requestGroupFilter.value = null },
              label = { Text(if (language == AppLanguage.BENGALI) "সকল গ্রুপ" else "All Groups") }
            )
          }
          items(BloodGroup.entries) { group ->
            FilterChip(
              selected = selectedGroup == group.display,
              onClick = {
                viewModel.requestGroupFilter.value =
                  if (selectedGroup == group.display) null else group.display
              },
              label = { Text(group.display) },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = BloodRedPrimary,
                selectedLabelColor = Color.White
              )
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))
      }

      // Requests List or Empty State
      if (displayedRequests.isEmpty()) {
        item {
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 32.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = TextSecondary,
                modifier = Modifier.size(44.dp)
              )
              Spacer(modifier = Modifier.height(12.dp))
              Text(
                text = if (language == AppLanguage.BENGALI)
                  "কোনো রক্তের আবেদন পাওয়া যায়নি"
                else
                  "No active blood requests in this category",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = DarkNavy
              )
              Spacer(modifier = Modifier.height(6.dp))
              Text(
                text = if (language == AppLanguage.BENGALI)
                  "আপনার বা আপনার পরিচিত কারও রক্তের প্রয়োজন হলে এখনই নতুন আবেদন প্রকাশ করুন।"
                else
                  "If you or someone you know requires blood, post an emergency request now.",
                fontSize = 12.sp,
                color = TextSecondary,
                modifier = Modifier.padding(horizontal = 16.dp)
              )
              Spacer(modifier = Modifier.height(16.dp))
              Button(
                onClick = { onNavigateToScreen(Screen.CREATE_REQUEST) },
                colors = ButtonDefaults.buttonColors(containerColor = BloodRedPrimary),
                shape = RoundedCornerShape(8.dp)
              ) {
                Text(AppStrings.emergencyRequestBtn(language))
              }
            }
          }
        }
      } else {
        items(displayedRequests) { request ->
          EmergencyRequestCard(
            request = request,
            language = language,
            modifier = Modifier.padding(bottom = 12.dp)
          )
        }
      }

      // Bottom Spacer for FAB
      item {
        Spacer(modifier = Modifier.height(64.dp))
      }
    }
  }
}
