package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.PranSancharDatabase
import com.example.data.model.BloodGroup
import com.example.data.model.BloodRequest
import com.example.data.model.Campaign
import com.example.data.model.Donor
import com.example.data.model.Notice
import com.example.data.model.UserProfile
import com.example.data.repository.PranSancharRepository
import com.example.ui.localization.AppLanguage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class Screen {
  HOME,
  DONORS,
  REQUESTS,
  CAMPAIGNS,
  DASHBOARD,
  REGISTER_DONOR,
  CREATE_REQUEST
}

class MainViewModel(application: Application) : AndroidViewModel(application) {
  private val database = PranSancharDatabase.getDatabase(application, viewModelScope)
  val repository = PranSancharRepository(
    donorDao = database.donorDao(),
    bloodRequestDao = database.bloodRequestDao(),
    donationRecordDao = database.donationRecordDao(),
    campaignDao = database.campaignDao(),
    noticeDao = database.noticeDao(),
    auditLogDao = database.auditLogDao(),
    userProfileDao = database.userProfileDao()
  )

  // Language state
  private val _language = MutableStateFlow(AppLanguage.BENGALI)
  val language: StateFlow<AppLanguage> = _language.asStateFlow()

  fun toggleLanguage() {
    _language.value = if (_language.value == AppLanguage.BENGALI) AppLanguage.ENGLISH else AppLanguage.BENGALI
  }

  // Navigation
  private val _currentScreen = MutableStateFlow(Screen.HOME)
  val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

  fun navigateTo(screen: Screen) {
    _currentScreen.value = screen
  }

  // SnackBar / Alert notification
  private val _toastMessage = MutableStateFlow<String?>(null)
  val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

  fun showToast(msg: String) {
    _toastMessage.value = msg
  }

  fun clearToast() {
    _toastMessage.value = null
  }

  // Donor Search Filters
  val searchQuery = MutableStateFlow("")
  val selectedBloodGroupFilter = MutableStateFlow<String?>(null)
  val selectedDistrictFilter = MutableStateFlow<String?>(null)
  val filterOnlyAvailable = MutableStateFlow(false)

