package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.AuditLog
import com.example.data.model.BloodRequest
import com.example.data.model.Campaign
import com.example.data.model.DonationRecord
import com.example.data.model.Donor
import com.example.data.model.Notice
import com.example.data.model.UserProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface DonorDao {
  @Query("SELECT * FROM donors ORDER BY isAvailable DESC, isVerified DESC, id DESC")
  fun getAllDonors(): Flow<List<Donor>>

  @Query("SELECT * FROM donors WHERE id = :id")
  suspend fun getDonorById(id: Long): Donor?

  @Query("SELECT * FROM donors WHERE email = :email OR phone = :phone LIMIT 1")
  suspend fun findDonorByEmailOrPhone(email: String, phone: String): Donor?

  @Query("SELECT COUNT(*) FROM donors")
  fun getTotalDonorsCount(): Flow<Int>

  @Query("SELECT COUNT(*) FROM donors WHERE bloodGroup = :bloodGroup AND isAvailable = 1")
  fun getAvailableCountByGroup(bloodGroup: String): Flow<Int>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertDonor(donor: Donor): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertDonors(donors: List<Donor>)

  @Update
  suspend fun updateDonor(donor: Donor)

  @Delete
  suspend fun deleteDonor(donor: Donor)
}

@Dao
interface BloodRequestDao {
  @Query("SELECT * FROM blood_requests ORDER BY CASE urgencyLevel WHEN 'EMERGENCY' THEN 1 WHEN 'URGENT' THEN 2 ELSE 3 END, id DESC")
  fun getAllRequests(): Flow<List<BloodRequest>>

  @Query("SELECT * FROM blood_requests WHERE status IN ('ACTIVE', 'APPROVED') ORDER BY CASE urgencyLevel WHEN 'EMERGENCY' THEN 1 WHEN 'URGENT' THEN 2 ELSE 3 END, id DESC")
  fun getActiveRequests(): Flow<List<BloodRequest>>

  @Query("SELECT COUNT(*) FROM blood_requests WHERE status IN ('ACTIVE', 'APPROVED')")
  fun getActiveRequestsCount(): Flow<Int>

  @Query("SELECT * FROM blood_requests WHERE id = :id")
  suspend fun getRequestById(id: Long): BloodRequest?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertRequest(request: BloodRequest): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertRequests(requests: List<BloodRequest>)

  @Update
  suspend fun updateRequest(request: BloodRequest)

  @Delete
  suspend fun deleteRequest(request: BloodRequest)
}

@Dao
interface DonationRecordDao {
  @Query("SELECT * FROM donations ORDER BY id DESC")
  fun getAllDonations(): Flow<List<DonationRecord>>

  @Query("SELECT COUNT(*) FROM donations WHERE isVerified = 1")
  fun getVerifiedDonationsCount(): Flow<Int>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertDonation(donation: DonationRecord): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertDonations(donations: List<DonationRecord>)
}

@Dao
interface CampaignDao {
  @Query("SELECT * FROM campaigns ORDER BY id DESC")
  fun getAllCampaigns(): Flow<List<Campaign>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertCampaign(campaign: Campaign): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertCampaigns(campaigns: List<Campaign>)

  @Update
  suspend fun updateCampaign(campaign: Campaign)

  @Delete
  suspend fun deleteCampaign(campaign: Campaign)
}

@Dao
interface NoticeDao {
  @Query("SELECT * FROM notices ORDER BY isPinned DESC, id DESC")
  fun getAllNotices(): Flow<List<Notice>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertNotice(notice: Notice): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertNotices(notices: List<Notice>)

  @Update
  suspend fun updateNotice(notice: Notice)

  @Delete
  suspend fun deleteNotice(notice: Notice)
}

@Dao
interface AuditLogDao {
  @Query("SELECT * FROM audit_logs ORDER BY id DESC LIMIT 50")
  fun getAllAuditLogs(): Flow<List<AuditLog>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertLog(log: AuditLog): Long
}

@Dao
interface UserProfileDao {
  @Query("SELECT * FROM profiles LIMIT 1")
  fun getCurrentProfile(): Flow<UserProfile?>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertProfile(profile: UserProfile): Long

  @Update
  suspend fun updateProfile(profile: UserProfile)
}
