package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Campaign
import com.example.data.model.Notice
import com.example.ui.components.BloodGroupBadge
import com.example.ui.components.StatCard
import com.example.ui.localization.AppLanguage
import com.example.ui.localization.AppStrings
import com.example.ui.theme.BloodRedContainer
import com.example.ui.theme.BloodRedPrimary
import com.example.ui.theme.CardBorder
import com.example.ui.theme.DarkNavy
import com.example.ui.theme.StatusAvailable
import com.example.ui.theme.StatusEmergency
import com.example.ui.theme.StatusFulfilled
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.MainViewModel

@Composable
fun AdminDashboardScreen(
  viewModel: MainViewModel,
  onExitAdmin: () -> Unit
) {
  val language by viewModel.language.collectAsState()
  val isUnlocked by viewModel.isAdminUnlocked.collectAsState()
  val adminPin by viewModel.adminPinInput.collectAsState()

  val allDonors by viewModel.filteredDonors.collectAsState()
  val allRequests by viewModel.filteredRequests.collectAsState()
  val campaigns by viewModel.allCampaigns.collectAsState()
  val notices by viewModel.allNotices.collectAsState()
  val auditLogs by viewModel.allAuditLogs.collectAsState()

  var selectedTab by remember { mutableIntStateOf(0) }

  // Dialogs
  var showNewCampaignDialog by remember { mutableStateOf(false) }
  var campTitle by remember { mutableStateOf("") }
  var campTitleBn by remember { mutableStateOf("") }
  var campLocation by remember { mutableStateOf("") }
  var campDate by remember { mutableStateOf("") }
  var campTarget by remember { mutableStateOf("150") }

  var showNewNoticeDialog by remember { mutableStateOf(false) }
  var noticeTitle by remember { mutableStateOf("") }
  var noticeTitleBn by remember { mutableStateOf("") }
  var noticeContent by remember { mutableStateOf("") }
  var noticeContentBn by remember { mutableStateOf("") }

  if (!isUnlocked) {
    // PIN Authentication Gate
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF7F8FA))
        .padding(24.dp),
      contentAlignment = Alignment.Center
    ) {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("admin_pin_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
      ) {
        Column(
          modifier = Modifier.padding(24.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Box(
            modifier = Modifier
              .size(64.dp)
              .clip(CircleShape)
              .background(DarkNavy),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Shield,
              contentDescription = null,
              tint = Color(0xFFFFCC00),
              modifier = Modifier.size(36.dp)
            )
          }

          Spacer(modifier = Modifier.height(16.dp))

          Text(
            text = if (language == AppLanguage.BENGALI)
              "সুপার অ্যাডমিন সুরক্ষা যাচাই"
            else
              "Super Admin Authentication",
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            color = DarkNavy
          )

          Spacer(modifier = Modifier.height(6.dp))

          Text(
            text = if (language == AppLanguage.BENGALI)
              "শুধুমাত্র সংস্থার অনুমোদিত অ্যাডমিনের জন্য সংরক্ষিত।"
            else
              "Restricted area. Only authorized organization Super Admin can access.",
            fontSize = 12.sp,
            color = TextSecondary,
            lineHeight = 17.sp
          )

          Spacer(modifier = Modifier.height(20.dp))

          OutlinedTextField(
            value = adminPin,
            onValueChange = { viewModel.adminPinInput.value = it },
            label = { Text(AppStrings.enterAdminPin(language)) },
            placeholder = { Text(AppStrings.pinPlaceholder(language)) },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = BloodRedPrimary) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("admin_pin_input"),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
          )

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = if (language == AppLanguage.BENGALI) "ডিফল্ট অ্যাডমিন পিন: 7860" else "Default Admin PIN: 7860",
            fontSize = 11.sp,
            color = BloodRedPrimary,
            fontWeight = FontWeight.Bold
          )

          Spacer(modifier = Modifier.height(20.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            OutlinedButton(
              onClick = { onExitAdmin() },
              modifier = Modifier.weight(1f),
              shape = RoundedCornerShape(10.dp)
            ) {
              Text(if (language == AppLanguage.BENGALI) "ফিরে যান" else "Back")
            }

            Button(
              onClick = { viewModel.verifyAdminPin() },
              modifier = Modifier
                .weight(1.3f)
                .testTag("admin_verify_button"),
              colors = ButtonDefaults.buttonColors(containerColor = BloodRedPrimary),
              shape = RoundedCornerShape(10.dp)
            ) {
              Text(AppStrings.verifyAndLogin(language), fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }
  } else {
    // Unlocked Super Admin Panel
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF7F8FA))
    ) {
      // Admin Header
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(DarkNavy)
          .padding(horizontal = 16.dp, vertical = 12.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = Color(0xFFFFCC00), modifier = Modifier.size(22.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = if (language == AppLanguage.BENGALI) "প্রাণ সঞ্চার কেন্দ্রীয় প্রশাসন" else "Central Admin Console",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.White
              )
              Text(
                text = "Super Admin: Authorized",
                fontSize = 10.sp,
                color = Color.White.copy(alpha = 0.7f)
              )
            }
          }

          Button(
            onClick = {
              viewModel.logoutAdmin()
              onExitAdmin()
            },
            colors = ButtonDefaults.buttonColors(containerColor = BloodRedPrimary),
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier.height(32.dp)
          ) {
            Text(AppStrings.exitAdmin(language), fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }
        }
      }

      // Tabs
      TabRow(
        selectedTabIndex = selectedTab,
        containerColor = Color.White,
        contentColor = BloodRedPrimary
      ) {
        Tab(
          selected = selectedTab == 0,
          onClick = { selectedTab = 0 },
          text = { Text(if (language == AppLanguage.BENGALI) "রক্তদাতা (${allDonors.size})" else "Donors (${allDonors.size})", fontSize = 11.sp) }
        )
        Tab(
          selected = selectedTab == 1,
          onClick = { selectedTab = 1 },
          text = { Text(if (language == AppLanguage.BENGALI) "আবেদন (${allRequests.size})" else "Requests (${allRequests.size})", fontSize = 11.sp) }
        )
        Tab(
          selected = selectedTab == 2,
          onClick = { selectedTab = 2 },
          text = { Text(if (language == AppLanguage.BENGALI) "ক্যাম্পেইন" else "Campaigns", fontSize = 11.sp) }
        )
        Tab(
          selected = selectedTab == 3,
          onClick = { selectedTab = 3 },
          text = { Text(if (language == AppLanguage.BENGALI) "অডিট লগ" else "Audit Logs", fontSize = 11.sp) }
        )
      }

      // Tab Contents
      when (selectedTab) {
        0 -> {
          // Donors Management Tab
          LazyColumn(
            modifier = Modifier
              .fillMaxSize()
              .testTag("admin_donors_tab"),
            contentPadding = PaddingValues(16.dp)
          ) {
            items(allDonors) { donor ->
              Card(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(bottom = 10.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, CardBorder)
              ) {
                Column(modifier = Modifier.padding(14.dp)) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                      BloodGroupBadge(bloodGroup = donor.bloodGroup, isSmall = true)
                      Spacer(modifier = Modifier.width(10.dp))
                      Column {
                        Text(text = donor.fullName, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = DarkNavy)
                        Text(text = "${donor.phone} • ${donor.district}", fontSize = 11.sp, color = TextSecondary)
                      }
                    }

                    // Verification Action
                    IconButton(
                      onClick = { viewModel.toggleDonorVerification(donor.id) }
                    ) {
                      Icon(
                        imageVector = if (donor.isVerified) Icons.Default.Verified else Icons.Default.Check,
                        contentDescription = "Toggle Verification",
                        tint = if (donor.isVerified) StatusAvailable else TextMuted
                      )
                    }
                  }

                  Spacer(modifier = Modifier.height(8.dp))

                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Text(
                      text = if (donor.isVerified) "Status: Verified Organization Donor" else "Status: Unverified / Pending",
                      fontSize = 11.sp,
                      color = if (donor.isVerified) StatusAvailable else TextSecondary
                    )

                    Row {
                      TextButton(
                        onClick = { viewModel.toggleDonorAvailability(donor.id, donor.isAvailable) }
                      ) {
                        Text(
                          text = if (donor.isAvailable) "Deactivate" else "Activate",
                          fontSize = 11.sp,
                          color = if (donor.isAvailable) Color(0xFFEF4444) else StatusAvailable
                        )
                      }

                      IconButton(
                        onClick = { viewModel.deleteDonor(donor) }
                      ) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFEF4444), modifier = Modifier.size(18.dp))
                      }
                    }
                  }
                }
              }
            }
          }
        }

        1 -> {
          // Blood Requests Management Tab
          LazyColumn(
            modifier = Modifier
              .fillMaxSize()
              .testTag("admin_requests_tab"),
            contentPadding = PaddingValues(16.dp)
          ) {
            items(allRequests) { request ->
              Card(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(bottom = 10.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, CardBorder)
              ) {
                Column(modifier = Modifier.padding(14.dp)) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                      BloodGroupBadge(bloodGroup = request.bloodGroup, isSmall = true)
                      Spacer(modifier = Modifier.width(10.dp))
                      Column {
                        Text(text = request.patientName, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = DarkNavy)
                        Text(text = "${request.unitsRequired} Bag(s) • ${request.hospitalName}", fontSize = 11.sp, color = TextSecondary)
                      }
                    }

                    Box(
                      modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                          when (request.status) {
                            "ACTIVE" -> Color(0xFFFEF2F2)
                            "FULFILLED" -> Color(0xFFEFF6FF)
                            else -> Color(0xFFF1F5F9)
                          }
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                      Text(
                        text = request.status,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (request.status) {
                          "ACTIVE" -> StatusEmergency
                          "FULFILLED" -> StatusFulfilled
                          else -> DarkNavy
                        }
                      )
                    }
                  }

                  Spacer(modifier = Modifier.height(10.dp))

                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                  ) {
                    if (request.status != "FULFILLED") {
                      Button(
                        onClick = { viewModel.updateRequestStatus(request.id, "FULFILLED") },
                        modifier = Modifier
                          .weight(1f)
                          .height(34.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = StatusAvailable),
                        shape = RoundedCornerShape(6.dp)
                      ) {
                        Icon(Icons.Default.DoneAll, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(AppStrings.markFulfilled(language), fontSize = 10.sp)
                      }
                    }

                    if (request.status != "REJECTED") {
                      OutlinedButton(
                        onClick = { viewModel.updateRequestStatus(request.id, "REJECTED") },
                        modifier = Modifier.height(34.dp),
                        shape = RoundedCornerShape(6.dp)
                      ) {
                        Text(AppStrings.reject(language), fontSize = 10.sp, color = Color(0xFFEF4444))
                      }
                    }

                    IconButton(
                      onClick = { viewModel.deleteRequest(request) },
                      modifier = Modifier.size(34.dp)
                    ) {
                      Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFEF4444), modifier = Modifier.size(18.dp))
                    }
                  }
                }
              }
            }
          }
        }

        2 -> {
          // Campaigns & Announcements Tab
          LazyColumn(
            modifier = Modifier
              .fillMaxSize()
              .testTag("admin_campaigns_tab"),
            contentPadding = PaddingValues(16.dp)
          ) {
            item {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
              ) {
                Button(
                  onClick = { showNewCampaignDialog = true },
                  modifier = Modifier.weight(1f),
                  colors = ButtonDefaults.buttonColors(containerColor = BloodRedPrimary),
                  shape = RoundedCornerShape(8.dp)
                ) {
                  Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(if (language == AppLanguage.BENGALI) "নতুন ক্যাম্পেইন" else "New Campaign", fontSize = 12.sp)
                }

                Button(
                  onClick = { showNewNoticeDialog = true },
                  modifier = Modifier.weight(1f),
                  colors = ButtonDefaults.buttonColors(containerColor = DarkNavy),
                  shape = RoundedCornerShape(8.dp)
                ) {
                  Icon(Icons.Default.Notifications, contentDescription = null, modifier = Modifier.size(16.dp))
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(if (language == AppLanguage.BENGALI) "জরুরি নোটিশ" else "New Notice", fontSize = 12.sp)
                }
              }

              Spacer(modifier = Modifier.height(14.dp))
            }

            items(campaigns) { camp ->
              Card(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(bottom = 10.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, CardBorder)
              ) {
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Column(modifier = Modifier.weight(1f)) {
                    Text(text = camp.title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = DarkNavy)
                    Text(text = "${camp.date} • ${camp.location}", fontSize = 11.sp, color = TextSecondary)
                  }
                  IconButton(onClick = { viewModel.deleteCampaign(camp) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFEF4444), modifier = Modifier.size(18.dp))
                  }
                }
              }
            }
          }
        }

        3 -> {
          // Audit Logs Tab
          LazyColumn(
            modifier = Modifier
              .fillMaxSize()
              .testTag("admin_audit_logs_tab"),
            contentPadding = PaddingValues(16.dp)
          ) {
            item {
              Text(
                text = if (language == AppLanguage.BENGALI) "সিস্টেম ও অ্যাডমিন অডিট হিস্টোরি" else "System & Admin Audit Logs",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = DarkNavy
              )
              Spacer(modifier = Modifier.height(8.dp))
            }
            items(auditLogs) { log ->
              Card(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(bottom = 8.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, CardBorder)
              ) {
                Column(modifier = Modifier.padding(12.dp)) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                  ) {
                    Text(text = log.action, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = BloodRedPrimary)
                    Text(text = "By: ${log.performedBy}", fontSize = 10.sp, color = TextSecondary)
                  }
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(text = log.details, fontSize = 11.sp, color = TextPrimary)
                }
              }
            }
          }
        }
      }
    }
  }

  // Create Campaign Dialog
  if (showNewCampaignDialog) {
    AlertDialog(
      onDismissRequest = { showNewCampaignDialog = false },
      title = { Text(if (language == AppLanguage.BENGALI) "নতুন রক্তদান ক্যাম্পেইন যোগ করুন" else "Add New Blood Drive Campaign") },
      text = {
        Column {
          OutlinedTextField(
            value = campTitle,
            onValueChange = { campTitle = it },
            label = { Text("Campaign Title (English)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )
          Spacer(modifier = Modifier.height(8.dp))
          OutlinedTextField(
            value = campTitleBn,
            onValueChange = { campTitleBn = it },
            label = { Text("ক্যাম্পেইন শিরোনাম (বাংলা)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )
          Spacer(modifier = Modifier.height(8.dp))
          OutlinedTextField(
            value = campLocation,
            onValueChange = { campLocation = it },
            label = { Text("Location") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )
          Spacer(modifier = Modifier.height(8.dp))
          OutlinedTextField(
            value = campDate,
            onValueChange = { campDate = it },
            label = { Text("Date & Time") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            if (campTitle.isNotBlank()) {
              val newCamp = Campaign(
                title = campTitle.trim(),
                titleBn = if (campTitleBn.isBlank()) campTitle else campTitleBn.trim(),
                description = "Voluntary blood donation drive organized by Pran Sanchar.",
                descriptionBn = "প্রাণ সঞ্চার রক্তদান সংস্থা কর্তৃক আয়োজিত স্বেচ্ছায় রক্তদান কর্মসূচি।",
                date = campDate.ifBlank { "Upcoming Date" },
                time = "10:00 AM - 5:00 PM",
                location = campLocation.ifBlank { "Dhaka" },
                locationBn = campLocation.ifBlank { "ঢাকা" },
                targetUnits = campTarget.toIntOrNull() ?: 100,
                collectedUnits = 0
              )
              viewModel.createCampaign(newCamp) {
                showNewCampaignDialog = false
                campTitle = ""
                campTitleBn = ""
                campLocation = ""
              }
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = BloodRedPrimary)
        ) {
          Text(if (language == AppLanguage.BENGALI) "প্রকাশ করুন" else "Publish")
        }
      },
      dismissButton = {
        TextButton(onClick = { showNewCampaignDialog = false }) {
          Text(if (language == AppLanguage.BENGALI) "বাতিল" else "Cancel")
        }
      }
    )
  }

  // Create Notice Dialog
  if (showNewNoticeDialog) {
    AlertDialog(
      onDismissRequest = { showNewNoticeDialog = false },
      title = { Text(if (language == AppLanguage.BENGALI) "জরুরি নোটিশ প্রকাশ করুন" else "Publish Official Notice") },
      text = {
        Column {
          OutlinedTextField(
            value = noticeTitle,
            onValueChange = { noticeTitle = it },
            label = { Text("Title (English)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )
          Spacer(modifier = Modifier.height(8.dp))
          OutlinedTextField(
            value = noticeTitleBn,
            onValueChange = { noticeTitleBn = it },
            label = { Text("নোটিশ শিরোনাম (বাংলা)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )
          Spacer(modifier = Modifier.height(8.dp))
          OutlinedTextField(
            value = noticeContent,
            onValueChange = { noticeContent = it },
            label = { Text("Content / Details") },
            minLines = 2,
            modifier = Modifier.fillMaxWidth()
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            if (noticeTitle.isNotBlank()) {
              val newNotice = Notice(
                title = noticeTitle.trim(),
                titleBn = if (noticeTitleBn.isBlank()) noticeTitle else noticeTitleBn.trim(),
                content = noticeContent.trim(),
                contentBn = if (noticeContentBn.isBlank()) noticeContent else noticeContentBn.trim(),
                category = "Announcement",
                priority = "Normal",
                isPinned = true,
                publishedAt = "Today"
              )
              viewModel.createNotice(newNotice) {
                showNewNoticeDialog = false
                noticeTitle = ""
                noticeTitleBn = ""
                noticeContent = ""
              }
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = DarkNavy)
        ) {
          Text(if (language == AppLanguage.BENGALI) "প্রকাশ করুন" else "Publish")
        }
      },
      dismissButton = {
        TextButton(onClick = { showNewNoticeDialog = false }) {
          Text(if (language == AppLanguage.BENGALI) "বাতিল" else "Cancel")
        }
      }
    )
  }
}
