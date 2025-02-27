package com.example.drinkinggameapp.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayersDao {
    @Query("Select * from players order by playerName ASC")
    fun getAllPlayers(): Flow<List<Players>>

    @Query("Select COUNT(*) from players")
    suspend fun getPlayersCount(): Int

    @Insert
    suspend fun addPlayer(players: Players)

    @Update
    suspend fun updatePlayer(players: Players)

    @Query("Select * from players where playerQuestioned = 0 order by Random() limit 1")
    suspend fun getRandomPlayer(): Players

    @Delete
    suspend fun deletePlayer(players: Players)
}

@Dao
interface QuestionsDao {
    //Truth
    @Query("Select * from questions where isDare = 0 order by id ASC")
    fun getAllTruthQuestions(): Flow<List<Questions>>

    //Dare
    @Query("Select * from questions where isDare = 1 order by id ASC")
    fun getAllDareCommands(): Flow<List<Questions>>

    //General
    @Query("Select COUNT(*) from questions where isDare = 0")
    fun getCountTruth(): Long

    @Query("Select COUNT(*) from questions where isDare = 1")
    fun getCountDare(): Long
}