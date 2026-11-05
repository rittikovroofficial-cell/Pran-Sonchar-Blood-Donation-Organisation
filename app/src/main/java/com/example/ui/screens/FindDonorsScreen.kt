package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import com.example.data.model.BangladeshLocations
import com.example.data.model.BloodGroup
import com.example.ui.components.DonorCard
import com.example.ui.localization.AppLanguage
import com.example.ui.localization.AppStrings
import com.example.ui.theme.BloodRedPrimary
import com.example.ui.theme.DarkNavy
import com.example.ui.theme.StatusAvailable
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindDonorsScreen(
  viewModel: MainViewModel,
  onNavigateToScreen: (Screen) -> Unit
) {
  val language by viewModel.language.collectAsState()
  val filteredDonors by viewModel.filteredDonors.collectAsState()
  val searchQuery by viewModel.searchQuery.collectAsState()
  val selectedGroup by viewModel.selectedBloodGroupFilter.collectAsState()
  val selectedDistrict by viewModel.selectedDistrictFilter.collectAsState()
  val onlyAvailable by viewModel.filterOnlyAvailable.collectAsState()

  var districtDropdownExpanded by remember { mutableStateOf(false) }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF7F8FA))
      .testTag("find_donors_screen_column"),
    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
  ) {
    // 1. Search Box
    item {
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { viewModel.searchQuery.value = it },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("donor_search_input"),
        placeholder = {
          Text(
            text = if (language == AppLanguage.BENGALI)
              "রক্তদাতার নাম, উপজেলা বা এলাকা লিখুন..."
            else
              "Search by name, upazila, or area...",
            fontSize = 13.sp
          )
        },
        leadingIcon = {
          Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = BloodRedPrimary
          )
        },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
            IconButton(onClick = { viewModel.searchQuery.value = "" }) {
              Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
            }
          }
        },
        shape = RoundedCornerShape(12.dp),
        singleLine = true
      )

      Spacer(modifier = Modifier.height(10.dp))
    }

    // 2. Blood Group Filters
    item {
      Column {
        Text(
          text = AppStrings.filterBloodGroup(language),
          fontWeight = FontWeight.SemiBold,
          fontSize = 12.sp,
          color = TextSecondary
        )
        Spacer(modifier = Modifier.height(4.dp))
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          item {
            FilterChip(
              selected = selectedGroup == null,
              onClick = { viewModel.selectedBloodGroupFilter.value = null },
              label = { Text(if (language == AppLanguage.BENGALI) "সকল গ্রুপ" else "All") },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = BloodRedPrimary,
                selectedLabelColor = Color.White
              ),
              modifier = Modifier.testTag("filter_chip_all_groups")
            )
          }
          items(BloodGroup.entries) { group ->
            FilterChip(
              selected = selectedGroup == group.display,
              onClick = {
                viewModel.selectedBloodGroupFilter.value =
                  if (selectedGroup == group.display) null else group.display
              },
              label = { Text(group.display) },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = BloodRedPrimary,
                selectedLabelColor = Color.White
              ),
              modifier = Modifier.testTag("filter_chip_${group.display}")
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))
    }

    // 3. District Picker & Availability Toggle
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // District Dropdown
        ExposedDropdownMenuBox(
          expanded = districtDropdownExpanded,
          onExpandedChange = { districtDropdownExpanded = !districtDropdownExpanded },
          modifier = Modifier.weight(1f)
        ) {
          OutlinedTextField(
            value = selectedDistrict ?: (if (language == AppLanguage.BENGALI) "সকল জেলা" else "All Districts"),
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = districtDropdownExpanded) },
            leadingIcon = {
              Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = BloodRedPrimary,
                modifier = Modifier.size(18.dp)
              )
            },
            modifier = Modifier
              .menuAnchor()
              .fillMaxWidth()
              .testTag("district_filter_dropdown"),
            shape = RoundedCornerShape(10.dp),
            textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp)
          )

          ExposedDropdownMenu(
            expanded = districtDropdownExpanded,
            onDismissRequest = { districtDropdownExpanded = false }
          ) {
            DropdownMenuItem(
              text = { Text(if (language == AppLanguage.BENGALI) "সকল জেলা" else "All Districts") },
              onClick = {
                viewModel.selectedDistrictFilter.value = null
                districtDropdownExpanded = false
              }
            )
            BangladeshLocations.districts.forEach { districtItem ->
              val label = if (language == AppLanguage.BENGALI) districtItem.nameBn else districtItem.nameEn
              DropdownMenuItem(
                text = { Text(label) },
                onClick = {
                  viewModel.selectedDistrictFilter.value = districtItem.nameEn
                  districtDropdownExpanded = false
                }
              )
            }
          }
        }

        // Only Available Switch
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(start = 4.dp)
        ) {
          Column {
            Text(
              text = if (language == AppLanguage.BENGALI) "প্রস্তুত" else "Ready",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = DarkNavy
            )
          }
          Spacer(modifier = Modifier.width(6.dp))
          Switch(
            checked = onlyAvailable,
            onCheckedChange = { viewModel.filterOnlyAvailable.value = it },
            colors = SwitchDefaults.colors(
              checkedThumbColor = Color.White,
              checkedTrackColor = StatusAvailable
            ),
            modifier = Modifier.testTag("filter_only_available_switch")
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))
    }

    // 4. Results Header
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "${filteredDonors.size} ${if (language == AppLanguage.BENGALI) "জন রক্তদাতা পাওয়া গেছে" else "Donors Found"}",
          fontWeight = FontWeight.Bold,
          fontSize = 14.sp,
          color = DarkNavy
        )

        if (selectedGroup != null || selectedDistrict != null || searchQuery.isNotBlank() || onlyAvailable) {
          Text(
            text = if (language == AppLanguage.BENGALI) "ফিল্টার মুছুন" else "Reset",
            fontSize = 12.sp,
            color = BloodRedPrimary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.clickable {
              viewModel.selectedBloodGroupFilter.value = null
              viewModel.selectedDistrictFilter.value = null
              viewModel.searchQuery.value = ""
              viewModel.filterOnlyAvailable.value = false
            }
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))
    }

    // 5. Donors List or Empty State
    if (filteredDonors.isEmpty()) {
      item {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
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
              imageVector = Icons.Default.FilterList,
              contentDescription = null,
              tint = TextSecondary,
              modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
              text = if (language == AppLanguage.BENGALI)
                "নির্বাচিত ফিল্টারে কোনো রক্তদাতা পাওয়া যায়নি"
              else
                "No donors found with current filters",
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              color = DarkNavy
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = if (language == AppLanguage.BENGALI)
                "অন্যান্য জেলা বা রক্তগ্রুপ নির্বাচন করে দেখুন অথবা নিজে রক্তদাতা হিসেবে যোগ দিন।"
              else
                "Try clearing filters or register yourself as a voluntary donor.",
              fontSize = 12.sp,
              color = TextSecondary,
              modifier = Modifier.padding(horizontal = 16.dp),
              lineHeight = 18.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
              onClick = { onNavigateToScreen(Screen.REGISTER_DONOR) },
              colors = ButtonDefaults.buttonColors(containerColor = BloodRedPrimary),
              shape = RoundedCornerShape(8.dp)
            ) {
              Icon(
                imageVector = Icons.Default.PersonAdd,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = AppStrings.becomeDonorBtn(language),
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }
    } else {
      items(filteredDonors) { donor ->
        DonorCard(
          donor = donor,
          language = language,
          modifier = Modifier.padding(bottom = 10.dp)
        )
      }
    }
  }
}
