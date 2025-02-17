package com.example.drinkinggameapp.Database

sealed interface QuestionsEvent {
    object NextQuestion: QuestionsEvent
}