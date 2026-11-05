package com.example.data.repository

import com.example.data.local.AuditLogDao
import com.example.data.local.BloodRequestDao
import com.example.data.local.CampaignDao
import com.example.data.local.DonationRecordDao
import com.example.data.local.DonorDao
import com.example.data.local.NoticeDao
import com.example.data.local.UserProfileDao
import com.example.data.model.AuditLog
import com.example.data.model.BloodRequest
import com.example.data.model.Campaign
import com.example.data.model.DonationRecord
import com.example.data.model.Donor
import com.example.data.model.Notice
import com.example.data.model.UserProfile
import kotlinx.coroutines.flow.Flow

class PranSancharRepository(
  private val donorDao: DonorDao,
  private val bloodRequestDao: BloodRequestDao,
  private val donationRecordDao: DonationRecordDao,
  private val campaignDao: CampaignDao,
  private val noticeDao: NoticeDao,
  private val auditLogDao: AuditLogDao,
  private val userProfileDao: UserProfileDao
) {
  val allDonors: Flow<List<Donor>> = donorDao.getAllDonors()
  val totalDonorsCount: Flow<Int> = donorDao.getTotalDonorsCount()

  val allRequests: Flow<List<BloodRequest>> = bloodRequestDao.getAllRequests()
  val activeRequests: Flow<List<BloodRequest>> = bloodRequestDao.getActiveRequests()
  val activeRequestsCount: Flow<Int> = bloodRequestDao.getActiveRequestsCount()

  val allDonations: Flow<List<DonationRecord>> = donationRecordDao.getAllDonations()
  val verifiedDonationsCount: Flow<Int> = donationRecordDao.getVerifiedDonationsCount()

  val allCampaigns: Flow<List<Campaign>> = campaignDao.getAllCampaigns()
  val allNotices: Flow<List<Notice>> = noticeDao.getAllNotices()
  val allAuditLogs: Flow<List<AuditLog>> = auditLogDao.getAllAuditLogs()
  val currentUserProfile: Flow<UserProfile?> = userProfileDao.getCurrentProfile()

  fun getAvailableCountByGroup(group: String): Flow<Int> = donorDao.getAvailableCountByGroup(group)

  suspend fun registerDonor(donor: Donor): Result<Long> {
    val existing = donorDao.findDonorByEmailOrPhone(donor.email, donor.phone)
    if (existing != null) {
      return Result.failure(Exception("A donor is already registered with this phone or email."))
    }
    val id = donorDao.insertDonor(donor)
    auditLogDao.insertLog(
      AuditLog(
        action = "DONOR_REGISTERED",
        performedBy = donor.fullName,
        details = "Registered as ${donor.bloodGroup} donor from ${donor.district}"
      )
    )
    return Result.success(id)
  }

  suspend fun updateDonorAvailability(donorId: Long, isAvailable: Boolean) {
    val donor = donorDao.getDonorById(donorId) ?: return
    donorDao.updateDonor(donor.copy(isAvailable = isAvailable))
    auditLogDao.insertLog(
      AuditLog(
        action = "DONOR_AVAILABILITY_CHANGED",
        performedBy = donor.fullName,
        details = "Availability set to: $isAvailable"
      )
    )
  }

  suspend fun updateDonorLastDonationDate(donorId: Long, newDate: String) {
    val donor = donorDao.getDonorById(donorId) ?: return
    donorDao.updateDonor(
      donor.copy(
        lastDonationDate = newDate,
        totalDonations = donor.totalDonations + 1,
        isAvailable = false // Automatically mark recently donated
      )
    )
    auditLogDao.insertLog(
      AuditLog(
        action = "DONOR_DONATION_RECORDED",
        performedBy = donor.fullName,
        details = "Donated on $newDate, total: ${donor.totalDonations + 1}"
      )
    )
  }

  suspend fun toggleDonorVerification(donorId: Long) {
    val donor = donorDao.getDonorById(donorId) ?: return
    val updatedStatus = !donor.isVerified
    donorDao.updateDonor(donor.copy(isVerified = updatedStatus))
    auditLogDao.insertLog(
      AuditLog(
        action = "DONOR_VERIFICATION_TOGGLED",
        performedBy = "Admin",
        details = "Verification for ${donor.fullName} set to: $updatedStatus"
      )
    )
  }

  suspend fun deleteDonor(donor: Donor) {
    donorDao.deleteDonor(donor)
    auditLogDao.insertLog(
      AuditLog(
        action = "DONOR_DELETED",
        performedBy = "Admin",
        details = "Deleted donor ${donor.fullName}"
      )
    )
  }

  suspend fun submitBloodRequest(request: BloodRequest): Long {
    val id = bloodRequestDao.insertRequest(request)
    auditLogDao.insertLog(
      AuditLog(
        action = "BLOOD_REQUEST_SUBMITTED",
        performedBy = request.contactPerson,
        details = "Requested ${request.unitsRequired} unit(s) of ${request.bloodGroup} at ${request.hospitalName}"
      )
    )
    return id
  }

  suspend fun updateRequestStatus(requestId: Long, newStatus: String, notes: String = "") {
    val request = bloodRequestDao.getRequestById(requestId) ?: return
    bloodRequestDao.updateRequest(request.copy(status = newStatus))
    auditLogDao.insertLog(
      AuditLog(
        action = "REQUEST_STATUS_UPDATED",
        performedBy = "Admin",
        details = "Request #${request.id} for ${request.patientName} set to: $newStatus"
      )
    )
  }

  suspend fun deleteRequest(request: BloodRequest) {
    bloodRequestDao.deleteRequest(request)
    auditLogDao.insertLog(
      AuditLog(
        action = "REQUEST_DELETED",
        performedBy = "Admin",
        details = "Deleted request #${request.id}"
      )
    )
  }

  suspend fun createCampaign(campaign: Campaign): Long {
    val id = campaignDao.insertCampaign(campaign)
    auditLogDao.insertLog(
      AuditLog(
        action = "CAMPAIGN_CREATED",
        performedBy = "Admin",
        details = "Created campaign: ${campaign.title}"
      )
    )
    return id
  }

  suspend fun deleteCampaign(campaign: Campaign) {
    campaignDao.deleteCampaign(campaign)
    auditLogDao.insertLog(
      AuditLog(
        action = "CAMPAIGN_DELETED",
        performedBy = "Admin",
        details = "Deleted campaign: ${campaign.title}"
      )
    )
  }

  suspend fun createNotice(notice: Notice): Long {
    val id = noticeDao.insertNotice(notice)
    auditLogDao.insertLog(
      AuditLog(
        action = "NOTICE_PUBLISHED",
        performedBy = "Admin",
        details = "Published notice: ${notice.title}"
      )
    )
    return id
  }

  suspend fun deleteNotice(notice: Notice) {
    noticeDao.deleteNotice(notice)
  }

  suspend fun updateUserProfile(profile: UserProfile) {
    userProfileDao.updateProfile(profile)
  }
}
