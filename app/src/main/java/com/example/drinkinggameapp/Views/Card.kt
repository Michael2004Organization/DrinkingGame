package com.example.drinkinggameapp.Views

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.drinkinggameapp.R
import com.example.drinkinggameapp.ViewModels.MainViewModel
import com.example.drinkinggameapp.ViewModels.QuestionsViewModel
import com.example.drinkinggameapp.ui.theme.DrinkingGameAppTheme
import kotlinx.coroutines.delay

@Composable
fun height(): Int {
    val configuration = LocalConfiguration.current
    return configuration.screenHeightDp
}

@Composable
fun width(): Int {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp
}

//@Preview
@Composable
fun CardMain(
    viewModel: MainViewModel
) {
    val timer: Long = 1000
    TimedLayout(timer)

    var showCard by remember { mutableStateOf(value = false) }
    LaunchedEffect(key1 = Unit) {
        delay(timer)
        showCard = true
    }

    if (showCard) {
        CardGenerator(viewModel = viewModel)
    }
}

//@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DrinkingGameAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            //MainScreen()
            //TruthOrDare()
        }
    }
}

@Composable
fun PopupDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    Dialog(
        onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "This is a dialog with buttons and an image.",
                    modifier = Modifier.padding(16.dp),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Dismiss")
                    }
                    TextButton(
                        onClick = { onConfirmation() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}

@Composable
fun TimedLayout(
    timer: Long
) {
    var show by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = Unit) {
        delay(timer)
        show = false
    }

    if (show) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.orange))
        ) { padding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (show) {
                    Text(
                        text = "Truth\n or\n Dare",
                        textAlign = TextAlign.Center,
                        fontSize = 50.sp
                    )
                }
            }
        }
    }
}

//@Preview
@Composable
fun CardGenerator(
    viewModel: MainViewModel
) {
    val randomPlayer by viewModel.randomPlayer.collectAsState()
    val question by viewModel.questionsViewModel.currentQuestion.collectAsState()
    var rotated by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getRandomPlayer2()
    }

    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(500)
    )

    fun toggleFlip() {
        rotated = !rotated
    }

    //Colors, usw.
    val backgroundColor: Color = colorResource(R.color.black)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor),
        bottomBar = { BottomRow() }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Red)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = randomPlayer?.playerName ?: "No Player left",
                    color = Color.White,
                    fontSize = 35.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            ElevatedCard(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
                    .graphicsLayer {
                        rotationY = rotation
                        cameraDistance = 8 * density
                    }
                    .clickable {
                        rotated = !rotated
                    },

                elevation = CardDefaults.cardElevation(
                    defaultElevation = 50.dp
                ),
            ) {
                var questionText: String = "testQ"
                questionText = question?.question ?: "No Questions available!"

                var answersText: String = "testA"
                answersText = question?.answer ?: "No Questions available!"

                if (!rotated) {
                    //QuestionCard(viewModel, questionText)
                    GenerallCard(
                        viewModel.questionsViewModel,
                        "Frage:",
                        questionText,
                        { toggleFlip() })
                    //GenerallCard("Frage:", questionText)
                } else {
                    //AnswerCard(viewModel, rotation, answersText)
                    GenerallCard(
                        viewModel.questionsViewModel,
                        "Antwort:",
                        answersText,
                        { toggleFlip() })
                    //GenerallCard("Antwort:", answersText)
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun BottomRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Green)
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {}
        ) {
            Text(
                text = "Wahrheit"
            )
        }
        Button(
            onClick = {}
        ) {
            Text(
                text = "Pflicht"
            )
        }
    }
}

//@Preview
@Composable
fun GenerallCard(
    viewModel: QuestionsViewModel,
    cardHeaderText: String,
    cardBodyText: String,
    onClick: () -> Unit
) {
    val isQuestionCard = cardHeaderText.contains("Frage")
    val rotation = if (isQuestionCard) 0f else 180f
    val horizontalArrangement = if (isQuestionCard) Arrangement.Start else Arrangement.End

    //Colors
    val headerBackground = Color.Black
    val headerTextColor = Color.White

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        //Card Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(headerBackground),
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .graphicsLayer { rotationY = rotation }
                    .padding(start = 7.dp),
                text = cardHeaderText,
                color = headerTextColor,
                fontSize = 25.sp
            )
        }

        //Card TextBody
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(10.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier
                    .graphicsLayer { rotationY = rotation },
                text = cardBodyText,
                textAlign = TextAlign.Center
            )
        }

        //Card Foot
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Green)
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier
                    .weight(1f)
                    .graphicsLayer { rotationY = 180f },
                onClick = { viewModel.previousQuestion() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Previous"
                )
            }

            //Answer Btn
            if (isQuestionCard) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onClick() }
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Antwort anzeigen",
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                }
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }

            IconButton(
                modifier = Modifier
                    .weight(1f),
                onClick = { viewModel.nextQuestion() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Next"
                )
            }
        }
    }
}