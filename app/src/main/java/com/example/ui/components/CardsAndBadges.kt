package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BloodRequest
import com.example.data.model.Donor
import com.example.ui.localization.AppLanguage
import com.example.ui.localization.AppStrings
import com.example.ui.theme.BloodRedContainer
import com.example.ui.theme.BloodRedDark
import com.example.ui.theme.BloodRedLight
import com.example.ui.theme.BloodRedPrimary
import com.example.ui.theme.CardBorder
import com.example.ui.theme.DarkNavy
import com.example.ui.theme.DarkNavyContainer
import com.example.ui.theme.StatusAvailable
import com.example.ui.theme.StatusAvailableBg
import com.example.ui.theme.StatusEmergency
import com.example.ui.theme.StatusEmergencyBg
import com.example.ui.theme.StatusFulfilled
import com.example.ui.theme.StatusFulfilledBg
import com.example.ui.theme.StatusRecentlyDonated
import com.example.ui.theme.StatusRecentlyDonatedBg
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun BloodGroupBadge(
  bloodGroup: String,
  modifier: Modifier = Modifier,
  isSmall: Boolean = false
) {
  val size = if (isSmall) 36.dp else 48.dp
  val fontSize = if (isSmall) 13.sp else 16.sp

  Box(
    modifier = modifier
      .size(size)
      .clip(RoundedCornerShape(12.dp))
      .background(
        brush = Brush.linearGradient(
          colors = listOf(BloodRedLight, BloodRedDark)
        )
      ),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = bloodGroup,
      color = Color.White,
      fontWeight = FontWeight.ExtraBold,
      fontSize = fontSize
    )
  }
}

@Composable
fun StatCard(
  number: String,
  label: String,
  icon: ImageVector,
  accentColor: Color = BloodRedPrimary,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier.testTag("stat_card_${label.replace(" ", "_").lowercase()}"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    border = BorderStroke(1.dp, CardBorder),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(44.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(accentColor.copy(alpha = 0.12f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = accentColor,
          modifier = Modifier.size(24.dp)
        )
      }
      Spacer(modifier = Modifier.width(12.dp))
      Column {
        Text(
          text = number,
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.ExtraBold,
            color = DarkNavy
          )
        )
        Text(
          text = label,
          style = MaterialTheme.typography.bodySmall.copy(
            color = TextSecondary,
            fontSize = 11.sp
          ),
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
      }
    }
  }
}

@Composable
fun EmergencyRequestCard(
  request: BloodRequest,
  language: AppLanguage,
  modifier: Modifier = Modifier,
  onCardClick: () -> Unit = {}
) {
  val context = LocalContext.current
  val isUrgent = request.urgencyLevel == "EMERGENCY"

  val infiniteTransition = rememberInfiniteTransition(label = "pulse")
  val alphaPulse by infiniteTransition.animateFloat(
    initialValue = 0.6f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(800),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulse"
  )

  Card(
    modifier = modifier
      .fillMaxWidth()
      .clickable { onCardClick() }
      .testTag("emergency_request_card_${request.id}"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (isUrgent) Color(0xFFFFF7F7) else Color.White
    ),
    border = BorderStroke(
      width = if (isUrgent) 1.5.dp else 1.dp,
      color = if (isUrgent) BloodRedPrimary.copy(alpha = 0.7f) else CardBorder
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          BloodGroupBadge(bloodGroup = request.bloodGroup, isSmall = true)
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = request.patientName,
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              color = DarkNavy
            )
            Text(
              text = AppStrings.unitsNeeded(language, request.unitsRequired),
              fontSize = 12.sp,
              color = BloodRedPrimary,
              fontWeight = FontWeight.SemiBold
            )
          }
        }

        // Urgency pill
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
              if (isUrgent) BloodRedPrimary.copy(alpha = alphaPulse) else StatusRecentlyDonated
            )
            .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = if (isUrgent) Icons.Default.Warning else Icons.Default.Schedule,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = if (isUrgent) (if (language == AppLanguage.BENGALI) "জরুরি" else "EMERGENCY") else (if (language == AppLanguage.BENGALI) "প্রয়োজনীয়" else "URGENT"),
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 10.sp
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Hospital & Location
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.LocalHospital,
          contentDescription = null,
          tint = BloodRedPrimary,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = request.hospitalName,
          fontSize = 13.sp,
          fontWeight = FontWeight.Medium,
          color = TextPrimary,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
      }

      Spacer(modifier = Modifier.height(6.dp))

      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.LocationOn,
          contentDescription = null,
          tint = TextSecondary,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "${request.upazila}, ${request.district}",
          fontSize = 12.sp,
          color = TextSecondary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
          imageVector = Icons.Default.Schedule,
          contentDescription = null,
          tint = TextSecondary,
          modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = request.requiredDate,
          fontSize = 12.sp,
          color = TextSecondary
        )
      }

      if (request.additionalInfo.isNotBlank()) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = request.additionalInfo,
          fontSize = 11.sp,
          color = TextSecondary,
          maxLines = 2,
          overflow = TextOverflow.Ellipsis
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Contact & Share Actions
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Button(
          onClick = {
            val intent = Intent(Intent.ACTION_DIAL).apply {
              data = Uri.parse("tel:${request.contactPhone}")
            }
            context.startActivity(intent)
          },
          modifier = Modifier
            .weight(1f)
            .height(40.dp)
            .testTag("call_request_button_${request.id}"),
          colors = ButtonDefaults.buttonColors(containerColor = BloodRedPrimary),
          shape = RoundedCornerShape(8.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Call,
            contentDescription = "Call",
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = if (language == AppLanguage.BENGALI) "যোগাযোগ করুন" else "Call Contact",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
          )
        }

        OutlinedButton(
          onClick = {
            val shareText = """
              🚨 EMERGENCY BLOOD NEEDED 🚨
              Organization: Pran Sanchar (প্রাণ সঞ্চার রক্তদান সংস্থা)
              Blood Group: ${request.bloodGroup} (${request.unitsRequired} Unit(s))
              Patient: ${request.patientName}
              Hospital: ${request.hospitalName}, ${request.upazila}, ${request.district}
              Date: ${request.requiredDate} (${request.requiredTime})
              Contact Person: ${request.contactPerson} (${request.contactPhone})
              Note: ${request.additionalInfo}
              "রক্তের বন্ধনে, প্রাণের স্পন্দনে"
            """.trimIndent()
            val sendIntent = Intent().apply {
              action = Intent.ACTION_SEND
              putExtra(Intent.EXTRA_TEXT, shareText)
              type = "text/plain"
            }
            context.startActivity(Intent.createChooser(sendIntent, "Share Blood Request"))
          },
          modifier = Modifier
            .height(40.dp)
            .testTag("share_request_button_${request.id}"),
          shape = RoundedCornerShape(8.dp),
          border = BorderStroke(1.dp, BloodRedPrimary)
        ) {
          Icon(
            imageVector = Icons.Default.Share,
            contentDescription = "Share",
            tint = BloodRedPrimary,
            modifier = Modifier.size(16.dp)
          )
        }
      }
    }
  }
}

