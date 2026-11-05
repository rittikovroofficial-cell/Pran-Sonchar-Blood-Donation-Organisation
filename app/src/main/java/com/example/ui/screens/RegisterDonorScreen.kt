package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bloodtype
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BangladeshLocations
import com.example.data.model.BloodGroup
import com.example.data.model.Donor
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
fun RegisterDonorScreen(
  viewModel: MainViewModel,
  onNavigateBack: () -> Unit
) {
  val language by viewModel.language.collectAsState()

  var fullName by remember { mutableStateOf("") }
  var phone by remember { mutableStateOf("") }
  var email by remember { mutableStateOf("") }
  var selectedBloodGroup by remember { mutableStateOf("O+") }
  var dateOfBirth by remember { mutableStateOf("1998-01-01") }
  var gender by remember { mutableStateOf("Male") }
  var selectedDistrict by remember { mutableStateOf("Dhaka") }
  var upazila by remember { mutableStateOf("") }
  var detailedAddress by remember { mutableStateOf("") }
  var lastDonationDate by remember { mutableStateOf("") }
  var neverDonated by remember { mutableStateOf(false) }
  var isAvailable by remember { mutableStateOf(true) }
  var consentCall by remember { mutableStateOf(true) }
  var consentWhatsapp by remember { mutableStateOf(true) }

  var districtExpanded by remember { mutableStateOf(false) }
  var errorMessage by remember { mutableStateOf<String?>(null) }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF7F8FA))
      .testTag("register_donor_screen_column"),
    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
  ) {
    item {
      // Header Info Card
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DarkNavy)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = if (language == AppLanguage.BENGALI)
              "স্বেচ্ছায় রক্তদাতা নিবন্ধন ফর্ম"
            else
              "Voluntary Blood Donor Registration",
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            color = Color.White
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = if (language == AppLanguage.BENGALI)
              "সঠিক তথ্য প্রদান করে দেশের জরুরি রক্ত সংকটে মানুষের পাশে দাঁড়ান। আপনার ফোন নম্বর আপনার অনুমতি অনুযায়ী দেখানো হবে।"
            else
              "Join our voluntary network to save lives across Bangladesh. Your contact details are shared strictly per your consent preferences.",
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.88f),
            lineHeight = 17.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))
    }

    // Full Name
    item {
      OutlinedTextField(
        value = fullName,
        onValueChange = { fullName = it },
        label = { Text(if (language == AppLanguage.BENGALI) "পূর্ণ নাম *" else "Full Name *") },
        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = BloodRedPrimary) },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("donor_reg_name_input"),
        shape = RoundedCornerShape(10.dp),
        singleLine = true
      )
      Spacer(modifier = Modifier.height(12.dp))
    }

    // Phone Number
    item {
      OutlinedTextField(
        value = phone,
        onValueChange = { phone = it },
        label = { Text(if (language == AppLanguage.BENGALI) "মোবাইল নম্বর (১১ ডিজিট) *" else "Phone Number (01XXXXXXXXX) *") },
        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = BloodRedPrimary) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("donor_reg_phone_input"),
        shape = RoundedCornerShape(10.dp),
        singleLine = true
      )
      Spacer(modifier = Modifier.height(12.dp))
    }

    // Email
    item {
      OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text(if (language == AppLanguage.BENGALI) "ইমেইল ঠিকানা *" else "Email Address *") },
        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = BloodRedPrimary) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("donor_reg_email_input"),
        shape = RoundedCornerShape(10.dp),
        singleLine = true
      )
      Spacer(modifier = Modifier.height(12.dp))
    }

    // Blood Group Selector
    item {
      Column {
        Text(
          text = if (language == AppLanguage.BENGALI) "রক্তের গ্রুপ নির্বাচন করুন *" else "Select Blood Group *",
          fontWeight = FontWeight.Bold,
          fontSize = 13.sp,
          color = DarkNavy
        )
        Spacer(modifier = Modifier.height(6.dp))
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          items(BloodGroup.entries) { group ->
            FilterChip(
              selected = selectedBloodGroup == group.display,
              onClick = { selectedBloodGroup = group.display },
              label = { Text(group.display, fontWeight = FontWeight.Bold) },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = BloodRedPrimary,
                selectedLabelColor = Color.White
              ),
              modifier = Modifier.testTag("donor_reg_group_${group.display}")
            )
          }
        }
      }
      Spacer(modifier = Modifier.height(12.dp))
    }

    // District Dropdown & Upazila
    item {
      ExposedDropdownMenuBox(
        expanded = districtExpanded,
        onExpandedChange = { districtExpanded = !districtExpanded },
        modifier = Modifier.fillMaxWidth()
      ) {
        OutlinedTextField(
          value = selectedDistrict,
          onValueChange = {},
          readOnly = true,
          label = { Text(if (language == AppLanguage.BENGALI) "জেলা *" else "District *") },
          trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = districtExpanded) },
          leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = BloodRedPrimary) },
          modifier = Modifier
            .menuAnchor()
            .fillMaxWidth()
            .testTag("donor_reg_district_select"),
          shape = RoundedCornerShape(10.dp)
        )

        ExposedDropdownMenu(
          expanded = districtExpanded,
          onDismissRequest = { districtExpanded = false }
        ) {
          BangladeshLocations.districts.forEach { districtItem ->
            val label = if (language == AppLanguage.BENGALI) districtItem.nameBn else districtItem.nameEn
            DropdownMenuItem(
              text = { Text(label) },
              onClick = {
                selectedDistrict = districtItem.nameEn
                districtExpanded = false
              }
            )
          }
        }
      }
      Spacer(modifier = Modifier.height(12.dp))
    }

    // Upazila / Area
    item {
      OutlinedTextField(
        value = upazila,
        onValueChange = { upazila = it },
        label = { Text(if (language == AppLanguage.BENGALI) "উপজেলা বা এলাকার নাম *" else "Upazila / Area *") },
        leadingIcon = { Icon(Icons.Default.Home, contentDescription = null, tint = BloodRedPrimary) },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("donor_reg_upazila_input"),
        shape = RoundedCornerShape(10.dp),
        singleLine = true
      )
      Spacer(modifier = Modifier.height(12.dp))
    }

    // Detailed Address (Optional)
    item {
      OutlinedTextField(
        value = detailedAddress,
        onValueChange = { detailedAddress = it },
        label = { Text(if (language == AppLanguage.BENGALI) "বিস্তারিত ঠিকানা (ঐচ্ছিক)" else "Detailed Address (Optional)") },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("donor_reg_address_input"),
        shape = RoundedCornerShape(10.dp),
        singleLine = true
      )
      Spacer(modifier = Modifier.height(12.dp))
    }

    // Last Donation Date
    item {
      Column {
        Text(
          text = if (language == AppLanguage.BENGALI) "শেষ রক্তদানের তথ্য" else "Last Donation History",
          fontWeight = FontWeight.Bold,
          fontSize = 13.sp,
          color = DarkNavy
        )
        Spacer(modifier = Modifier.height(4.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
          Checkbox(
            checked = neverDonated,
            onCheckedChange = {
              neverDonated = it
              if (it) lastDonationDate = ""
            },
            colors = CheckboxDefaults.colors(checkedColor = BloodRedPrimary)
          )
          Text(
            text = if (language == AppLanguage.BENGALI) "আগে কখনো রক্তদান করিনি (নতুন রক্তদাতা)" else "Never donated blood before",
            fontSize = 12.sp,
            color = TextPrimary
          )
        }

        if (!neverDonated) {
          OutlinedTextField(
            value = lastDonationDate,
            onValueChange = { lastDonationDate = it },
            label = { Text(if (language == AppLanguage.BENGALI) "সর্বশেষ রক্তদানের তারিখ (যেমন: 2024-08-15)" else "Last Donation Date (YYYY-MM-DD)") },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("donor_reg_last_date_input"),
            shape = RoundedCornerShape(10.dp),
            singleLine = true
          )
        }
      }
      Spacer(modifier = Modifier.height(14.dp))
    }

    // Availability Toggle
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = if (language == AppLanguage.BENGALI) "আমি রক্তদানে প্রস্তুত" else "Ready to Donate Blood",
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp,
              color = DarkNavy
            )
            Text(
              text = if (language == AppLanguage.BENGALI)
                "জরুরি প্রয়োজনে রক্ত সন্ধানকারীদের তালিকায় আপনার প্রোফাইল দেখাবে।"
              else
                "Shows your profile as available when recipients search for blood.",
              fontSize = 11.sp,
              color = TextSecondary
            )
          }
          Switch(
            checked = isAvailable,
            onCheckedChange = { isAvailable = it },
            colors = SwitchDefaults.colors(
              checkedThumbColor = Color.White,
              checkedTrackColor = StatusAvailable
            )
          )
        }
      }
      Spacer(modifier = Modifier.height(14.dp))
    }

    // Privacy & Consent Checkboxes
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(
            text = if (language == AppLanguage.BENGALI) "যোগাযোগ ও গোপনীয়তা সম্মতি" else "Contact & Privacy Consent",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = DarkNavy
          )
          Spacer(modifier = Modifier.height(6.dp))

          Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
              checked = consentCall,
              onCheckedChange = { consentCall = it },
              colors = CheckboxDefaults.colors(checkedColor = BloodRedPrimary)
            )
            Text(
              text = if (language == AppLanguage.BENGALI)
                "রক্তপ্রার্থী সরাসরি আমাকে কল করতে পারবেন"
              else
                "Allow potential recipients to call me directly",
              fontSize = 12.sp
            )
          }

          Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
              checked = consentWhatsapp,
              onCheckedChange = { consentWhatsapp = it },
              colors = CheckboxDefaults.colors(checkedColor = BloodRedPrimary)
            )
            Text(
              text = if (language == AppLanguage.BENGALI)
                "হোয়াটসঅ্যাপে (WhatsApp) বার্তা পাঠাতে পারবেন"
              else
                "Allow WhatsApp messaging for coordination",
              fontSize = 12.sp
            )
          }
        }
      }
      Spacer(modifier = Modifier.height(16.dp))
    }

    // Error message if any
    if (errorMessage != null) {
      item {
        Text(
          text = errorMessage ?: "",
          color = BloodRedPrimary,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          modifier = Modifier.padding(bottom = 12.dp)
        )
      }
    }

    // Submit Button
    item {
      Button(
        onClick = {
          if (fullName.isBlank()) {
            errorMessage = if (language == AppLanguage.BENGALI) "দয়া করে পূর্ণ নাম লিখুন।" else "Please enter your full name."
            return@Button
          }
          if (phone.length < 11) {
            errorMessage = if (language == AppLanguage.BENGALI) "সঠিক ১১ ডিজিটের ফোন নম্বর দিন (যেমন: 017XXXXXXXX)।" else "Please enter a valid 11-digit phone number."
            return@Button
          }
          if (email.isBlank() || !email.contains("@")) {
            errorMessage = if (language == AppLanguage.BENGALI) "সঠিক ইমেইল ঠিকানা দিন।" else "Please enter a valid email address."
            return@Button
          }
          if (upazila.isBlank()) {
            errorMessage = if (language == AppLanguage.BENGALI) "উপজেলা বা এলাকার নাম লিখুন।" else "Please enter your upazila or area."
            return@Button
          }

          errorMessage = null
          val newDonor = Donor(
            fullName = fullName.trim(),
            phone = phone.trim(),
            email = email.trim(),
            bloodGroup = selectedBloodGroup,
            dateOfBirth = dateOfBirth,
            gender = gender,
            district = selectedDistrict,
            upazila = upazila.trim(),
            detailedAddress = detailedAddress.trim(),
            lastDonationDate = if (neverDonated) "" else lastDonationDate.trim(),
            isAvailable = isAvailable,
            consentSharePhone = consentCall,
            consentShareWhatsapp = consentWhatsapp,
            isVerified = false,
            totalDonations = if (neverDonated || lastDonationDate.isBlank()) 0 else 1
          )

          viewModel.registerDonor(newDonor) {
            onNavigateBack()
          }
        },
        modifier = Modifier
          .fillMaxWidth()
          .height(50.dp)
          .testTag("submit_donor_registration_button"),
        colors = ButtonDefaults.buttonColors(containerColor = BloodRedPrimary),
        shape = RoundedCornerShape(12.dp)
      ) {
        Icon(imageVector = Icons.Default.Check, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = if (language == AppLanguage.BENGALI) "নিবন্ধন সম্পন্ন করুন" else "Complete Registration",
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp
        )
      }

      Spacer(modifier = Modifier.height(32.dp))
    }
  }
}
