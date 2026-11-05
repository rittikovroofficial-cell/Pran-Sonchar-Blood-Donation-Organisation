package com.example.data.local

import com.example.data.model.AuditLog
import com.example.data.model.BloodRequest
import com.example.data.model.Campaign
import com.example.data.model.DonationRecord
import com.example.data.model.Donor
import com.example.data.model.Notice
import com.example.data.model.UserProfile

object InitialSeedData {
  val defaultUser = UserProfile(
    id = 1,
    fullName = "Tanvir Hasan",
    email = "tanvir.hasan@example.bd",
    phone = "01711002233",
    bloodGroup = "O+",
    role = "USER"
  )

  val donors = listOf(
    Donor(
      id = 1,
      fullName = "Dr. Arifur Rahman",
      phone = "01711234567",
      email = "arif.dr@pransanchar.org",
      bloodGroup = "O+",
      dateOfBirth = "1992-04-12",
      gender = "Male",
      district = "Dhaka",
      upazila = "Dhanmondi",
      detailedAddress = "Road 27, Dhanmondi",
      lastDonationDate = "2024-08-15",
      isAvailable = true,
      consentSharePhone = true,
      consentShareWhatsapp = true,
      isVerified = true,
      totalDonations = 14,
      notes = "Regular volunteer and voluntary blood donor."
    ),
    Donor(
      id = 2,
      fullName = "Nusrat Jahan Shumi",
      phone = "01819345678",
      email = "nusrat.shumi@example.com",
      bloodGroup = "A+",
      dateOfBirth = "1996-09-20",
      gender = "Female",
      district = "Dhaka",
      upazila = "Mirpur",
      detailedAddress = "Section 10, Mirpur",
      lastDonationDate = "2024-09-02",
      isAvailable = true,
      consentSharePhone = true,
      consentShareWhatsapp = true,
      isVerified = true,
      totalDonations = 8,
      notes = "Available for urgent calls around Mirpur and Agargaon."
    ),
    Donor(
      id = 3,
      fullName = "Shahadat Hossain Rifat",
      phone = "01912456789",
      email = "rifat.hossain@example.com",
      bloodGroup = "B+",
      dateOfBirth = "1994-01-15",
      gender = "Male",
      district = "Chattogram",
      upazila = "Panchlaish",
      detailedAddress = "Near Medical Gate 2, Chattogram",
      lastDonationDate = "2024-07-28",
      isAvailable = true,
      consentSharePhone = true,
      consentShareWhatsapp = true,
      isVerified = true,
      totalDonations = 11,
      notes = "Volunteer at Chattogram Medical College unit."
    ),
    Donor(
      id = 4,
      fullName = "Farzana Akhter Bristy",
      phone = "01623567890",
      email = "farzana.bristy@example.com",
      bloodGroup = "AB+",
      dateOfBirth = "1998-11-05",
      gender = "Female",
      district = "Sylhet",
      upazila = "Subidbazar",
      detailedAddress = "Kazi Elias, Sylhet Sadar",
      lastDonationDate = "2024-06-10",
      isAvailable = true,
      consentSharePhone = true,
      consentShareWhatsapp = false,
      isVerified = true,
      totalDonations = 5,
      notes = "Ready to donate in Sylhet city area."
    ),
    Donor(
      id = 5,
      fullName = "Mahmudul Hasan Tanmoy",
      phone = "01534678901",
      email = "tanmoy.hasan@example.com",
      bloodGroup = "O-",
      dateOfBirth = "1991-03-18",
      gender = "Male",
      district = "Dhaka",
      upazila = "Uttara",
      detailedAddress = "Sector 7, Uttara",
      lastDonationDate = "2024-08-01",
      isAvailable = true,
      consentSharePhone = true,
      consentShareWhatsapp = true,
      isVerified = true,
      totalDonations = 19,
      notes = "Rare group O- donor. Dedicated emergency responder."
    ),
    Donor(
      id = 6,
      fullName = "Sadia Tasnim",
      phone = "01725789012",
      email = "sadia.tasnim@example.com",
      bloodGroup = "A-",
      dateOfBirth = "1997-07-22",
      gender = "Female",
      district = "Rajshahi",
      upazila = "Boalia",
      detailedAddress = "Kazihata, Rajshahi",
      lastDonationDate = "2024-05-14",
      isAvailable = true,
      consentSharePhone = true,
      consentShareWhatsapp = true,
      isVerified = true,
      totalDonations = 6,
      notes = "Available for urgent calls at RMCH."
    ),
    Donor(
      id = 7,
      fullName = "Kazi Imtiaz Ahmed",
      phone = "01836890123",
      email = "imtiaz.kazi@example.com",
      bloodGroup = "B-",
      dateOfBirth = "1993-12-30",
      gender = "Male",
      district = "Khulna",
      upazila = "Sonadanga",
      detailedAddress = "Boyra Main Road, Khulna",
      lastDonationDate = "2024-07-04",
      isAvailable = true,
      consentSharePhone = false,
      consentShareWhatsapp = true,
      isVerified = true,
      totalDonations = 9,
      notes = "Prefers WhatsApp contact for coordination."
    ),
    Donor(
      id = 8,
      fullName = "Rezaul Karim",
      phone = "01947901234",
      email = "rezaul.karim@example.com",
      bloodGroup = "AB-",
      dateOfBirth = "1989-05-11",
      gender = "Male",
      district = "Dhaka",
      upazila = "Mohammadpur",
      detailedAddress = "Tajmahal Road, Mohammadpur",
      lastDonationDate = "2024-10-25",
      isAvailable = false,
      consentSharePhone = true,
      consentShareWhatsapp = true,
      isVerified = true,
      totalDonations = 16,
      notes = "Recently donated. Next eligible in January."
    )
  )

