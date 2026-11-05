package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.BloodGroupBadge
import com.example.ui.localization.AppLanguage
import com.example.ui.localization.AppStrings
import com.example.ui.theme.BloodRedContainer
import com.example.ui.theme.BloodRedDark
import com.example.ui.theme.BloodRedPrimary
import com.example.ui.theme.CardBorder
import com.example.ui.theme.DarkNavy
import com.example.ui.theme.StatusAvailable
import com.example.ui.theme.StatusAvailableBg
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.Screen

@Composable
fun UserDashboardScreen(
  viewModel: MainViewModel,
  onNavigateToScreen: (Screen) -> Unit,
  onOpenAdmin: () -> Unit
) {
  val context = LocalContext.current
  val language by viewModel.language.collectAsState()
  val currentUser by viewModel.currentUserProfile.collectAsState()
  val allDonors by viewModel.filteredDonors.collectAsState()
  val allRequests by viewModel.filteredRequests.collectAsState()

  // Find if user is in donors table
  val myDonorRecord = allDonors.firstOrNull { it.phone == currentUser?.phone || it.id == 1L }

  var showDonationDialog by remember { mutableStateOf(false) }
  var newDonationDate by remember { mutableStateOf("2024-11-05") }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF7F8FA))
      .testTag("user_dashboard_column"),
    contentPadding = PaddingValues(16.dp)
  ) {
    // 1. Profile Header Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, CardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(
                  brush = Brush.linearGradient(
                    colors = listOf(BloodRedPrimary, BloodRedDark)
                  )
                ),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(36.dp)
              )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = currentUser?.fullName ?: "Tanvir Hasan",
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = DarkNavy
              )
              Text(
                text = currentUser?.phone ?: "01711002233",
                fontSize = 12.sp,
                color = TextSecondary
              )
              Text(
                text = currentUser?.email ?: "tanvir.hasan@example.bd",
                fontSize = 11.sp,
                color = TextSecondary
              )
            }

            BloodGroupBadge(
              bloodGroup = currentUser?.bloodGroup ?: "O+",
              isSmall = false
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
          ) {
            OutlinedButton(
              onClick = { onNavigateToScreen(Screen.LOGIN) },
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.height(34.dp)
            ) {
              Text(
                text = if (language == AppLanguage.BENGALI) "লগইন / অন্য অ্যাকাউন্ট" else "Login / Switch Account",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))
    }

    // 2. Donor Status & Quick Controls
    item {
      if (myDonorRecord != null) {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          border = BorderStroke(1.dp, CardBorder)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = null,
                  tint = StatusAvailable,
                  modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = if (language == AppLanguage.BENGALI) "রক্তদাতা স্ট্যাটাস" else "Donor Status",
                  fontWeight = FontWeight.Bold,
                  fontSize = 15.sp,
                  color = DarkNavy
                )
              }

              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(8.dp))
                  .background(StatusAvailableBg)
                  .padding(horizontal = 8.dp, vertical = 4.dp)
              ) {
                Text(
                  text = if (myDonorRecord.isAvailable) AppStrings.availableNow(language) else AppStrings.unavailable(language),
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = StatusAvailable
                )
              }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Toggle Availability
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = if (language == AppLanguage.BENGALI) "রক্তদানের প্রাপ্যতা সচল রাখুন" else "Donor Availability Toggle",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Medium,
                  color = TextPrimary
                )
                Text(
                  text = if (language == AppLanguage.BENGALI)
                    "বন্ধ রাখলে আপনাকে সার্চ তালিকায় সাময়িক অপারগ দেখাবে।"
                  else
                    "Turn off if you are traveling or temporarily unable to donate.",
                  fontSize = 11.sp,
                  color = TextSecondary
                )
              }

              Switch(
                checked = myDonorRecord.isAvailable,
                onCheckedChange = {
                  viewModel.toggleDonorAvailability(myDonorRecord.id, myDonorRecord.isAvailable)
                },
                colors = SwitchDefaults.colors(
                  checkedThumbColor = Color.White,
                  checkedTrackColor = StatusAvailable
                )
              )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Last Donation & Update Button
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(
                  text = if (language == AppLanguage.BENGALI) "সর্বশেষ রক্তদান" else "Last Donation Date",
                  fontSize = 11.sp,
                  color = TextSecondary
                )
                Text(
                  text = if (myDonorRecord.lastDonationDate.isNotBlank()) myDonorRecord.lastDonationDate else "N/A",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold,
                  color = DarkNavy
                )
              }

              Button(
                onClick = { showDonationDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = BloodRedPrimary),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.height(36.dp)
              ) {
                Icon(Icons.Default.DateRange, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = if (language == AppLanguage.BENGALI) "নতুন রক্তদান রেকর্ড" else "Log Donation",
                  fontSize = 11.sp
                )
              }
            }
          }
        }
      } else {
        // Prompt to register as donor
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          border = BorderStroke(1.dp, CardBorder)
        ) {
          Column(modifier = Modifier.padding(18.dp)) {
            Text(
              text = if (language == AppLanguage.BENGALI) "আপনি এখনও রক্তদাতা হিসেবে নিবন্ধিত নন" else "You are not yet registered as a donor",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp,
              color = DarkNavy
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = if (language == AppLanguage.BENGALI) "রক্তদান করে মুমূর্ষু রোগীর জীবন বাঁচানোর মহৎ উদ্যোগে শরিক হোন।" else "Register your blood group to help patients in emergencies.",
              fontSize = 12.sp,
              color = TextSecondary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
              onClick = { onNavigateToScreen(Screen.REGISTER_DONOR) },
              colors = ButtonDefaults.buttonColors(containerColor = BloodRedPrimary),
              shape = RoundedCornerShape(8.dp)
            ) {
              Icon(Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(AppStrings.becomeDonorBtn(language))
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))
    }

    // 3. Super Admin Gateway Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkNavy)
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.AdminPanelSettings,
              contentDescription = null,
              tint = Color(0xFFFFCC00),
              modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = if (language == AppLanguage.BENGALI) "সুপার অ্যাডমিন ড্যাশবোর্ড" else "Super Admin Gateway",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
              )
              Text(
                text = if (language == AppLanguage.BENGALI)
                  "সংস্থার রক্তদাতা যাচাই, রক্তের আবেদন অনুমোদন ও ক্যাম্পেইন পরিচালনা করুন।"
                else
                  "Organization management, donor verification, approval, and audit logs.",
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.85f),
                lineHeight = 16.sp
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          Button(
            onClick = { onOpenAdmin() },
            colors = ButtonDefaults.buttonColors(
              containerColor = Color(0xFFFFCC00),
              contentColor = DarkNavy
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(42.dp)
              .testTag("open_admin_button")
          ) {
            Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = if (language == AppLanguage.BENGALI) "অ্যাডমিন প্যানেলে প্রবেশ করুন" else "Access Admin Dashboard",
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))
    }

    // 4. Organization Contact & Support Card
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("dashboard_contact_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, CardBorder)
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Phone,
              contentDescription = null,
              tint = BloodRedPrimary,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = if (language == AppLanguage.BENGALI) "অফিসিয়াল যোগাযোগ ও সহায়তা" else "Official Support & Contact",
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              color = DarkNavy
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Phone & WhatsApp
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Button(
              onClick = {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                  data = Uri.parse("tel:+8801773326910")
                }
                context.startActivity(intent)
              },
              modifier = Modifier
                .weight(1.1f)
                .height(40.dp)
                .testTag("dashboard_call_button"),
              colors = ButtonDefaults.buttonColors(containerColor = BloodRedPrimary),
              shape = RoundedCornerShape(8.dp)
            ) {
              Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("+8801773326910", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }

            Button(
              onClick = {
                val url = "https://api.whatsapp.com/send?phone=+8801773326910&text=Hello%20Pran%20Sanchar"
                val intent = Intent(Intent.ACTION_VIEW).apply {
                  data = Uri.parse(url)
                }
                context.startActivity(intent)
              },
              modifier = Modifier
                .weight(0.9f)
                .height(40.dp)
                .testTag("dashboard_whatsapp_button"),
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
              shape = RoundedCornerShape(8.dp)
            ) {
              Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.White)
              Spacer(modifier = Modifier.width(4.dp))
              Text("WhatsApp", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Email
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(Color(0xFFF1F5F9))
              .clickable {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                  data = Uri.parse("mailto:Rittikovroofficial@gmail.com")
                  putExtra(Intent.EXTRA_SUBJECT, "Pran Sanchar Blood Donation Inquiry")
                }
                context.startActivity(intent)
              }
              .padding(horizontal = 12.dp, vertical = 10.dp)
              .testTag("dashboard_email_button")
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null,
                tint = BloodRedPrimary,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(
                  text = if (language == AppLanguage.BENGALI) "ইমেইল পাঠান" else "Send Email",
                  fontSize = 10.sp,
                  color = TextSecondary
                )
                Text(
                  text = "Rittikovroofficial@gmail.com",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold,
                  color = DarkNavy
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }

  // Dialog to log new donation
  if (showDonationDialog && myDonorRecord != null) {
    AlertDialog(
      onDismissRequest = { showDonationDialog = false },
      title = {
        Text(
          text = if (language == AppLanguage.BENGALI) "নতুন রক্তদানের তারিখ রেকর্ড করুন" else "Record New Blood Donation",
          fontWeight = FontWeight.Bold
        )
      },
      text = {
        Column {
          Text(
            text = if (language == AppLanguage.BENGALI)
              "রক্তদানের তারিখ নির্বাচন করুন। পরবর্তী ৯০ দিন আপনার প্রোফাইল স্বয়ংক্রিয়ভাবে 'সম্প্রতি রক্ত দিয়েছেন' হিসেবে দেখানো হবে।"
            else
              "Enter donation date. Your profile will be marked as recently donated for the next 90 days.",
            fontSize = 12.sp,
            color = TextSecondary
          )
          Spacer(modifier = Modifier.height(12.dp))
          OutlinedTextField(
            value = newDonationDate,
            onValueChange = { newDonationDate = it },
            label = { Text("YYYY-MM-DD") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            viewModel.updateLastDonation(myDonorRecord.id, newDonationDate)
            showDonationDialog = false
          },
          colors = ButtonDefaults.buttonColors(containerColor = BloodRedPrimary)
        ) {
          Text(if (language == AppLanguage.BENGALI) "সংরক্ষণ করুন" else "Save")
        }
      },
      dismissButton = {
        TextButton(onClick = { showDonationDialog = false }) {
          Text(if (language == AppLanguage.BENGALI) "বাতিল" else "Cancel")
        }
      }
    )
  }
}
