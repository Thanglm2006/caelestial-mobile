package com.example.cupcake.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PickupScreen(
    onNext: (String) -> Unit,
    onCancel: () -> Unit
) {
    val options = listOf("Today", "Tomorrow", "Next week")
    var selected by remember { mutableStateOf(options[0]) }

    Column(Modifier.padding(16.dp)) {

        Text("Pickup date", style = MaterialTheme.typography.headlineSmall)

        options.forEach {
            Row {
                RadioButton(
                    selected = (it == selected),
                    onClick = { selected = it }
                )
                Text(it)
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(onClick = { onNext(selected) }) {
            Text("Next")
        }

        TextButton(onClick = onCancel) {
            Text("Cancel")
        }
    }
}