package com.example.drinkinggameapp.Views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "screen1"
    ) {
        composable("screen1") { Screen1(navController) }
        composable("screen2") { Screen2(navController) }
    }
}

@Composable
fun Screen1(navController: NavController) {
    ScreenTemplate(
        title = "Screen 1",
        buttonText = "Go to Screen 2",
        onClick = {
            navController.navigate("screen2")
        }
    )
}

@Composable
fun Screen2(navController: NavController) {
    ScreenTemplate(
        title = "Screen 2",
        buttonText = "Go to Screen 1",
        onClick = {
            navController.navigate("screen1")
        }
    )
}

@Composable
fun ScreenTemplate(
    title: String,
    buttonText: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = title, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onClick) {
            Text(text = buttonText)
        }
    }
}