package com.example.ui.localization

enum class AppLanguage {
  BENGALI, ENGLISH
}

object AppStrings {
  fun orgName(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "প্রাণ সঞ্চার রক্তদান সংস্থা"
    AppLanguage.ENGLISH -> "PRAN SANCHAR BLOOD DONATION ORGANIZATION"
  }

  fun orgTagline(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "রক্তের বন্ধনে, প্রাণের স্পন্দনে"
    AppLanguage.ENGLISH -> "In bonds of blood, in beats of life"
  }

  fun navHome(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "হোম"
    AppLanguage.ENGLISH -> "Home"
  }

  fun navDonors(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "রক্তদাতা"
    AppLanguage.ENGLISH -> "Donors"
  }

  fun navRequests(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "রক্তের আবেদন"
    AppLanguage.ENGLISH -> "Requests"
  }

  fun navCampaigns(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "ক্যাম্পেইন"
    AppLanguage.ENGLISH -> "Campaigns"
  }

  fun navDashboard(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "ড্যাশবোর্ড"
    AppLanguage.ENGLISH -> "Dashboard"
  }

  fun searchDonorsBtn(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "রক্তদাতা খুঁজুন"
    AppLanguage.ENGLISH -> "Find Donors"
  }

  fun becomeDonorBtn(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "রক্তদাতা হন"
    AppLanguage.ENGLISH -> "Become a Donor"
  }

  fun emergencyRequestBtn(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "জরুরি আবেদন"
    AppLanguage.ENGLISH -> "Emergency Request"
  }

  fun totalDonors(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "নিবন্ধিত রক্তদাতা"
    AppLanguage.ENGLISH -> "Registered Donors"
  }

  fun totalDonations(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "সফল রক্তদান"
    AppLanguage.ENGLISH -> "Successful Donations"
  }

  fun activeRequests(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "সক্রিয় রক্তের আবেদন"
    AppLanguage.ENGLISH -> "Active Requests"
  }

  fun districtsCovered(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "জেলায় সেবা"
    AppLanguage.ENGLISH -> "Districts Active"
  }

  fun bloodAvailability(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "রক্তের গ্রুপ ও প্রাপ্যতা"
    AppLanguage.ENGLISH -> "Blood Group Availability"
  }

