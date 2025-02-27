package com.example.drinkinggameapp.ViewModels

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.drinkinggameapp.Database.AppDatabase
import com.example.drinkinggameapp.Database.Players
import com.example.drinkinggameapp.Database.Questions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
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

    val truthQuestions: StateFlow<List<Questions>> = dao.questionsDao().getAllTruthQuestions()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val dareCommands: StateFlow<List<Questions>> = dao.questionsDao().getAllDareCommands()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    //Players
    fun addPlayerWithCount() {
        viewModelScope.launch {
            val count = dao.playersDao().getPlayersCount() + 1
            val playerName = "Spieler $count"

            dao.playersDao().addPlayer(Players(playerName = playerName, playerQuestioned = false))
        }
    }

    fun addPlayer(name: String) {
        viewModelScope.launch {
            dao.playersDao().addPlayer(Players(playerName = name, playerQuestioned = false))
        }
    }

    fun getRandomPlayer(onResult: (Players) -> Unit) {
        viewModelScope.launch {
            val randomPlayer = dao.playersDao().getRandomPlayer()
            onResult(randomPlayer)
        }
    }

    private val _randomPlayer = MutableStateFlow<Players?>(null)
    val randomPlayer: StateFlow<Players?> get() = _randomPlayer
    fun getRandomPlayer2() {
        viewModelScope.launch {
            val player = dao.playersDao().getRandomPlayer()
            _randomPlayer.value = player
        }
    }

    fun deletePlayer(id: Int, name: String, playerQuestioned: Boolean) {
        viewModelScope.launch {
            dao.playersDao().deletePlayer(
                Players(
                    id = id,
                    playerName = name,
                    playerQuestioned = playerQuestioned
                )
            )
        }
    }

    fun updatePlayer(id: Int, name: String, playerQuestioned: Boolean) {
        viewModelScope.launch {
            dao.playersDao().updatePlayer(
                Players(id = id, playerName = name, playerQuestioned = playerQuestioned)
            )
        }
    }
}