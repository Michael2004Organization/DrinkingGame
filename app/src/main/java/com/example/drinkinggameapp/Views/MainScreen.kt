package com.example.drinkinggameapp.Views

import android.annotation.SuppressLint
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.drinkinggameapp.R
import com.example.drinkinggameapp.SpinWheelScreen
import com.example.drinkinggameapp.ViewModels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
//@Preview
@Composable
fun MainScreen(
    viewModel: MainViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navController = rememberNavController()

    fun toggleDrawer() {
        scope.launch {
            if (drawerState.isClosed) {
                drawerState.open()

            } else {
                drawerState.close()
            }
        }
    }

    // Header
    GameNavbar()

    CompositionLocalProvider(
        value = LocalLayoutDirection provides LayoutDirection.Rtl
    ) {
        ModalNavigationDrawer(
            modifier = Modifier.background(Color.White),
            drawerState = drawerState,
            gesturesEnabled = true,
            drawerContent = {
                ModalDrawerSheet {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(horizontal = 16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Spacer(Modifier.height(12.dp))

                        Row {
                            IconButton(onClick = {
                                toggleDrawer()
                            }) {
                                Icon(
                                    Icons.Filled.Menu,
                                    contentDescription = null,
                                    //tint = Color.White
                                )
                            }

                            Text(
                                "Einstellungen",
                                modifier = Modifier.padding(top = 9.dp, start = 120.dp),
                                style = MaterialTheme.typography.titleLarge,
                            )
                        }

                        HorizontalDivider()

                        NavigationDrawerItem(label = { Text("Start Screen") },
                            selected = false,
                            onClick = {
                                toggleDrawer()
                                navController.navigate("mainScreen")
                            })
                        NavigationDrawerItem(label = { Text("Spielerauswahl") },
                            selected = false,
                            onClick = {
                                toggleDrawer()
                                navController.navigate("userSelection")
                            })
                        NavigationDrawerItem(label = { Text("Card Game") },
                            selected = false,
                            onClick = {
                                toggleDrawer()
                                navController.navigate("cardGame")
                            })
                        NavigationDrawerItem(label = { Text("Spin the Wheel Game") },
                            selected = false,
                            onClick = {
                                toggleDrawer()
                                navController.navigate("spinWheelScreen")
                            })
                        HorizontalDivider()
                    }
                }
            },
        ) {
            CompositionLocalProvider(
                value = LocalLayoutDirection provides LayoutDirection.Ltr
            ) {
                //Screen Inhalt
                ScreenMenuItem(scope, drawerState)

                NavHost(
                    navController = navController,
                    startDestination = "userSelection"
                ) {
                    composable("mainScreen") {
                        StartScreen()
                    }

                    composable("userSelection") { backStackEntry ->
                        val test = backStackEntry.arguments?.getString("playerid")?.toIntOrNull()
                        UserSelection(viewModel, navController)
                        //UserSelection(viewModel)
                    }

                    composable("cardGame") {
                        CardGenerator(viewModel)
                    }

                    composable("spinWheelScreen") {
                        SpinWheelScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun StartScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Start Screen"
        )
    }
}

@Composable
fun ScreenMenuItem(
    scope: CoroutineScope, drawerState: DrawerState
) {
    val backgroundColor: Color = colorResource(id = R.color.orange)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color = backgroundColor),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            },
        ) {
            Icon(
                Icons.Filled.Menu,
                tint = Color.Black,
                contentDescription = "Test",
            )
        }
    }
}

@Composable
fun GameNavbar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.Black, RectangleShape),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "DrinkingGame",
            color = Color.White,
            textAlign = TextAlign.Center,
        )
    }
}