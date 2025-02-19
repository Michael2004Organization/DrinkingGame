package com.example.drinkinggameapp.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drinkinggameapp.R
import com.example.drinkinggameapp.ViewModels.MainViewModel

//@Preview
@Composable
fun UserSelection(
    viewModel: MainViewModel
) {
    Scaffold(
        modifier = Modifier
            .padding(top = 50.dp)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .background(color = colorResource(id = R.color.darkBlue)),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Spielerauswahl",
                    color = Color.White,
                    fontSize = 30.sp
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        //.clip(shape = RoundedCornerShape(20.dp))
                        .padding(start = 20.dp, end = 20.dp, top = 50.dp, bottom = 20.dp)
                        .background(Color.White, shape = RoundedCornerShape(15.dp))
                        .height(600.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color.Black,
                                shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                            )
                            .height(30.dp), verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 5.dp),
                            text = "Spieler hinzufügen: ",
                            color = Color.White,
                        )
                    }
                    val playerList = remember { mutableStateOf(listOf<String>()) }

                    addPlayer(playerList, viewModel)

                    val currentPlayersList by viewModel.playersList
                        .collectAsState(initial = emptyList())
                    LazyColumn {
                        items(currentPlayersList) { player ->
                            addedPlayer(player.playerName,
                                onClick = {
                                    viewModel.deletePlayer(player.id, player.playerName)
                                })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun addPlayer(
    playerList: MutableState<List<String>>,
    viewModel: MainViewModel
) {
    var playerName by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.15f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Rounded.Person,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                //.padding(start = 5.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.7f),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
        }
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.15f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = {
                    if (playerName.isNotEmpty()) {
                        viewModel.addPlayer(playerName)

                        playerList.value = playerList.value + playerName
                        playerName = ""
                    } else {
                        var playerAmount = playerList.value.size + 1

                        playerList.value =
                            playerList.value + "Spieler ${playerAmount}"
                        playerName = ""
                    }
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
        }
    }
}

@Composable
fun addedPlayer(
    player: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.15f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Rounded.Person,
                contentDescription = "Player Icon",
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.70f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = player,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 25.sp
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.15f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
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