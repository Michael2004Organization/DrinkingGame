package com.example.drinkinggameapp.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class Players(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val playerName: String
)