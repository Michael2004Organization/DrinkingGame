package com.example.drinkinggameapp.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Questions(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val text: String,
    val isDare: Boolean
)
