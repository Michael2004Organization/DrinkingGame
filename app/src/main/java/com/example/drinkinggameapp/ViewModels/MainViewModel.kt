package com.example.drinkinggameapp.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.drinkinggameapp.Database.AppDatabase
import com.example.drinkinggameapp.Database.Players
import com.example.drinkinggameapp.Database.Questions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application)

    val playersViewModel = PlayersViewModel(application)
    val questionsViewModel = QuestionsViewModel(application)

    val playersList: Flow<List<Players>> = dao.playersDao().getAllPlayers()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val questionsList: StateFlow<List<Questions>> = dao.questionsDao().getAllQuestions()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    //Questions


    //Players
    fun addPlayerWithCount() {
        viewModelScope.launch {
            val count = dao.playersDao().getPlayersCount() + 1
            val playerName = "Spieler $count"

            dao.playersDao().addPlayer(Players(playerName = playerName))
        }
    }

    fun addPlayer(name: String) {
        viewModelScope.launch {
            dao.playersDao().addPlayer(Players(playerName = name))
        }
    }

    fun deletePlayer(id: Int, name: String) {
        viewModelScope.launch {
            dao.playersDao().deletePlayer(Players(id = id, playerName = name))
        }
    }
}