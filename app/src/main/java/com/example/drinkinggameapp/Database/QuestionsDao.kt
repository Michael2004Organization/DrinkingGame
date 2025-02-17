package com.example.drinkinggameapp.Database

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionsDao {
    @Query("Select * from questions order by question ASC")
    fun getAllQuestions(): Flow<List<Questions>>

    @Query("Select COUNT(*) from questions")
    fun getQuestionAmountInTable(): Long
}