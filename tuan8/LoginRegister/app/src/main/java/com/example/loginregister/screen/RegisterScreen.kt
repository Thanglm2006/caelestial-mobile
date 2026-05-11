package com.example.loginregister.screen

import androidx.compose.runtime.*
import androidx.compose.material3.*
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun RegisterScreen(onRegisterSuccess: () -> Unit, goToLogin: () -> Unit) {

    val auth = FirebaseAuth.getInstance()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column {
        Text("Register")

        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") })

        Button(onClick = {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        message = "Đăng ký thành công"
                        onRegisterSuccess()
                    } else {
                        message = "Lỗi: ${it.exception?.message}"
                    }
                }
        }) {
            Text("Register")
        }

        Button(onClick = { goToLogin() }) {
            Text("Back to Login")
        }

        Text(message)
    }
}