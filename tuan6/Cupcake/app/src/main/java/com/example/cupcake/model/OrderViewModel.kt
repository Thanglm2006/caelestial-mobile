package com.example.cupcake.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class OrderViewModel : ViewModel() {

    var quantity = mutableStateOf(0)
    var flavor = mutableStateOf("")
    var date = mutableStateOf("")
    var price = mutableStateOf(0.0)

    fun setQuantity(qty: Int) {
        quantity.value = qty
        price.value = qty * 2.0
    }

    fun setFlavor(f: String) {
        flavor.value = f
    }

    fun setDate(d: String) {
        date.value = d
    }

    fun reset() {
        quantity.value = 0
        flavor.value = ""
        date.value = ""
        price.value = 0.0
    }
}