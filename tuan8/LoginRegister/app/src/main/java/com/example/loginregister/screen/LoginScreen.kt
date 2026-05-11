package com.example.loginregister.screen

import androidx.compose.runtime.*
import androidx.compose.material3.*
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, goToRegister: () -> Unit) {

    val auth = FirebaseAuth.getInstance()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column {
        Text("Login")

        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") })

        Button(onClick = {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        message = "Đăng nhập thành công"
                        onLoginSuccess()
                    } else {
                        message = "Lỗi: ${it.exception?.message}"
                    }
                }
        }) {
            Text("Login")
        }

        Button(onClick = { goToRegister() }) {
            Text("Go to Register")
        }

        Text(message)
    }
}