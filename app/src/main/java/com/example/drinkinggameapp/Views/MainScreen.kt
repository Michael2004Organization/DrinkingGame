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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.drinkinggameapp.R
import com.example.drinkinggameapp.SpinWheelScreen
import com.example.drinkinggameapp.ViewModels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
//@Preview
@Composable
fun MainScreen(
    viewModel: MainViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navController = rememberNavController()

    val currentViewName = remember { mutableStateOf(value = "User Selection") }

    fun toggleDrawer() {
        scope.launch {
            if (drawerState.isClosed) {
                drawerState.open()

            } else {
                drawerState.close()
            }
        }
    }

    //Colors
    val backgroundColor = Color.Black
    val textColor = Color.White

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        //Top-Header
        GameNavbar()

        Scaffold(
            topBar = {
                Surface(
                    shadowElevation = 8.dp, // Fügt Schatten hinzu
                    tonalElevation = 4.dp,  // Nutzt Material3 Farbhierarchie
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ScreenMenuItem(scope, drawerState, currentViewName)
                }
            },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                CompositionLocalProvider(
                    value = LocalLayoutDirection provides LayoutDirection.Ltr
                ) {
                    ModalNavigationDrawer(
                        modifier = Modifier.background(Color.White),
                        drawerState = drawerState,
                        gesturesEnabled = true,
                        drawerContent = {
                            ModalDrawerSheet(
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .background(color = backgroundColor)
                                        .padding(horizontal = 16.dp)
                                        .verticalScroll(rememberScrollState())
                                ) {
                                    Spacer(Modifier.height(12.dp))

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
//                                        IconButton(onClick = {
//                                            toggleDrawer()
//                                        }) {
//                                            Icon(
//                                                Icons.Filled.Menu,
//                                                contentDescription = null,
//                                                //tint = Color.White
//                                            )
//                                        }

                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            text = "Einstellungen",
                                            textAlign = TextAlign.Start,
                                            color = textColor,
                                            style = MaterialTheme.typography.titleLarge,
                                        )
                                    }

                                    HorizontalDivider()

                                    val navigationItems = listOf(
                                        listOf("Start Screen", "mainScreen"),
                                        listOf("Spielerauswahl", "userSelection"),
                                        listOf("Card Game", "cardMain"),
                                        listOf("Spin the Wheel Game", "spinWheelScreen")
                                    )

                                    for (items in navigationItems) {
                                        CustomNavigationDrawerItem(
                                            navController = navController,
                                            toggleDrawer = {
                                                toggleDrawer()
                                            },
                                            textColor = textColor,
                                            viewText = items[0],
                                            navViewName = items[1]
                                        )
                                    }
                                    HorizontalDivider()
                                }
                            }
                        },
                    ) {
                        CompositionLocalProvider(
                            value = LocalLayoutDirection provides LayoutDirection.Ltr
                        ) {
                            NavHost(
                                navController = navController,
                                startDestination = "userSelection",
                            ) {
                                composable("mainScreen") {
                                    currentViewName.value = "Main Screen"
                                    StartScreen()
                                }

                                composable("userSelection") { backStackEntry ->
                                    val test =
                                        backStackEntry.arguments?.getString("playerid")
                                            ?.toIntOrNull()

                                    currentViewName.value = "User Selection"
                                    UserSelection(viewModel, navController)
                                }

                                composable("cardMain") {
                                    currentViewName.value = "Card Game"
                                    CardMain(viewModel, navController)
                                }

                                composable("spinWheelScreen") {
                                    currentViewName.value = "Wheel of Destiny"
                                    SpinWheelScreen()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomNavigationDrawerItem(
    navController: NavController,
    toggleDrawer: () -> Unit,
    textColor: Color,
    viewText: String,
    navViewName: String
) {
    NavigationDrawerItem(
        modifier = Modifier
            .background(color = Color.Black)
            .shadow(elevation = 8.dp),
        label = {
            Text(
                text = viewText,
                color = textColor
            )
        },
        selected = false,
        onClick = {
            toggleDrawer()
            navController.navigate(route = navViewName)
        }
    )
}

@Composable
fun StartScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Start Screen",
            color = Color.White
        )
    }
}

@Composable
fun ScreenMenuItem(
    scope: CoroutineScope, drawerState: DrawerState,
    currentViewName: MutableState<String>
) {
    val backgroundColor: Color = colorResource(id = R.color.appBlack)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = backgroundColor),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            modifier = Modifier
                .padding(6.dp),
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
                tint = Color.White,
                contentDescription = "Menu Item",
            )
        }

        Text(
            text = currentViewName.value,
            fontSize = 20.sp,
            color = Color.White
        )
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