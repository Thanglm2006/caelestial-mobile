package com.example.cupcake.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StartScreen(onNext: (Int) -> Unit) {
    Column(Modifier.padding(16.dp)) {

        Text("Order Cupcakes", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(16.dp))

        listOf(1, 6, 12).forEach {
            Button(
                onClick = { onNext(it) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("$it cupcakes")
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}