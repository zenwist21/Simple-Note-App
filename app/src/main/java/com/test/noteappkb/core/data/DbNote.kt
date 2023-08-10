package com.test.noteappkb.core.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [NoteEntities::class], version = 1, exportSchema = true
)

abstract class DbNote : RoomDatabase() {
    abstract fun dbDao() : DbDao

    companion object {
        private var INSTANCE: DbNote? = null

        fun getInstance(context: Context): DbNote {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    DbNote::class.java, "notes_database"
                ).build()
            }
            return INSTANCE!!
        }
    }
}