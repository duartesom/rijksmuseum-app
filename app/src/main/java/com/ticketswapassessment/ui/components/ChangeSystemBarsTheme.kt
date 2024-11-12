package com.ticketswapassessment.ui.components

import android.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun ChangeSystemBarsTheme(lightTheme: Boolean, context: ComponentActivity) {
    val barColor = Color.TRANSPARENT
    LaunchedEffect(lightTheme) {
        if (lightTheme) {
            context.enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(
                    barColor, barColor,
                ),
                navigationBarStyle = SystemBarStyle.light(
                    barColor, barColor,
                ),
            )
        } else {
            context.enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.dark(
                    barColor,
                ),
                navigationBarStyle = SystemBarStyle.dark(
                    barColor,
                ),
            )
        }
    }
}