package com.example.testassessment.roomdb

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(
    entities = [PhotosEntity::class],
    version = 1
)
abstract class LocalDb : RoomDatabase() {

    abstract val dao: RoomDao

    companion object {


        @Volatile
        private var INSTANCE: LocalDb? = null

        fun getDatabase(context: Context): LocalDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDb::class.java,
                    "local_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}