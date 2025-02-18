package com.example.drinkinggameapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.drinkinggameapp.Database.QuestionDatabase
import com.example.drinkinggameapp.Models.SpinWheelItem
import com.example.drinkinggameapp.ViewModels.QuestionViewModelFactory
import com.example.drinkinggameapp.ViewModels.QuestionsViewModel
import com.example.drinkinggameapp.Views.MainScreen
import com.example.drinkinggameapp.Views.SpinWheelState
import com.example.drinkinggameapp.Views.WheelSpinCompleteFile
import com.example.drinkinggameapp.ui.theme.DrinkingGameAppTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import androidx.compose.runtime.remember as remember1

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DrinkingGameAppTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                ) {
                    //MainScreen()
                    //StartScreen()
                    //SpinWheelScreen()
                    //AppNavigation()
                    MyApp(context = this@MainActivity)
                }
            }
        }
    }

    @Composable
    fun MyApp(context: Context) {
        val db = QuestionDatabase.getDatabase(context)

        val viewModel: QuestionsViewModel = viewModel(
            factory = QuestionViewModelFactory(db.questionsDao())
        )

        //MainScreen(viewModel)
        MainScreen()
    }
}

@Preview
@Composable
fun SpinWheelScreen() {
    val items = remember1 {
        persistentListOf(
            SpinWheelItem(
                colors = persistentListOf(Color.Red, Color.Red),
                content = { Text("20$") }
            ),
            SpinWheelItem(
                colors = persistentListOf(Color.Green, Color.Green),
                content = { Text("40$") }
            ),
            SpinWheelItem(
                colors = persistentListOf(Color.Magenta, Color.Magenta),
                content = { Text("60$") }
            ),
            SpinWheelItem(
                colors = persistentListOf(Color.Gray, Color.Gray),
                content = { Text("80$") }
            ),
            SpinWheelItem(
                colors = persistentListOf(Color.Blue, Color.Blue),
                content = { Text("100$") }
            ),
            SpinWheelItem(
                colors = persistentListOf(Color.White, Color.White),
                content = { Text("200$") }
            ),
        )
    }

    //Dein SpinWheelSate mit Beispielbildern und Werten
    val spinWheelState = rememberSpinWheelState(
        items = items,
        backgroundImage = R.drawable.ic_launcher_background,
        centerImage = R.drawable.kreis,
        indicatorImage = R.drawable.blitz,
        stopDuration = 3.seconds,
        stopNbTurn = 3f,
    )

    // UI für das Spinwheel und einen Button zum Starten des Spins
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        WheelSpinCompleteFile().SpinWheelComponent(spinWheelState)

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DrinkingGameAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            //MainScreen()
        }
    }
}

@Composable
fun rememberSpinWheelState(
    items: PersistentList<com.example.drinkinggameapp.Models.SpinWheelItem>,
    @DrawableRes backgroundImage: Int,
    @DrawableRes centerImage: Int,
    @DrawableRes indicatorImage: Int,
    onSpinningFinished: (() -> Unit)? = null,
    initSpinWheelSection: Int? = 0,
    stopDuration: Duration = 8.seconds,
    stopNbTurn: Float = 3f,
    rotationPerSecond: Float = 0.8f,
    scope: CoroutineScope = rememberCoroutineScope(),
): SpinWheelState {
    return remember {
        SpinWheelState(
            items = items,
            backgroundImage = backgroundImage,
            centerImage = centerImage,
            indicatorImage = indicatorImage,
            initSpinWheelSection = initSpinWheelSection,
            stopDuration = stopDuration,
            stopNbTurn = stopNbTurn,
            rotationPerSecond = rotationPerSecond,
            scope = scope,
            onSpinningFinished = onSpinningFinished
        )
    }
}