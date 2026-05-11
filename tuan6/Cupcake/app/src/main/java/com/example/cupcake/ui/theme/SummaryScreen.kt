package com.example.cupcake.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cupcake.model.OrderViewModel
import java.text.NumberFormat

@Composable
fun SummaryScreen(
    orderViewModel: OrderViewModel,
    onCancel: () -> Unit
) {
    Column(Modifier.padding(16.dp)) {

        Text("Order Summary", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(16.dp))

        Text("Quantity: ${orderViewModel.quantity.value}")
        Text("Flavor: ${orderViewModel.flavor.value}")
        Text("Pickup: ${orderViewModel.date.value}")
        Text(
            "Total: ${
                NumberFormat.getCurrencyInstance()
                    .format(orderViewModel.price.value)
            }"
        )

        Spacer(Modifier.height(16.dp))

        Button(onClick = { }) {
            Text("Send Order")
        }

        TextButton(onClick = onCancel) {
            Text("Cancel")
        }
    }
}