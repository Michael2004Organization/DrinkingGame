package com.example.drinkinggameapp.ViewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.drinkinggameapp.Database.AppDatabase
import com.example.drinkinggameapp.Database.Players
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PlayersViewModel(
    //private val dao: PlayersDao,
    application: Application
) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).playersDao()

    val playersList: StateFlow<List<Players>> = dao.getAllPlayers()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        viewModelScope.launch {
            Log.d("RoomTest", "Start")

            val test = dao.getAllPlayers()
            Log.d("RoomTest", "Spieler in DB: $test")

            Log.d("RoomTest", "End")
        }
    }

    fun addPlayer(player: String) {
        viewModelScope.launch {
            dao.addPlayer(Players(playerName = player))
        }
    }
}