  val requests = listOf(
    BloodRequest(
      id = 1,
      patientName = "Mrs. Rahima Begum (62)",
      bloodGroup = "O+",
      unitsRequired = 2,
      hospitalName = "Dhaka Medical College Hospital (DMCH)",
      district = "Dhaka",
      upazila = "Bakshibazar",
      hospitalAddress = "Ward 12, Bed 4, DMCH Emergency Complex",
      requiredDate = "Today / Urgent",
      requiredTime = "Within 4 Hours",
      contactPerson = "Mohammad Selim (Son)",
      contactPhone = "01712987654",
      urgencyLevel = "EMERGENCY",
      status = "ACTIVE",
      additionalInfo = "Cardiac bypass surgery scheduled. Immediate blood grouping crossmatch required."
    ),
    BloodRequest(
      id = 2,
      patientName = "Master Siam (8)",
      bloodGroup = "B+",
      unitsRequired = 1,
      hospitalName = "Bangabandhu Sheikh Mujib Medical University (BSMMU)",
      district = "Dhaka",
      upazila = "Shahbagh",
      hospitalAddress = "Block D, Pediatric Hematology Ward",
      requiredDate = "Tomorrow Morning",
      requiredTime = "10:00 AM",
      contactPerson = "Anwar Hossain (Father)",
      contactPhone = "01815876543",
      urgencyLevel = "URGENT",
      status = "ACTIVE",
      additionalInfo = "Thalassemia regular blood transfusion. Fresh blood required."
    ),
    BloodRequest(
      id = 3,
      patientName = "Sultana Razia (29)",
      bloodGroup = "AB-",
      unitsRequired = 2,
      hospitalName = "Chittagong Medical College Hospital (CMCH)",
      district = "Chattogram",
      upazila = "Panchlaish",
      hospitalAddress = "Gynae & Obs OT-2, CMCH",
      requiredDate = "Immediate",
      requiredTime = "Immediate Emergency",
      contactPerson = "Engr. Kamrul Islam (Husband)",
      contactPhone = "01918765432",
      urgencyLevel = "EMERGENCY",
      status = "ACTIVE",
      additionalInfo = "Emergency Caesarean section. Rare AB- blood needed urgently!"
    ),
    BloodRequest(
      id = 4,
      patientName = "Abdul Malek (45)",
      bloodGroup = "A+",
      unitsRequired = 1,
      hospitalName = "Sylhet MAG Osmani Medical College Hospital",
      district = "Sylhet",
      upazila = "Kajolshah",
      hospitalAddress = "Orthopedic Ward 5",
      requiredDate = "In 2 Days",
      requiredTime = "11:00 AM",
      contactPerson = "Jamil Ahmed (Brother)",
      contactPhone = "01614654321",
      urgencyLevel = "ROUTINE",
      status = "ACTIVE",
      additionalInfo = "Post road traffic accident bone fixation surgery."
    )
  )

  val donations = listOf(
    DonationRecord(
      id = 1,
      donorId = 1,
      donorName = "Dr. Arifur Rahman",
      bloodGroup = "O+",
      donationDate = "2024-08-15",
      hospitalName = "Dhaka Medical College Hospital",
      district = "Dhaka",
      units = 1,
      relatedRequestId = null,
      isVerified = true,
      certificateNumber = "PS-DON-2024-0891"
    ),
    DonationRecord(
      id = 2,
      donorId = 2,
      donorName = "Nusrat Jahan Shumi",
      bloodGroup = "A+",
      donationDate = "2024-09-02",
      hospitalName = "National Heart Foundation, Mirpur",
      district = "Dhaka",
      units = 1,
      relatedRequestId = null,
      isVerified = true,
      certificateNumber = "PS-DON-2024-0914"
    ),
    DonationRecord(
      id = 3,
      donorId = 3,
      donorName = "Shahadat Hossain Rifat",
      bloodGroup = "B+",
      donationDate = "2024-07-28",
      hospitalName = "Chittagong Medical College Hospital",
      district = "Chattogram",
      units = 1,
      relatedRequestId = null,
      isVerified = true,
      certificateNumber = "PS-DON-2024-0733"
    ),
    DonationRecord(
      id = 4,
      donorId = 5,
      donorName = "Mahmudul Hasan Tanmoy",
      bloodGroup = "O-",
      donationDate = "2024-08-01",
      hospitalName = "Evercare Hospital, Dhaka",
      district = "Dhaka",
      units = 1,
      relatedRequestId = null,
      isVerified = true,
      certificateNumber = "PS-DON-2024-0803"
    )
  )

