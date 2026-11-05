package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.AuditLog
import com.example.data.model.BloodRequest
import com.example.data.model.Campaign
import com.example.data.model.DonationRecord
import com.example.data.model.Donor
import com.example.data.model.Notice
import com.example.data.model.UserProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
  entities = [
    UserProfile::class,
    Donor::class,
    BloodRequest::class,
    DonationRecord::class,
    Campaign::class,
    Notice::class,
    AuditLog::class
  ],
  version = 1,
  exportSchema = false
)
abstract class PranSancharDatabase : RoomDatabase() {
  abstract fun donorDao(): DonorDao
  abstract fun bloodRequestDao(): BloodRequestDao
  abstract fun donationRecordDao(): DonationRecordDao
  abstract fun campaignDao(): CampaignDao
  abstract fun noticeDao(): NoticeDao
  abstract fun auditLogDao(): AuditLogDao
  abstract fun userProfileDao(): UserProfileDao

  companion object {
    @Volatile
    private var INSTANCE: PranSancharDatabase? = null

    fun getDatabase(context: Context, scope: CoroutineScope): PranSancharDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          PranSancharDatabase::class.java,
          "pran_sanchar_db"
        )
          .fallbackToDestructiveMigration()
          .addCallback(DatabaseCallback(scope))
          .build()
        INSTANCE = instance
        instance
      }
    }

    private class DatabaseCallback(
      private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
      override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        INSTANCE?.let { database ->
          scope.launch(Dispatchers.IO) {
            populateDatabase(database)
          }
        }
      }

      suspend fun populateDatabase(db: PranSancharDatabase) {
        db.userProfileDao().insertProfile(InitialSeedData.defaultUser)
        db.donorDao().insertDonors(InitialSeedData.donors)
        db.bloodRequestDao().insertRequests(InitialSeedData.requests)
        db.donationRecordDao().insertDonations(InitialSeedData.donations)
        db.campaignDao().insertCampaigns(InitialSeedData.campaigns)
        db.noticeDao().insertNotices(InitialSeedData.notices)
        InitialSeedData.auditLogs.forEach { db.auditLogDao().insertLog(it) }
      }
    }
  }
}
