package com.example.drinkinggameapp.Views

import android.view.RoundedCorner
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.drinkinggameapp.ViewModels.MainViewModel
import com.example.drinkinggameapp.ViewModels.QuestionsViewModel

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
fun CardGenerator(
    viewModel: MainViewModel
) {
    //viewModel.getQuestion()

    val question by viewModel.questionsViewModel.currentQuestion.collectAsState()

    var rotated by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(500)
    )

    fun toggleFlip() {
        rotated = !rotated
    }

    Scaffold(
        modifier = Modifier
            .padding(top = 50.dp)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ElevatedCard(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 100.dp)
                    .graphicsLayer {
                        rotationY = rotation
                        cameraDistance = 8 * density
                    }
                    .clickable {
                        rotated = !rotated
                    },
                //.size(width = width().dp),

                elevation = CardDefaults.cardElevation(
                    defaultElevation = 12.dp
                ),
            ) {
                val questionText: String
                questionText = question?.question ?: "No Questions available!"

                val answersText: String
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
    val rotation: Float
    val horizontalArrangement: Arrangement.Horizontal

    if (cardHeaderText.contains("Frage")) {
        rotation = 0f
        horizontalArrangement = Arrangement.Start
    } else {
        rotation = 180f
        horizontalArrangement = Arrangement.End
    }

    Column(
        modifier = Modifier
    ) {
        //Card Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black),
            horizontalArrangement = horizontalArrangement
        ) {
            Text(
                modifier = Modifier
                    .graphicsLayer {
                        rotationY = rotation
                    }
                    .padding(start = 7.dp),
                text = cardHeaderText,
                color = Color.White,
            )
        }

        //Card TextBody
        Row(
            Modifier
                .background(Color.White)
                .fillMaxWidth(),
            horizontalArrangement = horizontalArrangement
        ) {
            Text(
                modifier = Modifier
                    .graphicsLayer {
                        rotationY = rotation
                    }
                    .padding(start = 7.dp, top = 5.dp, bottom = 5.dp, end = 7.dp),
                text = cardBodyText
            )
        }

        //Card Foot
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.White),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    modifier = Modifier
                        .graphicsLayer {
                            rotationY = 180f
                        },
                    onClick = {
                        viewModel.previousQuestion()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Arrow Left"
                    )
                }
            }

            //Answer Btn
            if (cardHeaderText.contains("Frage")) {
                Row(
                    modifier = Modifier
                        .weight(0.5f),
                ) {
                    Text(
                        modifier = Modifier
                            .clickable { onClick() }
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        text = "Antwort anzeigen",
                        textAlign = TextAlign.Center
                    )
                }
            }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.White),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    modifier = Modifier,
                    onClick = {
                        viewModel.nextQuestion()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Arrow Right"
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileCard() {

}