  fun recentEmergencyTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "জরুরি রক্তের প্রয়োজন"
    AppLanguage.ENGLISH -> "Urgent Blood Needed"
  }

  fun viewAll(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "সব দেখুন"
    AppLanguage.ENGLISH -> "View All"
  }

  fun howItWorksTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "রক্তদানের নিয়ম ও তথ্য"
    AppLanguage.ENGLISH -> "How Blood Donation Works"
  }

  fun upcomingEventsTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "আসন্ন ক্যাম্পেইন ও ইভেন্ট"
    AppLanguage.ENGLISH -> "Upcoming Campaigns & Events"
  }

  fun noticesTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "জরুরি নোটিশ ও সংবাদ"
    AppLanguage.ENGLISH -> "Notices & Announcements"
  }

  fun contactUsTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "যোগাযোগ ও হেল্পলাইন"
    AppLanguage.ENGLISH -> "Contact & 24/7 Helpline"
  }

  fun filterBloodGroup(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "রক্তের গ্রুপ"
    AppLanguage.ENGLISH -> "Blood Group"
  }

  fun filterDistrict(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "জেলা"
    AppLanguage.ENGLISH -> "District"
  }

  fun filterUpazila(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "উপজেলা / এলাকা"
    AppLanguage.ENGLISH -> "Upazila / Area"
  }

  fun filterAvailability(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "প্রাপ্যতা"
    AppLanguage.ENGLISH -> "Availability"
  }

  fun availableNow(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "রক্তদানে প্রস্তুত"
    AppLanguage.ENGLISH -> "Available Now"
  }

  fun recentlyDonated(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "সম্প্রতি রক্ত দিয়েছেন"
    AppLanguage.ENGLISH -> "Recently Donated"
  }

  fun unavailable(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "বর্তমানে অপারগ"
    AppLanguage.ENGLISH -> "Unavailable"
  }

  fun callDonor(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "কল করুন"
    AppLanguage.ENGLISH -> "Call Donor"
  }

  fun whatsappDonor(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "হোয়াটসঅ্যাপ"
    AppLanguage.ENGLISH -> "WhatsApp"
  }

  fun sendAlert(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "বার্তা পাঠান"
    AppLanguage.ENGLISH -> "Send Alert"
  }

  fun privacyNotice(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "রক্তদাতার গোপনীয়তা সম্পূর্ণ সুরক্ষিত।"
    AppLanguage.ENGLISH -> "Donor privacy is strictly protected."
  }

  fun unitsNeeded(lang: AppLanguage, count: Int): String = when (lang) {
    AppLanguage.BENGALI -> "$count ব্যাগ রক্ত প্রয়োজন"
    AppLanguage.ENGLISH -> "$count unit(s) needed"
  }

  fun hospital(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "হাসপাতাল"
    AppLanguage.ENGLISH -> "Hospital"
  }

  fun patient(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "রোগী"
    AppLanguage.ENGLISH -> "Patient"
  }

  fun dateNeeded(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "প্রয়োজনের তারিখ"
    AppLanguage.ENGLISH -> "Required Date"
  }

  fun urgency(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "জরুরিতা"
    AppLanguage.ENGLISH -> "Urgency"
  }

  fun contactPerson(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "যোগাযোগ ব্যক্তি"
    AppLanguage.ENGLISH -> "Contact Person"
  }

  fun shareRequest(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "শেয়ার করুন"
    AppLanguage.ENGLISH -> "Share Request"
  }

  fun adminMode(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "অ্যাডমিন প্যানেল"
    AppLanguage.ENGLISH -> "Admin Panel"
  }

  fun userMode(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "ইউজার প্রোফাইল"
    AppLanguage.ENGLISH -> "User Profile"
  }

  fun enterAdminPin(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "অ্যাডমিন পিন দিন"
    AppLanguage.ENGLISH -> "Enter Admin PIN"
  }

  fun pinPlaceholder(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "ডিফল্ট পিন: 7860"
    AppLanguage.ENGLISH -> "Default PIN: 7860"
  }

  fun verifyAndLogin(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "যাচাই ও প্রবেশ"
    AppLanguage.ENGLISH -> "Verify & Login"
  }

  fun exitAdmin(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "লগআউট"
    AppLanguage.ENGLISH -> "Exit Admin"
  }

  fun approve(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "অনুমোদন করুন"
    AppLanguage.ENGLISH -> "Approve"
  }

  fun reject(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "বাতিল করুন"
    AppLanguage.ENGLISH -> "Reject"
  }

  fun markFulfilled(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "সম্পন্ন চিহ্নিত করুন"
    AppLanguage.ENGLISH -> "Mark Fulfilled"
  }

  fun verifiedDonor(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "যাচাইকৃত রক্তদাতা"
    AppLanguage.ENGLISH -> "Verified Donor"
  }

  fun loginTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "অ্যাকাউন্টে লগইন করুন"
    AppLanguage.ENGLISH -> "Sign In to Your Account"
  }

  fun emailLabel(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "ইমেইল ঠিকানা"
    AppLanguage.ENGLISH -> "Email Address"
  }

  fun passwordLabel(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "পাসওয়ার্ড"
    AppLanguage.ENGLISH -> "Password"
  }

  fun forgotPassword(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "পাসওয়ার্ড ভুলে গেছেন?"
    AppLanguage.ENGLISH -> "Forgot Password?"
  }

  fun signInBtn(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "লগইন করুন"
    AppLanguage.ENGLISH -> "Sign In"
  }

  fun rememberMe(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "আমাকে মনে রাখুন"
    AppLanguage.ENGLISH -> "Remember Me"
  }

  fun noAccountText(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "কোনো অ্যাকাউন্ট নেই?"
    AppLanguage.ENGLISH -> "Don't have an account?"
  }

  fun downloadAppTitle(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "মোবাইল অ্যাপ ডাউনলোড করুন"
    AppLanguage.ENGLISH -> "Download Mobile App"
  }

  fun downloadAppDesc(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "জরুরি মুহূর্তে অফলাইনে রক্তদাতা খুঁজতে সরাসরি আপনার ফোনে অ্যাপটি ইনস্টল রাখুন।"
    AppLanguage.ENGLISH -> "Keep the app installed on your phone to find voluntary donors even with low connectivity."
  }

  fun downloadApkBtn(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "APK ডাউনলোড করুন"
    AppLanguage.ENGLISH -> "Download APK"
  }

  fun shareAppLinkBtn(lang: AppLanguage): String = when (lang) {
    AppLanguage.BENGALI -> "অ্যাপ লিংক শেয়ার করুন"
    AppLanguage.ENGLISH -> "Share App Link"
  }
}
