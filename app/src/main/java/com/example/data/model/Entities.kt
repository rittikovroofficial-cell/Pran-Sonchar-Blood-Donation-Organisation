package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
data class UserProfile(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val fullName: String,
  val email: String,
  val phone: String,
  val bloodGroup: String,
  val role: String = "USER", // "USER" or "SUPER_ADMIN"
  val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "donors")
data class Donor(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val userId: Long? = null,
  val fullName: String,
  val phone: String,
  val email: String,
  val bloodGroup: String,
  val dateOfBirth: String,
  val gender: String = "Not Specified",
  val district: String,
  val upazila: String,
  val detailedAddress: String = "",
  val lastDonationDate: String = "", // YYYY-MM-DD or empty
  val isAvailable: Boolean = true,
  val consentSharePhone: Boolean = true,
  val consentShareWhatsapp: Boolean = true,
  val isVerified: Boolean = false,
  val totalDonations: Int = 0,
  val notes: String = "",
  val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "blood_requests")
data class BloodRequest(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val userId: Long? = null,
  val patientName: String,
  val bloodGroup: String,
  val unitsRequired: Int = 1,
  val hospitalName: String,
  val district: String,
  val upazila: String,
  val hospitalAddress: String = "",
  val requiredDate: String,
  val requiredTime: String = "Anytime",
  val contactPerson: String,
  val contactPhone: String,
  val urgencyLevel: String = "EMERGENCY", // EMERGENCY, URGENT, ROUTINE
  val status: String = "ACTIVE", // PENDING, APPROVED, ACTIVE, FULFILLED, CANCELLED, REJECTED
  val additionalInfo: String = "",
  val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "donations")
data class DonationRecord(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val donorId: Long? = null,
  val donorName: String,
  val bloodGroup: String,
  val donationDate: String,
  val hospitalName: String,
  val district: String,
  val units: Int = 1,
  val relatedRequestId: Long? = null,
  val isVerified: Boolean = true,
  val certificateNumber: String = "",
  val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "campaigns")
data class Campaign(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val title: String,
  val titleBn: String,
  val description: String,
  val descriptionBn: String,
  val date: String,
  val time: String,
  val location: String,
  val locationBn: String,
  val targetUnits: Int = 100,
  val collectedUnits: Int = 0,
  val status: String = "UPCOMING", // UPCOMING, ONGOING, COMPLETED
  val organizer: String = "Pran Sanchar Volunteer Wing",
  val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "notices")
data class Notice(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val title: String,
  val titleBn: String,
  val content: String,
  val contentBn: String,
  val category: String = "General",
  val priority: String = "Normal", // Urgent, High, Normal
  val isPinned: Boolean = false,
  val publishedAt: String,
  val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "audit_logs")
data class AuditLog(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val action: String,
  val performedBy: String,
  val details: String,
  val timestamp: Long = System.currentTimeMillis()
)
