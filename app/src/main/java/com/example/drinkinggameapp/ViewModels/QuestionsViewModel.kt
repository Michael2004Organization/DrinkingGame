package com.example.drinkinggameapp.ViewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.drinkinggameapp.Database.AppDatabase
import com.example.drinkinggameapp.Database.QuestionsEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuestionsViewModel(
    application: Application
) : ViewModel() {
    private val dao = AppDatabase.getDatabase(application).questionsDao()

    init {
        viewModelScope.launch {
            dao.getAllTruthQuestions().collect() { questions ->
                Log.d("RoomTest", "Question in DB: $questions")
            }
        }
    }

    fun onEvent(event: QuestionsEvent) {
        when (event) {
            is QuestionsEvent.NextQuestion -> {
                viewModelScope.launch {
                }
            }
        }
    }

    // Truth
    private val truthQuestions = dao.getAllTruthQuestions().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    private var currentTruthIndex = MutableStateFlow(value = 0)

    val currentTruthQuestion = combine(truthQuestions, currentTruthIndex) { list, index ->
        list.getOrNull(index)
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun nextTruthQuestion() {
        GlobalScope.launch(Dispatchers.Main) {
            val truthCount = withContext(Dispatchers.IO) {
                dao.getCountTruth().toInt()
            }

            currentTruthIndex.update { it + 1 }

            if (currentTruthIndex.value > (truthCount - 1)) {
                currentTruthIndex.update { it - it }
            }
        }
    }


    // Dare
    private val dareCommand = dao.getAllDareCommands().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    private var currentDareIndex = MutableStateFlow(value = 0)

    val currentDareCommand = combine(dareCommand, currentDareIndex) { list, index ->
        list.getOrNull(index)
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun nextDareCommand() {
        GlobalScope.launch(Dispatchers.Main) {
            val dareCount = withContext(Dispatchers.IO) {
                dao.getCountDare().toInt()
            }

            currentDareIndex.update { it + 1 }

            if (currentDareIndex.value > (dareCount - 1)) {
                currentDareIndex.update { it - it }
            }
        }
    }

//    fun previousQuestion() {
//        GlobalScope.launch(Dispatchers.Main) {
////            val questionCount = withContext(Dispatchers.IO) {
////                dao.getQuestionAmountInTable().toInt()
////            }
//
//            currentIndex.update { it - 1 }
//
//            if (currentIndex.value < 1) {
//                currentIndex.update { it - it }
//            }
//        }
//    }
}

class QuestionViewModelFactory(
    //private val dao: QuestionsDao
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuestionsViewModel(application = application) as T
        }
        throw IllegalArgumentException("Unkwon ViewModel class")
    }
}