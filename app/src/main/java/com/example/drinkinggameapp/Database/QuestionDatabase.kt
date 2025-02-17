package com.example.drinkinggameapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Questions::class],
    version = 1,
    exportSchema = false
)
abstract class QuestionDatabase : RoomDatabase() {
    abstract fun questionsDao(): QuestionsDao

    companion object {
        @Volatile
        private var INSTANCE: QuestionDatabase? = null

        fun getDatabase(context: Context): QuestionDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuestionDatabase::class.java,
                    "question_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}