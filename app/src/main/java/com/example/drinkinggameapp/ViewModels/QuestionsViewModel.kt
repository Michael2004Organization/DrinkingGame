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
            dao.getAllQuestions().collect() { questions ->
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

    private val questions = dao.getAllQuestions().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    private var currentIndex = MutableStateFlow(0)

    val currentQuestion = combine(questions, currentIndex) { list, index ->
        list.getOrNull(index)
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

//    fun getQuestion(){
//        currentIndex.update { it }
//    }

    fun nextQuestion() {
        GlobalScope.launch(Dispatchers.Main) {
            val questionCount = withContext(Dispatchers.IO) {
                dao.getQuestionAmountInTable().toInt()

            }

            currentIndex.update { it + 1 }

            if (currentIndex.value >= questionCount) {
                currentIndex.update { it - (it + 1) }
            }
        }
    }

    fun previousQuestion() {
        GlobalScope.launch(Dispatchers.Main) {
            val questionCount = withContext(Dispatchers.IO) {
                dao.getQuestionAmountInTable().toInt()
            }

            currentIndex.update { it - 1 }

            if (currentIndex.value < 1) {
                currentIndex.update { it - (it + 1) }
            }
        }
    }
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