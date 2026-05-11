package com.example.loginregister

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.google.firebase.auth.FirebaseAuth
import com.example.loginregister.screen.LoginScreen
import com.example.loginregister.screen.RegisterScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    var screen by remember { mutableStateOf("login") }

    if (screen == "login") {
        LoginScreen(
            onLoginSuccess = {},
            goToRegister = { screen = "register" }
        )
    } else {
        RegisterScreen(
            onRegisterSuccess = { screen = "login" },
            goToLogin = { screen = "login" }
        )
    }
}