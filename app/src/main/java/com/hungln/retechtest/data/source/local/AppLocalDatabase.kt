package com.hungln.retechtest.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hungln.retechtest.data.network.response.OrganizationItem
import com.hungln.retechtest.data.network.response.Owner
import com.hungln.retechtest.data.network.response.RepositoryItem
import com.hungln.retechtest.data.network.response.User
import com.hungln.retechtest.util.AppConstants
import com.hungln.retechtest.util.DatabaseConverters

@Database(
    entities = [User::class, RepositoryItem::class, OrganizationItem::class, Owner::class],
    version = 5,
    exportSchema = false
)
@TypeConverters(DatabaseConverters::class)
abstract class AppLocalDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun userRepositoryDao(): UserRepositoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppLocalDatabase? = null

        fun getDatabase(context: Context): AppLocalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppLocalDatabase::class.java,
                    AppConstants.APP_DATABASE
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}