@Composable
fun DonorCard(
  donor: Donor,
  language: AppLanguage,
  modifier: Modifier = Modifier,
  onCardClick: () -> Unit = {}
) {
  val context = LocalContext.current

  val availabilityColor = when {
    donor.isAvailable -> StatusAvailable
    donor.lastDonationDate.isNotBlank() -> StatusRecentlyDonated
    else -> TextMuted
  }

  val availabilityBg = when {
    donor.isAvailable -> StatusAvailableBg
    donor.lastDonationDate.isNotBlank() -> StatusRecentlyDonatedBg
    else -> Color(0xFFF1F5F9)
  }

  val availabilityText = when {
    donor.isAvailable -> AppStrings.availableNow(language)
    donor.lastDonationDate.isNotBlank() -> AppStrings.recentlyDonated(language)
    else -> AppStrings.unavailable(language)
  }

  Card(
    modifier = modifier
      .fillMaxWidth()
      .clickable { onCardClick() }
      .testTag("donor_card_${donor.id}"),
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
        Row(verticalAlignment = Alignment.CenterVertically) {
          BloodGroupBadge(bloodGroup = donor.bloodGroup)
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = donor.fullName,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = DarkNavy
              )
              if (donor.isVerified) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = "Verified Donor",
                  tint = StatusAvailable,
                  modifier = Modifier.size(16.dp)
                )
              }
            }
            Text(
              text = "${donor.upazila}, ${donor.district}",
              fontSize = 12.sp,
              color = TextSecondary
            )
          }
        }

        // Availability badge
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(availabilityBg)
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Text(
            text = availabilityText,
            color = availabilityColor,
            fontWeight = FontWeight.SemiBold,
            fontSize = 10.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        if (donor.lastDonationDate.isNotBlank()) {
          Text(
            text = "${if (language == AppLanguage.BENGALI) "শেষ রক্তদান:" else "Last Donated:"} ${donor.lastDonationDate}",
            fontSize = 11.sp,
            color = TextSecondary
          )
        } else {
          Text(
            text = if (language == AppLanguage.BENGALI) "নতুন রক্তদাতা" else "New Voluntary Donor",
            fontSize = 11.sp,
            color = StatusAvailable,
            fontWeight = FontWeight.Medium
          )
        }

        if (donor.totalDonations > 0) {
          Text(
            text = "${donor.totalDonations} ${if (language == AppLanguage.BENGALI) "বার রক্তদান" else "Donations"}",
            fontSize = 11.sp,
            color = BloodRedPrimary,
            fontWeight = FontWeight.Bold
          )
        }
      }

      // Privacy-Safe Contact Section
      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        if (donor.consentSharePhone) {
          Button(
            onClick = {
              val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${donor.phone}")
              }
              context.startActivity(intent)
            },
            modifier = Modifier
              .weight(1f)
              .height(38.dp)
              .testTag("call_donor_${donor.id}"),
            colors = ButtonDefaults.buttonColors(containerColor = BloodRedPrimary),
            shape = RoundedCornerShape(8.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Call,
              contentDescription = "Call",
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = AppStrings.callDonor(language),
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }

        if (donor.consentShareWhatsapp) {
          Button(
            onClick = {
              val formattedPhone = if (donor.phone.startsWith("0")) "+88" + donor.phone else donor.phone
              val url = "https://api.whatsapp.com/send?phone=$formattedPhone&text=Hello%20$donor.fullName,%20I%20found%20your%20contact%20on%20Pran%20Sanchar%20Blood%20Donation%20platform."
              val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
              }
              context.startActivity(intent)
            },
            modifier = Modifier
              .weight(1f)
              .height(38.dp)
              .testTag("whatsapp_donor_${donor.id}"),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
            shape = RoundedCornerShape(8.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Chat,
              contentDescription = "WhatsApp",
              tint = Color.White,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = AppStrings.whatsappDonor(language),
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
          }
        }

        if (!donor.consentSharePhone && !donor.consentShareWhatsapp) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(Color(0xFFF1F5F9))
              .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = if (language == AppLanguage.BENGALI)
                "রক্তদাতা ব্যক্তিগত যোগাযোগের অনুরোধ করেছেন (হেল্পলাইন প্রযোজ্য)"
              else
                "Private contact preferences (Contact via Pran Sanchar Helpline)",
              fontSize = 10.sp,
              color = TextSecondary
            )
          }
        }
      }
    }
  }
}
