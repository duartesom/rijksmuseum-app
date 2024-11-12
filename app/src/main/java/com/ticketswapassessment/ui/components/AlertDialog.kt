package com.ticketswapassessment.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun AppAlertDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    icon: Painter,
    title: String,
    message : String
) {
    if (showDialog) {
        AlertDialog(
            icon = { Icon(icon, contentDescription = "Error icon", Modifier.size(60.dp)) },
            title = { Text(text = title) },
            text = { Text(text = message) },
            onDismissRequest = { onDismissRequest() },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text("Confirm")
                }
            }
        )
    }
}