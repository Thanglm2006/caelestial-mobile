package com.example.calculatetip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TipTimeApp()
                }
            }
        }
    }
}

@Composable
fun TipTimeApp() {
    var amountInput by remember { mutableStateOf("") }
    var tipInput by remember { mutableStateOf("") }
    var roundUp by remember { mutableStateOf(false) }

    val amount = amountInput.toDoubleOrNullSafe()
    val tipPercent = tipInput.toDoubleOrNullSafe()

    val tip = calculateTip(amount = amount, tipPercent = tipPercent, roundUp = roundUp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Calculate Tip", style = MaterialTheme.typography.titleLarge)

        NumberField(
            label = "Bill Amount",
            value = amountInput,
            onValueChange = { amountInput = it }
        )

        NumberField(
            label = "Tip Percentage",
            value = tipInput,
            onValueChange = { tipInput = it }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Round up tip?",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )
            Switch(
                checked = roundUp,
                onCheckedChange = { roundUp = it }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Tip Amount: $tip",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
private fun NumberField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            // Chỉ cho nhập số, dấu chấm, dấu phẩy (để tránh crash do ký tự lạ)
            val filtered = newValue.filter { it.isDigit() || it == '.' || it == ',' }
            onValueChange(filtered)
        },
        label = { Text(label) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
    )
}

private fun String.toDoubleOrNullSafe(): Double {
    // Cho phép người dùng nhập 10,5 hoặc 10.5
    return this.replace(',', '.').toDoubleOrNull() ?: 0.0
}

private fun calculateTip(amount: Double, tipPercent: Double, roundUp: Boolean): String {
    var tip = tipPercent / 100.0 * amount
    if (roundUp) tip = ceil(tip)
    return NumberFormat.getCurrencyInstance().format(tip)
}