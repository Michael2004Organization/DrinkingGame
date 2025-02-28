package com.example.drinkinggameapp.Views

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
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
    viewModel: MainViewModel,
    navController: NavController
) {
    val timer: Long = 100
    //TimedLayout(timer)

    var showCard by remember { mutableStateOf(value = false) }
    LaunchedEffect(key1 = Unit) {
        delay(timer)
        showCard = true
    }

    if (showCard) {
        CardGenerator(viewModel = viewModel, navController = navController)
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
            AddNewCard()
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
    viewModel: MainViewModel,
    navController: NavController
) {
    //States
    val randomPlayer by viewModel.randomPlayer.collectAsState()
    val truthQuestion by viewModel.questionsViewModel.currentTruthQuestion.collectAsState()
    val dareCommand by viewModel.questionsViewModel.currentDareCommand.collectAsState()

    //Remembers
    var rotated by remember { mutableStateOf(value = false) }
    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(500)
    )

    var text by remember { mutableStateOf(value = "Wahrheit oder Pflicht") }

    //Functions
    fun toggleFlip() {
        rotated = !rotated
    }

    //Values
    val backgroundColor: Color = Color.Black
    val roundedCornersDP = 25.dp

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            Spacer(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .background(color = backgroundColor)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    .graphicsLayer {
                        rotationY = rotation
                        cameraDistance = 8 * density
                    },
                shape = RoundedCornerShape(roundedCornersDP),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp,
                ),
            ) {
                var player = randomPlayer?.playerName ?: "No Player"
                if (player.contains("No Player")) {
                    viewModel.getRandomPlayer2()
                }

                if (!rotated) {
                    GenerallCard(
                        viewModel = viewModel.questionsViewModel,
                        navController = navController,
                        cardHeaderText = "Frage:",
                        cardBodyText = text,
                        randomPlayer = player,
                        onClick = { value ->
                            if (value.contains("Truth")) {
                                text = truthQuestion?.text ?: "Keine Fragen mehr verfügbar."
                            } else {
                                text = dareCommand?.text ?: "Keine Fragen mehr verfügbar."
                            }
                        },
                        onClickNext = {
                            viewModel.getRandomPlayer2()
                            text = "Wahrheit oder Pflicht"
                        })
                } else {
//                    GenerallCard(
//                        viewModel = viewModel.questionsViewModel,
//                        navController = navController,
//                        cardHeaderText = "Antwort:",
//                        cardBodyText = text,
//                        randomPlayer = randomPlayer?.playerName ?: "No Player left",
//                        onClick = { toggleFlip() })
                }
            }
        }
    }
}

//@Preview
@Composable
fun GenerallCard(
    viewModel: QuestionsViewModel,
    navController: NavController,
    cardHeaderText: String = "Frage: ",
    cardBodyText: String = "No Question available",
    randomPlayer: String = "",
    onClick: (String) -> Unit = {},
    onClickNext: () -> Unit = {}
) {
    var isAction by remember { mutableStateOf(value = false) }

    val isQuestionCard = cardHeaderText.contains("Frage")
    val rotation = if (isQuestionCard) 0f else 180f
    val horizontalArrangement = if (isQuestionCard) Arrangement.Start else Arrangement.End

    //Rounded corners
    val roundedCornersDP = 25.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.Black,
                shape = RoundedCornerShape(roundedCornersDP)
            )
    ) {
        //Body
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                //.padding(15.dp)
                .background(
                    color = colorResource(R.color.appBlack),
                    shape = RoundedCornerShape(roundedCornersDP)
                ),
            contentAlignment = Alignment.Center,
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.1f)
                        .padding(start = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        modifier = Modifier
                            .weight(1f),
                        onClick = {
                            navController.navigate(route = "userSelection")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier
                                .size(50.dp)
                                .border(1.dp, Color.White, shape = CircleShape)
                                .padding(5.dp)
                                .align(alignment = Alignment.CenterVertically)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1.5f))

                    IconButton(
                        modifier = Modifier
                            .weight(1f),
                        onClick = {
                            navController.navigate(route = "addNewCard")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier
                                .size(50.dp)
                                .border(1.dp, Color.White, shape = CircleShape)
                                .padding(5.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.8f)
                        .background(colorResource(R.color.appBlack)),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.Magenta)) {
                                append(randomPlayer)
                            }
                            withStyle(style = SpanStyle(color = Color.White)) {
                                append(", $cardBodyText")
                            }
                        },
                        modifier = Modifier
                            .graphicsLayer { rotationY = rotation }
                            .width(200.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 25.sp
                    )
                }

                if (!isAction) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, bottom = 5.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        val roundedCornersDP = 30.dp

                        //Wahrheit
                        Button(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 5.dp),
                            shape = RoundedCornerShape(roundedCornersDP),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black,
                                contentColor = Color.White
                            ),
                            onClick = {
                                viewModel.nextTruthQuestion()
                                isAction = true
                                onClick("Truth")
                            }
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                text = "Wahrheit",
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        //Pflicht
                        Button(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 5.dp),
                            shape = RoundedCornerShape(roundedCornersDP),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black,
                                contentColor = Color.White
                            ),
                            onClick = {
                                viewModel.nextDareCommand()
                                isAction = true
                                onClick("Dare")
                            }
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                text = "Pflicht",
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 10.dp, bottom = 5.dp),
                            //.height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Blue,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(25.dp),
                            onClick = {
                                isAction = false
                                onClickNext()
                            },
                        ) {
                            Text(
                                text = "Weiter",
                                color = Color.White,
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AddNewCard() {
    val backgroundColor = Color.Black
    val textColor = Color.White
    val iconColor = Color.White

    var newCardValue by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
                .background(color = backgroundColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(0.8f)
                        .padding(end = 25.dp),
                    text = "Neue Karte hinzufügen",
                    color = Color.White,
                    textAlign = TextAlign.End,
                    fontSize = 24.sp
                )

                IconButton(
                    modifier = Modifier
                        .weight(0.1f)
                        .padding(end = 5.dp),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier
                            .border(width = 1.dp, shape = CircleShape, color = iconColor)
                            .size(48.dp)
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier
                    .padding(10.dp),
                color = Color.White,
                thickness = 1.dp
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(start = 20.dp, end = 20.dp),
                value = newCardValue,
                textStyle = TextStyle(textAlign = TextAlign.Center),
                onValueChange = {
                    if(newCardValue.length <= 454){
                        newCardValue = it
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = backgroundColor,
                    unfocusedContainerColor = backgroundColor,
                    unfocusedPlaceholderColor = textColor,
                    focusedPlaceholderColor = textColor,
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    cursorColor = Color.Red
                ),
                placeholder = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "Neu Karte schreiben...",
                        color = textColor,
                        textAlign = TextAlign.Center
                    )
                },

            )
        }
    }
}