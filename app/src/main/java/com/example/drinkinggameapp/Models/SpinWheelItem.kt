package com.example.drinkinggameapp.Models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.PersistentList

@Stable
data class SpinWheelItem(
    val colors: PersistentList<androidx.compose.ui.graphics.Color>,
    val content: @Composable () -> Unit,
)

internal fun List<SpinWheelItem>.getDegreesPerItem(): Float = 360f / this.size.toFloat()