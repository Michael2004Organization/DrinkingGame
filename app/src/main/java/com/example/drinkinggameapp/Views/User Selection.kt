package com.example.drinkinggameapp.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.drinkinggameapp.Database.Players
import com.example.drinkinggameapp.R
import com.example.drinkinggameapp.ViewModels.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//@Preview
@Composable
fun UserSelection(
    viewModel: MainViewModel,
    navController: NavController
) {
    //Colors
    val backgroundColor: Color = colorResource(id = R.color.orange)
    val textBackground: Color = colorResource(id = R.color.green)
    val textColor: Color = Color.White

    //NavController
    val cardGame = "cardMain"

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(color = backgroundColor),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Spielerauswahl",
                    color = textColor,
                    fontSize = 30.sp
                )
            }
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        //.clip(shape = RoundedCornerShape(20.dp))
                        .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp)
                        .background(Color.White, shape = RoundedCornerShape(15.dp))
                        .height(600.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .background(
                                color = textBackground,
                                shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 5.dp),
                            text = "Spieler hinzufügen: ",
                            color = textColor,
                        )
                    }

                    player(
                        viewModel = viewModel,
                        player = "",
                        isUserAdd = true,
                        onClick = {})

                    HorizontalDivider(
                        modifier = Modifier
                            .padding(top = 20.dp, bottom = 0.dp),
                        thickness = 2.5.dp,
                        color = Color.Black
                    )

                    val currentPlayersList by viewModel.playersList
                        .collectAsState(initial = emptyList())
                    LazyColumn {
                        items(currentPlayersList) { player ->
                            player(
                                viewModel = viewModel,
                                player = player.playerName,
                                isUserAdd = false,
                                onClick = {
                                    viewModel.deletePlayer(
                                        player.id,
                                        player.playerName,
                                        player.playerQuestioned
                                    )
                                })
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 35.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    modifier = Modifier,
                    onClick = {

//                        val testP = viewModel.getRandomPlayer { player->
//
//                        }

                        navController.navigate("$cardGame")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = textBackground
                    )
                ) {
                    Text(
                        text = "Spiel starten",
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun player(
    viewModel: MainViewModel,
    player: String,
    isUserAdd: Boolean,
    onClick: () -> Unit
) {
    var playerName by remember { mutableStateOf("") }

    //Icons
    val personIcon: ImageVector = Icons.Default.Person

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        //First
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.15f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                personIcon,
                contentDescription = "Person Icon",
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        //Second
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.7f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (isUserAdd) {
                TextField(
                    modifier = Modifier
                        .fillMaxSize(),
                    value = playerName,
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center,
                    ),
                    onValueChange = {
                        if (it.length <= 13) {
                            playerName = it
                        }
                    },
                    placeholder = {
                        Text(
                            modifier = Modifier
                                .fillMaxSize(),
                            text = "Playername",
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                )
            } else {
                Text(
                    text = player,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 25.sp
                    )
                )
            }
        }
        //Third
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.15f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (isUserAdd) {
                IconButton(
                    onClick = {
                        if (playerName.isNotEmpty()) {
                            viewModel.addPlayer(playerName)

                        } else {
                            viewModel.addPlayerWithCount()
                        }

                        playerName = ""
                    },
                ) {
                    Icon(
                        Icons.Rounded.Add,
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(50.dp),
                    )
                }
            } else {
                IconButton(
                    onClick = { onClick() },
                ) {
                    Icon(
                        Icons.Rounded.Delete,
                        contentDescription = "Delete Player",
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}