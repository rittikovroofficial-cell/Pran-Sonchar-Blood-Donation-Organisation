package com.example.data.model

enum class BloodGroup(val display: String, val displayBn: String) {
  A_POS("A+", "এ পজিটিভ (A+)"),
  A_NEG("A-", "এ নেগেটিভ (A-)"),
  B_POS("B+", "বি পজিটিভ (B+)"),
  B_NEG("B-", "বি নেগেটিভ (B-)"),
  AB_POS("AB+", "এবি পজিটিভ (AB+)"),
  AB_NEG("AB-", "এবি নেগেটিভ (AB-)"),
  O_POS("O+", "ও পজিটিভ (O+)"),
  O_NEG("O-", "ও নেগেটিভ (O-)");

  companion object {
    fun fromDisplay(value: String): BloodGroup {
      return entries.firstOrNull { it.display.equals(value.trim(), ignoreCase = true) } ?: O_POS
    }
  }
}

enum class UrgencyLevel(val titleEn: String, val titleBn: String) {
  EMERGENCY("Immediate Emergency", "জরুরি (তাৎক্ষণিক)"),
  URGENT("Within 24 Hours", "২৪ ঘণ্টার মধ্যে"),
  ROUTINE("Scheduled / Routine", "পরিকল্পিত / স্বাভাবিক")
}

enum class RequestStatus(val titleEn: String, val titleBn: String) {
  PENDING("Pending Review", "পর্যালোচনাধীন"),
  APPROVED("Approved", "অনুমোদিত"),
  ACTIVE("Active Needed", "সক্রিয় প্রয়োজন"),
  FULFILLED("Fulfilled", "সম্পন্ন হয়েছে"),
  CANCELLED("Cancelled", "বাতিল"),
  REJECTED("Rejected", "প্রত্যাখ্যাত")
}

enum class DonorAvailability(val titleEn: String, val titleBn: String) {
  AVAILABLE("Available to Donate", "রক্তদানে প্রস্তুত"),
  RECENTLY_DONATED("Recently Donated", "সম্প্রতি রক্ত দিয়েছেন"),
  UNAVAILABLE("Temporarily Unavailable", "সাময়িক অপারগ")
}
