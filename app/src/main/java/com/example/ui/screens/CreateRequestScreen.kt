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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BangladeshLocations
import com.example.data.model.BloodGroup
import com.example.data.model.BloodRequest
import com.example.data.model.UrgencyLevel
import com.example.ui.localization.AppLanguage
import com.example.ui.theme.BloodRedPrimary
import com.example.ui.theme.DarkNavy
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRequestScreen(
  viewModel: MainViewModel,
  onNavigateBack: () -> Unit
) {
  val language by viewModel.language.collectAsState()

  var patientName by remember { mutableStateOf("") }
  var selectedBloodGroup by remember { mutableStateOf("O+") }
  var unitsRequired by remember { mutableIntStateOf(1) }
  var hospitalName by remember { mutableStateOf("") }
  var selectedDistrict by remember { mutableStateOf("Dhaka") }
  var upazila by remember { mutableStateOf("") }
  var hospitalAddress by remember { mutableStateOf("") }
  var requiredDate by remember { mutableStateOf("Today / Emergency") }
  var requiredTime by remember { mutableStateOf("Immediate") }
  var contactPerson by remember { mutableStateOf("") }
  var contactPhone by remember { mutableStateOf("") }
  var urgencyLevel by remember { mutableStateOf("EMERGENCY") }
  var additionalInfo by remember { mutableStateOf("") }

  var districtExpanded by remember { mutableStateOf(false) }
  var errorMessage by remember { mutableStateOf<String?>(null) }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF7F8FA))
      .testTag("create_request_screen_column"),
    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
  ) {
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = BloodRedPrimary)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Warning,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = if (language == AppLanguage.BENGALI)
                "জরুরি রক্তের আবেদন ফরম"
              else
                "Post Emergency Blood Request",
              fontWeight = FontWeight.Bold,
              fontSize = 17.sp,
              color = Color.White
            )
          }
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = if (language == AppLanguage.BENGALI)
              "হাসপাতাল ও রোগীর সঠিক তথ্য দিন যাতে নিকটস্থ রক্তদাতারা দ্রুত আপনার সাথে যোগাযোগ করতে পারেন।"
            else
              "Provide accurate hospital and contact details to connect urgently with nearby donors.",
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.92f)
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))
    }

    // Patient Name
    item {
      OutlinedTextField(
        value = patientName,
        onValueChange = { patientName = it },
        label = { Text(if (language == AppLanguage.BENGALI) "রোগীর নাম ও বয়স *" else "Patient Name & Age *") },
        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = BloodRedPrimary) },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("request_patient_name_input"),
        shape = RoundedCornerShape(10.dp),
        singleLine = true
      )
      Spacer(modifier = Modifier.height(12.dp))
    }

    // Blood Group
    item {
      Column {
        Text(
          text = if (language == AppLanguage.BENGALI) "প্রয়োজনীয় রক্তের গ্রুপ *" else "Blood Group Needed *",
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
              modifier = Modifier.testTag("request_group_${group.display}")
            )
          }
        }
      }
      Spacer(modifier = Modifier.height(14.dp))
    }

    // Units Required Stepper
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
          Column {
            Text(
              text = if (language == AppLanguage.BENGALI) "প্রয়োজনীয় রক্তের পরিমাণ (ব্যাগ)" else "Blood Units Required",
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp,
              color = DarkNavy
            )
            Text(
              text = if (language == AppLanguage.BENGALI) "প্রতি ব্যাগ রক্ত একজন প্রাপ্তবয়স্ক রক্তদাতার থেকে সংগ্রহ হয়" else "Units needed for the procedure",
              fontSize = 11.sp,
              color = TextSecondary
            )
          }

          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color(0xFFF1F5F9))
                .clickable { if (unitsRequired > 1) unitsRequired-- },
              contentAlignment = Alignment.Center
            ) {
              Icon(imageVector = Icons.Default.Remove, contentDescription = "Decrease", tint = DarkNavy)
            }

            Text(
              text = "$unitsRequired",
              fontWeight = FontWeight.ExtraBold,
              fontSize = 16.sp,
              color = BloodRedPrimary,
              modifier = Modifier.padding(horizontal = 14.dp)
            )

            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(BloodRedPrimary)
                .clickable { if (unitsRequired < 10) unitsRequired++ },
              contentAlignment = Alignment.Center
            ) {
              Icon(imageVector = Icons.Default.Add, contentDescription = "Increase", tint = Color.White)
            }
          }
        }
      }
      Spacer(modifier = Modifier.height(14.dp))
    }

    // Hospital Name with suggestions
    item {
      OutlinedTextField(
        value = hospitalName,
        onValueChange = { hospitalName = it },
        label = { Text(if (language == AppLanguage.BENGALI) "হাসপাতালের নাম *" else "Hospital Name *") },
        leadingIcon = { Icon(Icons.Default.LocalHospital, contentDescription = null, tint = BloodRedPrimary) },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("request_hospital_input"),
        shape = RoundedCornerShape(10.dp)
      )

      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = if (language == AppLanguage.BENGALI) "জনপ্রিয় হাসপাতাল:" else "Popular Medical Centers:",
        fontSize = 11.sp,
        color = TextSecondary
      )
      Spacer(modifier = Modifier.height(4.dp))
      LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        val quickHospitals = listOf(
          "DMCH, Dhaka",
          "BSMMU, Shahbagh",
          "NICVD, Mirpur",
          "CMCH, Chattogram",
          "Square Hospital",
          "Sylhet MAG Osmani"
        )
        items(quickHospitals) { item ->
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(Color(0xFFE2E8F0))
              .clickable { hospitalName = item }
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Text(text = item, fontSize = 10.sp, color = DarkNavy)
          }
        }
      }
      Spacer(modifier = Modifier.height(14.dp))
    }

    // District & Upazila
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
            .testTag("request_district_select"),
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

    // Upazila & Detailed Ward/Bed
    item {
      OutlinedTextField(
        value = upazila,
        onValueChange = { upazila = it },
        label = { Text(if (language == AppLanguage.BENGALI) "উপজেলা বা এলাকার নাম *" else "Upazila / Area *") },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("request_upazila_input"),
        shape = RoundedCornerShape(10.dp),
        singleLine = true
      )
      Spacer(modifier = Modifier.height(12.dp))

      OutlinedTextField(
        value = hospitalAddress,
        onValueChange = { hospitalAddress = it },
        label = { Text(if (language == AppLanguage.BENGALI) "হাসপাতালের ওয়ার্ড, কেবিন বা বেড নম্বর (ঐচ্ছিক)" else "Hospital Ward / Cabin / Bed No.") },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("request_address_input"),
        shape = RoundedCornerShape(10.dp),
        singleLine = true
      )
      Spacer(modifier = Modifier.height(12.dp))
    }

    // Urgency Level Selector
    item {
      Column {
        Text(
          text = if (language == AppLanguage.BENGALI) "জরুরিতা নির্ধারণ করুন *" else "Urgency Level *",
          fontWeight = FontWeight.Bold,
          fontSize = 13.sp,
          color = DarkNavy
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          UrgencyLevel.entries.forEach { level ->
            FilterChip(
              selected = urgencyLevel == level.name,
              onClick = { urgencyLevel = level.name },
              label = {
                Text(
                  text = if (language == AppLanguage.BENGALI) level.titleBn else level.titleEn,
                  fontSize = 11.sp
                )
              },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = if (level == UrgencyLevel.EMERGENCY) BloodRedPrimary else DarkNavy,
                selectedLabelColor = Color.White
              ),
              modifier = Modifier.weight(1f)
            )
          }
        }
      }
      Spacer(modifier = Modifier.height(12.dp))
    }

    // Required Date & Time
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        OutlinedTextField(
          value = requiredDate,
          onValueChange = { requiredDate = it },
          label = { Text(if (language == AppLanguage.BENGALI) "প্রয়োজনের তারিখ *" else "Required Date *") },
          modifier = Modifier.weight(1f),
          shape = RoundedCornerShape(10.dp),
          singleLine = true
        )
        OutlinedTextField(
          value = requiredTime,
          onValueChange = { requiredTime = it },
          label = { Text(if (language == AppLanguage.BENGALI) "সময়" else "Time") },
          modifier = Modifier.weight(1f),
          shape = RoundedCornerShape(10.dp),
          singleLine = true
        )
      }
      Spacer(modifier = Modifier.height(12.dp))
    }

    // Contact Person & Phone
    item {
      OutlinedTextField(
        value = contactPerson,
        onValueChange = { contactPerson = it },
        label = { Text(if (language == AppLanguage.BENGALI) "যোগাযোগকারীর নাম ও সম্পর্ক *" else "Contact Person & Relation *") },
        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = BloodRedPrimary) },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("request_contact_name_input"),
        shape = RoundedCornerShape(10.dp),
        singleLine = true
      )
      Spacer(modifier = Modifier.height(12.dp))

      OutlinedTextField(
        value = contactPhone,
        onValueChange = { contactPhone = it },
        label = { Text(if (language == AppLanguage.BENGALI) "যোগাযোগের ফোন নম্বর (১১ ডিজিট) *" else "Contact Phone Number *") },
        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = BloodRedPrimary) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("request_contact_phone_input"),
        shape = RoundedCornerShape(10.dp),
        singleLine = true
      )
      Spacer(modifier = Modifier.height(12.dp))
    }

    // Additional clinical notes
    item {
      OutlinedTextField(
        value = additionalInfo,
        onValueChange = { additionalInfo = it },
        label = { Text(if (language == AppLanguage.BENGALI) "রোগীর অবস্থা বা অতিরিক্ত তথ্য (যেমন: সার্জারি, ডেঙ্গু, থ্যালাসেমিয়া)" else "Additional Info (Surgery, Dengue, Thalassemia, etc.)") },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        minLines = 2
      )
      Spacer(modifier = Modifier.height(16.dp))
    }

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

    // Submit Request
    item {
      Button(
        onClick = {
          if (patientName.isBlank()) {
            errorMessage = if (language == AppLanguage.BENGALI) "দয়া করে রোগীর নাম লিখুন।" else "Please enter patient name."
            return@Button
          }
          if (hospitalName.isBlank()) {
            errorMessage = if (language == AppLanguage.BENGALI) "হাসপাতালের নাম লিখুন।" else "Please enter hospital name."
            return@Button
          }
          if (upazila.isBlank()) {
            errorMessage = if (language == AppLanguage.BENGALI) "উপজেলা বা এলাকার নাম লিখুন।" else "Please enter upazila/area."
            return@Button
          }
          if (contactPerson.isBlank()) {
            errorMessage = if (language == AppLanguage.BENGALI) "যোগাযোগকারীর নাম লিখুন।" else "Please enter contact person name."
            return@Button
          }
          if (contactPhone.length < 11) {
            errorMessage = if (language == AppLanguage.BENGALI) "সঠিক ১১ ডিজিটের ফোন নম্বর দিন।" else "Please enter valid 11-digit phone number."
            return@Button
          }

          errorMessage = null
          val newRequest = BloodRequest(
            patientName = patientName.trim(),
            bloodGroup = selectedBloodGroup,
            unitsRequired = unitsRequired,
            hospitalName = hospitalName.trim(),
            district = selectedDistrict,
            upazila = upazila.trim(),
            hospitalAddress = hospitalAddress.trim(),
            requiredDate = requiredDate.trim(),
            requiredTime = requiredTime.trim(),
            contactPerson = contactPerson.trim(),
            contactPhone = contactPhone.trim(),
            urgencyLevel = urgencyLevel,
            status = "ACTIVE",
            additionalInfo = additionalInfo.trim()
          )

          viewModel.submitBloodRequest(newRequest) {
            onNavigateBack()
          }
        },
        modifier = Modifier
          .fillMaxWidth()
          .height(50.dp)
          .testTag("submit_blood_request_button"),
        colors = ButtonDefaults.buttonColors(containerColor = BloodRedPrimary),
        shape = RoundedCornerShape(12.dp)
      ) {
        Icon(imageVector = Icons.Default.Warning, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = if (language == AppLanguage.BENGALI) "আবেদনটি সরাসরি প্রকাশ করুন" else "Publish Emergency Request",
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp
        )
      }

      Spacer(modifier = Modifier.height(32.dp))
    }
  }
}
