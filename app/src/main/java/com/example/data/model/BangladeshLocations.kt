package com.example.data.model

data class DistrictItem(
  val nameEn: String,
  val nameBn: String,
  val divisionEn: String,
  val divisionBn: String
)

object BangladeshLocations {
  val districts = listOf(
    DistrictItem("Dhaka", "ঢাকা", "Dhaka", "ঢাকা"),
    DistrictItem("Gazipur", "গাজীপুর", "Dhaka", "ঢাকা"),
    DistrictItem("Narayanganj", "নারায়ণগঞ্জ", "Dhaka", "ঢাকা"),
    DistrictItem("Tangail", "টাঙ্গাইল", "Dhaka", "ঢাকা"),
    DistrictItem("Narsingdi", "নরসিংদী", "Dhaka", "ঢাকা"),
    DistrictItem("Faridpur", "ফরিদপুর", "Dhaka", "ঢাকা"),
    DistrictItem("Manikganj", "মানিকগঞ্জ", "Dhaka", "ঢাকা"),
    DistrictItem("Munshiganj", "মুন্সিগঞ্জ", "Dhaka", "ঢাকা"),
    DistrictItem("Kishoreganj", "কিশোরগঞ্জ", "Dhaka", "ঢাকা"),

    DistrictItem("Chattogram", "চট্টগ্রাম", "Chattogram", "চট্টগ্রাম"),
    DistrictItem("Cox's Bazar", "কক্সবাজার", "Chattogram", "চট্টগ্রাম"),
    DistrictItem("Cumilla", "কুমিল্লা", "Chattogram", "চট্টগ্রাম"),
    DistrictItem("Feni", "ফেনী", "Chattogram", "চট্টগ্রাম"),
    DistrictItem("Brahmanbaria", "ব্রাহ্মণবাড়িয়া", "Chattogram", "চট্টগ্রাম"),
    DistrictItem("Noakhali", "নোয়াখালী", "Chattogram", "চট্টগ্রাম"),
    DistrictItem("Chandpur", "চাঁদপুর", "Chattogram", "চট্টগ্রাম"),

    DistrictItem("Sylhet", "সিলেট", "Sylhet", "সিলেট"),
    DistrictItem("Moulvibazar", "মৌলভীবাজার", "Sylhet", "সিলেট"),
    DistrictItem("Habiganj", "হবিগঞ্জ", "Sylhet", "সিলেট"),
    DistrictItem("Sunamganj", "সুনামগঞ্জ", "Sylhet", "সিলেট"),

    DistrictItem("Rajshahi", "রাজশাহী", "Rajshahi", "রাজশাহী"),
    DistrictItem("Bogura", "বগুড়া", "Rajshahi", "রাজশাহী"),
    DistrictItem("Pabna", "পাবনা", "Rajshahi", "রাজশাহী"),
    DistrictItem("Sirajganj", "সিরাজগঞ্জ", "Rajshahi", "রাজশাহী"),
    DistrictItem("Naogaon", "নওগাঁ", "Rajshahi", "রাজশাহী"),

    DistrictItem("Khulna", "খুলনা", "Khulna", "খুলনা"),
    DistrictItem("Jashore", "যশোর", "Khulna", "খুলনা"),
    DistrictItem("Kushtia", "কুষ্টিয়া", "Khulna", "খুলনা"),
    DistrictItem("Satkhira", "সাতক্ষীরা", "Khulna", "খুলনা"),

    DistrictItem("Barishal", "বরিশাল", "Barishal", "বরিশাল"),
    DistrictItem("Patuakhali", "পটুয়াখালী", "Barishal", "বরিশাল"),
    DistrictItem("Bhola", "ভোলা", "Barishal", "বরিশাল"),

    DistrictItem("Rangpur", "রংপুর", "Rangpur", "রংপুর"),
    DistrictItem("Dinajpur", "দিনাজপুর", "Rangpur", "রংপুর"),

    DistrictItem("Mymensingh", "ময়মনসিংহ", "Mymensingh", "ময়মনসিংহ"),
    DistrictItem("Jamalpur", "জামালপুর", "Mymensingh", "ময়মনসিংহ"),
    DistrictItem("Netrokona", "নেত্রকোণা", "Mymensingh", "ময়মনসিংহ")
  )

  val popularHospitals = listOf(
    "Dhaka Medical College Hospital (DMCH)",
    "Bangabandhu Sheikh Mujib Medical University (BSMMU)",
    "National Institute of Cardiovascular Diseases (NICVD)",
    "Square Hospital, Panthapath, Dhaka",
    "United Hospital, Gulshan, Dhaka",
    "Evercare Hospital, Bashundhara, Dhaka",
    "National Heart Foundation, Mirpur",
    "Kurmitola General Hospital, Dhaka",
    "Chittagong Medical College Hospital (CMCH)",
    "Sylhet MAG Osmani Medical College Hospital",
    "Rajshahi Medical College Hospital (RMCH)",
    "Khulna Medical College Hospital",
    "Sher-e-Bangla Medical College Hospital, Barishal",
    "Mymensingh Medical College Hospital",
    "Rangpur Medical College Hospital"
  )
}
