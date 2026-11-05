package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.BangladeshLocations
import com.example.data.model.BloodGroup
import com.example.data.model.DonorAvailability
import com.example.data.model.UrgencyLevel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read app name from strings xml`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Pran Sanchar", appName)
  }

  @Test
  fun `verify all 8 blood groups exist`() {
    val groups = BloodGroup.entries
    assertEquals(8, groups.size)
    assertTrue(groups.any { it.display == "A+" })
    assertTrue(groups.any { it.display == "O+" })
    assertTrue(groups.any { it.display == "AB-" })
    assertTrue(groups.any { it.display == "O-" })
  }

  @Test
  fun `verify Bangladesh districts dataset`() {
    val districts = BangladeshLocations.districts
    assertTrue(districts.size >= 30)
    assertTrue(districts.any { it.nameEn == "Dhaka" && it.nameBn == "ঢাকা" })
    assertTrue(districts.any { it.nameEn == "Chattogram" && it.nameBn == "চট্টগ্রাম" })
    assertTrue(districts.any { it.nameEn == "Sylhet" && it.nameBn == "সিলেট" })
  }

  @Test
  fun `verify urgency levels and donor statuses`() {
    assertNotNull(UrgencyLevel.EMERGENCY)
    assertNotNull(DonorAvailability.AVAILABLE)
    assertEquals("Available to Donate", DonorAvailability.AVAILABLE.titleEn)
  }

  @Test
  fun `verify Screen LOGIN enum exists`() {
    val screens = com.example.ui.viewmodel.Screen.entries
    assertTrue(screens.contains(com.example.ui.viewmodel.Screen.LOGIN))
  }
}