  // Filtered Donors flow
  val filteredDonors: StateFlow<List<Donor>> = combine(
    repository.allDonors,
    searchQuery,
    selectedBloodGroupFilter,
    selectedDistrictFilter,
    filterOnlyAvailable
  ) { donors, query, group, district, onlyAvailable ->
    donors.filter { donor ->
      val matchesQuery = query.isBlank() ||
          donor.fullName.contains(query, ignoreCase = true) ||
          donor.district.contains(query, ignoreCase = true) ||
          donor.upazila.contains(query, ignoreCase = true)
      val matchesGroup = group == null || donor.bloodGroup.equals(group, ignoreCase = true)
      val matchesDistrict = district == null || donor.district.equals(district, ignoreCase = true)
      val matchesAvailability = !onlyAvailable || donor.isAvailable
      matchesQuery && matchesGroup && matchesDistrict && matchesAvailability
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Request Filters
  val requestGroupFilter = MutableStateFlow<String?>(null)
  val filteredRequests: StateFlow<List<BloodRequest>> = combine(
    repository.allRequests,
    requestGroupFilter
  ) { requests, group ->
    if (group == null) requests else requests.filter { it.bloodGroup.equals(group, ignoreCase = true) }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Active Requests only (for Home banner & Emergency view)
  val activeEmergencyRequests: StateFlow<List<BloodRequest>> = repository.activeRequests
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Database Stats
  val totalDonorsCount: StateFlow<Int> = repository.totalDonorsCount
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

  val activeRequestsCount: StateFlow<Int> = repository.activeRequestsCount
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

  val verifiedDonationsCount: StateFlow<Int> = repository.verifiedDonationsCount
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

  val allCampaigns: StateFlow<List<Campaign>> = repository.allCampaigns
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val allNotices: StateFlow<List<Notice>> = repository.allNotices
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val allAuditLogs = repository.allAuditLogs
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val currentUserProfile: StateFlow<UserProfile?> = repository.currentUserProfile
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

  // Super Admin security
  private val _isAdminUnlocked = MutableStateFlow(false)
  val isAdminUnlocked: StateFlow<Boolean> = _isAdminUnlocked.asStateFlow()

  val adminPinInput = MutableStateFlow("")
  private val SUPER_ADMIN_PIN = "7860"

  fun verifyAdminPin(): Boolean {
    if (adminPinInput.value == SUPER_ADMIN_PIN) {
      _isAdminUnlocked.value = true
      adminPinInput.value = ""
      showToast("Super Admin access authorized!")
      return true
    } else {
      showToast("Incorrect Admin PIN. Use default: 7860")
      return false
    }
  }

  fun logoutAdmin() {
    _isAdminUnlocked.value = false
    showToast("Exited Admin mode.")
  }

  // Actions
  fun registerDonor(donor: Donor, onSuccess: () -> Unit) {
    viewModelScope.launch {
      val result = repository.registerDonor(donor)
      result.onSuccess {
        showToast(
          if (_language.value == AppLanguage.BENGALI)
            "স্বেচ্ছায় রক্তদাতা হিসেবে নিবন্ধন সফল হয়েছে!"
          else
            "Registered as voluntary blood donor successfully!"
        )
        onSuccess()
      }.onFailure { err ->
        showToast(err.message ?: "Registration error")
      }
    }
  }

  fun submitBloodRequest(request: BloodRequest, onSuccess: () -> Unit) {
    viewModelScope.launch {
      repository.submitBloodRequest(request)
      showToast(
        if (_language.value == AppLanguage.BENGALI)
          "রক্তের আবেদন সফলভাবে পোস্ট করা হয়েছে!"
        else
          "Emergency blood request published successfully!"
      )
      onSuccess()
    }
  }

  fun toggleDonorAvailability(donorId: Long, currentAvailability: Boolean) {
    viewModelScope.launch {
      repository.updateDonorAvailability(donorId, !currentAvailability)
      showToast("Availability status updated.")
    }
  }

  fun updateLastDonation(donorId: Long, dateString: String) {
    viewModelScope.launch {
      repository.updateDonorLastDonationDate(donorId, dateString)
      showToast("Last donation date recorded. Thank you for saving lives!")
    }
  }

  fun toggleDonorVerification(donorId: Long) {
    viewModelScope.launch {
      repository.toggleDonorVerification(donorId)
      showToast("Donor verification status changed.")
    }
  }

  fun deleteDonor(donor: Donor) {
    viewModelScope.launch {
      repository.deleteDonor(donor)
      showToast("Donor record deleted.")
    }
  }

  fun updateRequestStatus(requestId: Long, status: String) {
    viewModelScope.launch {
      repository.updateRequestStatus(requestId, status)
      showToast("Request status updated to: $status")
    }
  }

  fun deleteRequest(request: BloodRequest) {
    viewModelScope.launch {
      repository.deleteRequest(request)
      showToast("Blood request removed.")
    }
  }

  fun createCampaign(campaign: Campaign, onSuccess: () -> Unit) {
    viewModelScope.launch {
      repository.createCampaign(campaign)
      showToast("Campaign published successfully.")
      onSuccess()
    }
  }

  fun deleteCampaign(campaign: Campaign) {
    viewModelScope.launch {
      repository.deleteCampaign(campaign)
      showToast("Campaign removed.")
    }
  }

  fun createNotice(notice: Notice, onSuccess: () -> Unit) {
    viewModelScope.launch {
      repository.createNotice(notice)
      showToast("Notice published.")
      onSuccess()
    }
  }

  fun deleteNotice(notice: Notice) {
    viewModelScope.launch {
      repository.deleteNotice(notice)
      showToast("Notice deleted.")
    }
  }
}
