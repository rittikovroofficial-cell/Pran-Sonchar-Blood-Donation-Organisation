package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.BloodGroup
import com.example.ui.components.EmergencyRequestCard
import com.example.ui.components.StatCard
import com.example.ui.localization.AppLanguage
import com.example.ui.localization.AppStrings
import com.example.ui.theme.BloodRedContainer
import com.example.ui.theme.BloodRedDark
import com.example.ui.theme.BloodRedLight
import com.example.ui.theme.BloodRedPrimary
import com.example.ui.theme.CardBorder
import com.example.ui.theme.DarkNavy
import com.example.ui.theme.StatusAvailable
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.Screen

@Composable
fun HomeScreen(
  viewModel: MainViewModel,
  onNavigateToScreen: (Screen) -> Unit,
  onSelectBloodGroup: (String) -> Unit
) {
  val context = LocalContext.current
  val language by viewModel.language.collectAsState()
  val totalDonors by viewModel.totalDonorsCount.collectAsState()
  val activeRequestsCount by viewModel.activeRequestsCount.collectAsState()
  val totalDonations by viewModel.verifiedDonationsCount.collectAsState()
  val emergencyRequests by viewModel.activeEmergencyRequests.collectAsState()
  val campaigns by viewModel.allCampaigns.collectAsState()

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF7F8FA))
      .testTag("home_screen_lazy_column"),
    contentPadding = PaddingValues(bottom = 32.dp)
  ) {
    // 1. Hero Section
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(
            brush = Brush.verticalGradient(
              colors = listOf(BloodRedPrimary, Color(0xFFC21827))
            )
          )
          .padding(horizontal = 20.dp, vertical = 24.dp)
      ) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(Color.White)
                .padding(4.dp),
              contentAlignment = Alignment.Center
            ) {
              Image(
                painter = painterResource(id = R.drawable.ic_pran_sanchar),
                contentDescription = null,
                modifier = Modifier
                  .size(46.dp)
                  .clip(CircleShape)
              )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column {
              Text(
                text = AppStrings.orgName(language),
                style = MaterialTheme.typography.titleLarge.copy(
                  fontWeight = FontWeight.ExtraBold,
                  color = Color.White,
                  fontSize = if (language == AppLanguage.BENGALI) 20.sp else 17.sp
                )
              )
              Text(
                text = "“${AppStrings.orgTagline(language)}”",
                style = MaterialTheme.typography.bodyMedium.copy(
                  color = Color.White.copy(alpha = 0.92f),
                  fontWeight = FontWeight.Medium
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(18.dp))

          Text(
            text = if (language == AppLanguage.BENGALI)
              "রক্ত দিন, জীবন বাঁচান। বাংলাদেশের যেকোনো প্রান্তে জরুরি রক্তের প্রয়োজনে বা স্বেচ্ছায় রক্তদানে যুক্ত থাকুন প্রাণ সঞ্চারের সাথে।"
            else
              "Donate blood, save lives. Connect instantly with verified voluntary blood donors across all 64 districts in Bangladesh.",
            style = MaterialTheme.typography.bodyMedium.copy(
              color = Color.White.copy(alpha = 0.95f),
              lineHeight = 20.sp
            )
          )

          Spacer(modifier = Modifier.height(20.dp))

          // 3 Major Action Buttons
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Button(
              onClick = { onNavigateToScreen(Screen.DONORS) },
              modifier = Modifier
                .weight(1f)
                .height(46.dp)
                .testTag("home_search_donors_button"),
              colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = BloodRedPrimary
              ),
              shape = RoundedCornerShape(12.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = AppStrings.searchDonorsBtn(language),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
              )
            }

            Button(
              onClick = { onNavigateToScreen(Screen.REGISTER_DONOR) },
              modifier = Modifier
                .weight(1f)
                .height(46.dp)
                .testTag("home_become_donor_button"),
              colors = ButtonDefaults.buttonColors(
                containerColor = DarkNavy,
                contentColor = Color.White
              ),
              shape = RoundedCornerShape(12.dp)
            ) {
              Icon(
                imageVector = Icons.Default.PersonAdd,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = AppStrings.becomeDonorBtn(language),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Button(
              onClick = { onNavigateToScreen(Screen.CREATE_REQUEST) },
              modifier = Modifier
                .weight(1.3f)
                .height(46.dp)
                .testTag("home_emergency_request_button"),
              colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFCC00),
                contentColor = DarkNavy
              ),
              shape = RoundedCornerShape(12.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = AppStrings.emergencyRequestBtn(language),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 13.sp
              )
            }

            OutlinedButton(
              onClick = { onNavigateToScreen(Screen.LOGIN) },
              modifier = Modifier
                .weight(0.9f)
                .height(46.dp)
                .testTag("home_login_button"),
              colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White
              ),
              border = BorderStroke(1.5.dp, Color.White),
              shape = RoundedCornerShape(12.dp)
            ) {
              Icon(
                imageVector = Icons.Default.People,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = if (language == AppLanguage.BENGALI) "লগইন" else "Sign In",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
              )
            }
          }
        }
      }
    }

    // 2. Download App Section
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 10.dp)
          .testTag("home_download_app_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkNavy),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(46.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(BloodRedPrimary),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.HealthAndSafety,
                contentDescription = "Download App",
                tint = Color.White,
                modifier = Modifier.size(26.dp)
              )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = AppStrings.downloadAppTitle(language),
                  fontWeight = FontWeight.Bold,
                  fontSize = 15.sp,
                  color = Color.White
                )
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFFFFCC00))
                    .padding(horizontal = 5.dp, vertical = 2.dp)
                ) {
                  Text(
                    text = "APK",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 9.sp,
                    color = DarkNavy
                  )
                }
              }
              Text(
                text = AppStrings.downloadAppDesc(language),
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.85f),
                lineHeight = 15.sp
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Button(
              onClick = {
                val appUrl = "https://ais-pre-i4qwndeqzvjjpmldngbia7-847336418732.asia-northeast1.run.app"
                val intent = Intent(Intent.ACTION_VIEW).apply {
                  data = Uri.parse(appUrl)
                }
                context.startActivity(intent)
                viewModel.showToast(
                  if (language == AppLanguage.BENGALI)
                    "প্রাণ সঞ্চার মোবাইল অ্যাপ ডাউনলোড হচ্ছে..."
                  else
                    "Downloading Pran Sanchar App..."
                )
              },
              modifier = Modifier
                .weight(1.2f)
                .height(42.dp)
                .testTag("download_app_apk_button"),
              colors = ButtonDefaults.buttonColors(
                containerColor = BloodRedPrimary,
                contentColor = Color.White
              ),
              shape = RoundedCornerShape(10.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = AppStrings.downloadApkBtn(language),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
              )
            }

            OutlinedButton(
              onClick = {
                val shareText = """
                  🩸 প্রাণ সঞ্চার রক্তদান সংস্থা (Pran Sanchar Blood Donation Organization)
                  "রক্তের বন্ধনে, প্রাণের স্পন্দনে"
                  জরুরি রক্তের প্রয়োজনে ও স্বেচ্ছায় রক্তদানে যুক্ত হতে সরাসরি আমাদের মোবাইল অ্যাপটি ডাউনলোড করুন:
                  https://ais-pre-i4qwndeqzvjjpmldngbia7-847336418732.asia-northeast1.run.app
                """.trimIndent()
                val sendIntent = Intent().apply {
                  action = Intent.ACTION_SEND
                  putExtra(Intent.EXTRA_TEXT, shareText)
                  type = "text/plain"
                }
                context.startActivity(Intent.createChooser(sendIntent, "Share Pran Sanchar App"))
              },
              modifier = Modifier
                .weight(1f)
                .height(42.dp)
                .testTag("share_app_link_button"),
              border = BorderStroke(1.dp, Color.White.copy(alpha = 0.6f)),
              colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
              shape = RoundedCornerShape(10.dp)
            ) {
              Text(
                text = AppStrings.shareAppLinkBtn(language),
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
              )
            }
          }
        }
      }
    }

    // 2. Real Database Statistics Section
    item {
      Column(modifier = Modifier.padding(16.dp)) {
        Text(
          text = if (language == AppLanguage.BENGALI) "সংস্থার সার্বিক পরিসংখ্যান" else "Real Database Statistics",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = DarkNavy
          )
        )
        Spacer(modifier = Modifier.height(12.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          StatCard(
            number = "$totalDonors+",
            label = AppStrings.totalDonors(language),
            icon = Icons.Default.People,
            accentColor = BloodRedPrimary,
            modifier = Modifier.weight(1f)
          )
          StatCard(
            number = "$totalDonations+",
            label = AppStrings.totalDonations(language),
            icon = Icons.Default.Favorite,
            accentColor = StatusAvailable,
            modifier = Modifier.weight(1f)
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          StatCard(
            number = "$activeRequestsCount",
            label = AppStrings.activeRequests(language),
            icon = Icons.Default.WaterDrop,
            accentColor = Color(0xFFEF4444),
            modifier = Modifier.weight(1f)
          )
          StatCard(
            number = "64",
            label = AppStrings.districtsCovered(language),
            icon = Icons.Default.LocationCity,
            accentColor = DarkNavy,
            modifier = Modifier.weight(1f)
          )
        }
      }
    }

    // 3. Blood Group Availability Grid
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = AppStrings.bloodAvailability(language),
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = DarkNavy
            )
          )
          Text(
            text = if (language == AppLanguage.BENGALI) "গ্রুপ নির্বাচন করুন" else "Quick Filter",
            style = MaterialTheme.typography.bodySmall.copy(
              color = BloodRedPrimary,
              fontWeight = FontWeight.Medium
            )
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        val bloodGroups = BloodGroup.entries
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          items(bloodGroups) { group ->
            Card(
              modifier = Modifier
                .clickable {
                  onSelectBloodGroup(group.display)
                  onNavigateToScreen(Screen.DONORS)
                }
                .testTag("blood_group_card_${group.display}"),
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = Color.White),
              border = BorderStroke(1.dp, CardBorder),
              elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
              Column(
                modifier = Modifier
                  .width(76.dp)
                  .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                Box(
                  modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(BloodRedContainer),
                  contentAlignment = Alignment.Center
                ) {
                  Text(
                    text = group.display,
                    color = BloodRedPrimary,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp
                  )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                  text = if (language == AppLanguage.BENGALI) "রক্তদাতা" else "Donors",
                  fontSize = 10.sp,
                  color = TextSecondary
                )
              }
            }
          }
        }
      }
    }

    // 4. Urgent Emergency Requests Section
    item {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Warning,
              contentDescription = null,
              tint = BloodRedPrimary,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = AppStrings.recentEmergencyTitle(language),
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = DarkNavy
              )
            )
          }

          Text(
            text = AppStrings.viewAll(language),
            modifier = Modifier.clickable { onNavigateToScreen(Screen.REQUESTS) },
            style = MaterialTheme.typography.bodySmall.copy(
              color = BloodRedPrimary,
              fontWeight = FontWeight.Bold
            )
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (emergencyRequests.isEmpty()) {
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, CardBorder)
          ) {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = if (language == AppLanguage.BENGALI)
                  "বর্তমানে কোনো জরুরি রক্তের আবেদন নেই।"
                else
                  "No pending emergency blood requests at this moment.",
                color = TextSecondary,
                fontSize = 13.sp
              )
            }
          }
        } else {
          emergencyRequests.take(2).forEach { request ->
            EmergencyRequestCard(
              request = request,
              language = language,
              modifier = Modifier.padding(bottom = 10.dp),
              onCardClick = { onNavigateToScreen(Screen.REQUESTS) }
            )
          }
        }
      }
    }

    // 5. How Blood Donation Works
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
          text = AppStrings.howItWorksTitle(language),
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = DarkNavy
          )
        )
        Spacer(modifier = Modifier.height(10.dp))

        val steps = listOf(
          Pair(
            if (language == AppLanguage.BENGALI) "১. রক্তদানের যোগ্যতা" else "1. Eligibility",
            if (language == AppLanguage.BENGALI) "বয়স ১৮-৬০ বছর, ওজন ৪৮ কেজি+, শেষ রক্তদানের পর ৩-৪ মাস ব্যবধান।" else "Age 18-60, weight 48kg+, 90-120 days interval since last donation."
          ),
          Pair(
            if (language == AppLanguage.BENGALI) "২. সহজ নিবন্ধন ও যোগাযোগ" else "2. Contact & Screening",
            if (language == AppLanguage.BENGALI) "অ্যাপ থেকে সরাসরি রক্তদাতার সাথে যোগাযোগ বা জরুরি আবেদন পোস্ট করুন।" else "Connect securely or submit emergency request with hospital details."
          ),
          Pair(
            if (language == AppLanguage.BENGALI) "৩. জীবন বাঁচানোর আনন্দ" else "3. Save a Life",
            if (language == AppLanguage.BENGALI) "আপনার এক ব্যাগ রক্ত বাঁচাতে পারে দুর্ঘটনায় আহত বা থ্যালাসেমিয়া আক্রান্ত প্রাণ।" else "One single bag of blood can save up to 3 precious human lives."
          )
        )

        steps.forEach { (title, desc) ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, CardBorder)
          ) {
            Row(
              modifier = Modifier.padding(14.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(36.dp)
                  .clip(CircleShape)
                  .background(BloodRedContainer),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.HealthAndSafety,
                  contentDescription = null,
                  tint = BloodRedPrimary,
                  modifier = Modifier.size(20.dp)
                )
              }
              Spacer(modifier = Modifier.width(12.dp))
              Column {
                Text(
                  text = title,
                  fontWeight = FontWeight.Bold,
                  fontSize = 14.sp,
                  color = DarkNavy
                )
                Text(
                  text = desc,
                  fontSize = 12.sp,
                  color = TextSecondary
                )
              }
            }
          }
        }
      }
    }

    // 6. Organization Info & 24/7 Helpline
    item {
      Column(modifier = Modifier.padding(16.dp)) {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = DarkNavy),
          elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
          Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = null,
                tint = Color(0xFFFFCC00),
                modifier = Modifier.size(22.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = AppStrings.contactUsTitle(language),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
              )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = if (language == AppLanguage.BENGALI)
                "জরুরি রক্তের প্রয়োজন বা যেকোনো সহযোগিতার জন্য আমাদের কেন্দ্রীয় স্বেচ্ছাসেবী হেল্পলাইনে যোগাযোগ করুন।"
              else
                "For urgent blood requirements or donation inquiries, contact our 24/7 volunteer helpline directly.",
              fontSize = 12.sp,
              color = Color.White.copy(alpha = 0.85f)
            )
            Spacer(modifier = Modifier.height(14.dp))

            // Contact Buttons Row (Call & WhatsApp)
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              // Direct Call
              Button(
                onClick = {
                  val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:+8801773326910")
                  }
                  context.startActivity(intent)
                },
                modifier = Modifier
                  .weight(1.1f)
                  .height(42.dp)
                  .testTag("home_call_helpline_button"),
                colors = ButtonDefaults.buttonColors(
                  containerColor = Color(0xFFFFCC00),
                  contentColor = DarkNavy
                ),
                shape = RoundedCornerShape(10.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.Phone,
                  contentDescription = "Call",
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "+8801773326910",
                  fontWeight = FontWeight.Bold,
                  fontSize = 11.sp
                )
              }

              // WhatsApp
              Button(
                onClick = {
                  val url = "https://api.whatsapp.com/send?phone=+8801773326910&text=Hello%20Pran%20Sanchar%20Organization"
                  val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(url)
                  }
                  context.startActivity(intent)
                },
                modifier = Modifier
                  .weight(0.9f)
                  .height(42.dp)
                  .testTag("home_whatsapp_helpline_button"),
                colors = ButtonDefaults.buttonColors(
                  containerColor = Color(0xFF25D366),
                  contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.Chat,
                  contentDescription = "WhatsApp",
                  modifier = Modifier.size(16.dp),
                  tint = Color.White
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "WhatsApp",
                  fontWeight = FontWeight.Bold,
                  fontSize = 12.sp,
                  color = Color.White
                )
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Email Contact Button
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White.copy(alpha = 0.12f))
                .clickable {
                  val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:Rittikovroofficial@gmail.com")
                    putExtra(Intent.EXTRA_SUBJECT, "Pran Sanchar Blood Donation Organization Inquiry")
                  }
                  context.startActivity(intent)
                }
                .padding(horizontal = 14.dp, vertical = 10.dp)
                .testTag("home_email_contact_button")
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.Email,
                  contentDescription = "Email",
                  tint = Color(0xFFFFCC00),
                  modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                  Text(
                    text = if (language == AppLanguage.BENGALI) "অফিসিয়াল ইমেইল" else "Official Email",
                    fontSize = 10.sp,
                    color = Color.White.copy(alpha = 0.7f)
                  )
                  Text(
                    text = "Rittikovroofficial@gmail.com",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                  )
                }
              }
            }
          }
        }
      }
    }
  }
}
