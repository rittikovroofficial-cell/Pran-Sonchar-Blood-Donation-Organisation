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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.localization.AppLanguage
import com.example.ui.localization.AppStrings
import com.example.ui.theme.BloodRedContainer
import com.example.ui.theme.BloodRedPrimary
import com.example.ui.theme.CardBorder
import com.example.ui.theme.DarkNavy
import com.example.ui.theme.StatusAvailable
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.MainViewModel

@Composable
fun CampaignsAndNoticesScreen(
  viewModel: MainViewModel
) {
  val language by viewModel.language.collectAsState()
  val campaigns by viewModel.allCampaigns.collectAsState()
  val notices by viewModel.allNotices.collectAsState()

  var selectedTab by remember { mutableIntStateOf(0) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF7F8FA))
  ) {
    TabRow(
      selectedTabIndex = selectedTab,
      containerColor = Color.White,
      contentColor = BloodRedPrimary
    ) {
      Tab(
        selected = selectedTab == 0,
        onClick = { selectedTab = 0 },
        text = {
          Text(
            text = AppStrings.upcomingEventsTitle(language),
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
          )
        },
        icon = { Icon(Icons.Default.Event, contentDescription = null, modifier = Modifier.size(18.dp)) }
      )
      Tab(
        selected = selectedTab == 1,
        onClick = { selectedTab = 1 },
        text = {
          Text(
            text = AppStrings.noticesTitle(language),
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
          )
        },
        icon = { Icon(Icons.Default.Notifications, contentDescription = null, modifier = Modifier.size(18.dp)) }
      )
    }

    if (selectedTab == 0) {
      // Campaigns List
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .testTag("campaigns_list"),
        contentPadding = PaddingValues(16.dp)
      ) {
        items(campaigns) { campaign ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 14.dp)
              .testTag("campaign_card_${campaign.id}"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, CardBorder),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(BloodRedContainer)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                  Text(
                    text = campaign.status,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = BloodRedPrimary
                  )
                }

                Text(
                  text = campaign.organizer,
                  fontSize = 11.sp,
                  color = TextSecondary
                )
              }

              Spacer(modifier = Modifier.height(10.dp))

              Text(
                text = if (language == AppLanguage.BENGALI) campaign.titleBn else campaign.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = DarkNavy
              )

              Spacer(modifier = Modifier.height(6.dp))

              Text(
                text = if (language == AppLanguage.BENGALI) campaign.descriptionBn else campaign.description,
                fontSize = 12.sp,
                color = TextSecondary,
                lineHeight = 18.sp
              )

              Spacer(modifier = Modifier.height(12.dp))

              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Event, contentDescription = null, tint = BloodRedPrimary, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = campaign.date, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
              }

              Spacer(modifier = Modifier.height(4.dp))

              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = BloodRedPrimary, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = if (language == AppLanguage.BENGALI) campaign.locationBn else campaign.location,
                  fontSize = 12.sp,
                  color = TextSecondary
                )
              }

              Spacer(modifier = Modifier.height(14.dp))

              // Target vs Collected Progress
              Column {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text(
                    text = "${if (language == AppLanguage.BENGALI) "সংগৃহীত রক্ত:" else "Collected:"} ${campaign.collectedUnits} ব্যাগ",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkNavy
                  )
                  Text(
                    text = "${if (language == AppLanguage.BENGALI) "লক্ষ্যমাত্রা:" else "Target:"} ${campaign.targetUnits} ব্যাগ",
                    fontSize = 11.sp,
                    color = TextSecondary
                  )
                }
                Spacer(modifier = Modifier.height(6.dp))
                val progress = (campaign.collectedUnits.toFloat() / campaign.targetUnits.toFloat()).coerceIn(0f, 1f)
                LinearProgressIndicator(
                  progress = { progress },
                  modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                  color = BloodRedPrimary,
                  trackColor = Color(0xFFF1F5F9)
                )
              }

              Spacer(modifier = Modifier.height(14.dp))

              Button(
                onClick = {
                  viewModel.showToast(
                    if (language == AppLanguage.BENGALI)
                      "ক্যাম্পেইনে অংশ নেওয়ার জন্য ধন্যবাদ! আমাদের ভলান্টিয়ার টিম আপনার সাথে যোগাযোগ করবে।"
                    else
                      "Thank you for registering interest! Our volunteer team will coordinate with you."
                  )
                },
                modifier = Modifier
                  .fillMaxWidth()
                  .height(40.dp)
                  .testTag("volunteer_button_${campaign.id}"),
                colors = ButtonDefaults.buttonColors(containerColor = DarkNavy),
                shape = RoundedCornerShape(8.dp)
              ) {
                Icon(Icons.Default.VolunteerActivism, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = if (language == AppLanguage.BENGALI) "স্বেচ্ছাসেবী হিসেবে যোগ দিন" else "Volunteer for this Camp",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }
        }
      }
    } else {
      // Notices List
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .testTag("notices_list"),
        contentPadding = PaddingValues(16.dp)
      ) {
        items(notices) { notice ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 12.dp)
              .testTag("notice_card_${notice.id}"),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(
              1.dp,
              if (notice.isPinned) BloodRedPrimary.copy(alpha = 0.5f) else CardBorder
            )
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  if (notice.isPinned) {
                    Icon(
                      imageVector = Icons.Default.PushPin,
                      contentDescription = "Pinned",
                      tint = BloodRedPrimary,
                      modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                  }
                  Box(
                    modifier = Modifier
                      .clip(RoundedCornerShape(6.dp))
                      .background(
                        if (notice.priority == "Urgent") Color(0xFFFEF2F2) else Color(0xFFF1F5F9)
                      )
                      .padding(horizontal = 8.dp, vertical = 3.dp)
                  ) {
                    Text(
                      text = notice.category,
                      fontSize = 10.sp,
                      fontWeight = FontWeight.Bold,
                      color = if (notice.priority == "Urgent") BloodRedPrimary else DarkNavy
                    )
                  }
                }

                Text(
                  text = notice.publishedAt,
                  fontSize = 11.sp,
                  color = TextMuted
                )
              }

              Spacer(modifier = Modifier.height(8.dp))

              Text(
                text = if (language == AppLanguage.BENGALI) notice.titleBn else notice.title,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = DarkNavy
              )

              Spacer(modifier = Modifier.height(6.dp))

              Text(
                text = if (language == AppLanguage.BENGALI) notice.contentBn else notice.content,
                fontSize = 12.sp,
                color = TextSecondary,
                lineHeight = 18.sp
              )
            }
          }
        }
      }
    }
  }
}