  val campaigns = listOf(
    Campaign(
      id = 1,
      title = "Dhaka University Campus Voluntary Blood Camp",
      titleBn = "ঢাকা বিশ্ববিদ্যালয় ক্যাম্পাস স্বেচ্ছায় রক্তদান ক্যাম্প",
      description = "Annual voluntary blood donation drive organized by Pran Sanchar in collaboration with student volunteers. Free blood grouping and screening provided.",
      descriptionBn = "ঢাকা বিশ্ববিদ্যালয় টিএসসি চত্বরে প্রাণ সঞ্চারের বার্ষিক স্বেচ্ছায় রক্তদান কর্মসূচি। বিনামূল্যে রক্তের গ্রুপ নির্ণয় এবং স্বাস্থ্য পরামর্শ দেওয়া হবে।",
      date = "Next Friday, 10:00 AM - 5:00 PM",
      time = "10:00 AM - 5:00 PM",
      location = "TSC Complex, University of Dhaka",
      locationBn = "টিএসসি চত্বর, ঢাকা বিশ্ববিদ্যালয়",
      targetUnits = 250,
      collectedUnits = 86,
      status = "UPCOMING"
    ),
    Campaign(
      id = 2,
      title = "Chittagong Port City Emergency Blood Drive",
      titleBn = "চট্টগ্রাম বন্দর নগরী জরুরি রক্ত সংগ্রহ অভিযান",
      description = "Special blood camp to support emergency trauma and thalassemia patients in Chittagong division.",
      descriptionBn = "চট্টগ্রাম মেডিকেল কলেজ সংলগ্ন এলাকায় থ্যালাসেমিয়া ও ট্রমা রোগীদের সহায়তায় বিশেষ রক্তদান ক্যাম্প।",
      date = "Next Sunday, 9:00 AM - 4:00 PM",
      time = "9:00 AM - 4:00 PM",
      location = "GEC Convention Center, Chattogram",
      locationBn = "জিইসি কনভেনশন সেন্টার, চট্টগ্রাম",
      targetUnits = 180,
      collectedUnits = 120,
      status = "UPCOMING"
    )
  )

  val notices = listOf(
    Notice(
      id = 1,
      title = "Emergency Need for Negative Blood Groups (A-, B-, O-, AB-)",
      titleBn = "জরুরি আবেদন: নেগেটিভ গ্রুপের রক্তদাতাদের এগিয়ে আসার আহ্বান",
      content = "Blood banks across major tertiary hospitals are facing critical shortages of negative blood types. Healthy donors of A-, B-, O-, and AB- groups are requested to update their availability status in Pran Sanchar platform.",
      contentBn = "সরকারি ও বেসরকারি হাসপাতালগুলোতে নেগেটিভ গ্রুপের রক্তের তীব্র সংকট দেখা দিয়েছে। যে সকল সুস্থ রক্তদাতার গ্রুপ A-, B-, O-, AB- তাদের অ্যাপে প্রোফাইল সচল রাখার বিনীত অনুরোধ রইল।",
      category = "Urgent Appeal",
      priority = "Urgent",
      isPinned = true,
      publishedAt = "2024-11-04"
    ),
    Notice(
      id = 2,
      title = "Guidelines on Blood Donation Eligibility & Post-Donation Care",
      titleBn = "রক্তদানের যোগ্যতা ও রক্তদান পরবর্তী স্বাস্থ্য সচেতনতা নির্দেশিকা",
      content = "Weight must be 48kg+, age 18-60 years, hemoglobin level 12.5g/dL+. Please drink plenty of water and rest for 15 minutes after donation.",
      contentBn = "রক্তদাতার বয়স ১৮-৬০ বছর, ওজন নুন্যতম ৪৮ কেজি ও হিমোগ্লোবিন স্বাভাবিক হওয়া আবশ্যক। রক্তদানের পর পর্যাপ্ত পানি ও জুস পান করুন।",
      category = "Health Guide",
      priority = "Normal",
      isPinned = false,
      publishedAt = "2024-10-30"
    )
  )

  val auditLogs = listOf(
    AuditLog(
      id = 1,
      action = "DATABASE_INITIALIZED",
      performedBy = "System",
      details = "Pran Sanchar voluntary database initiated with verified Bangladeshi donor records."
    ),
    AuditLog(
      id = 2,
      action = "REQUEST_APPROVED",
      performedBy = "Admin",
      details = "Approved emergency request for DMCH patient Rahima Begum."
    )
  )
}
