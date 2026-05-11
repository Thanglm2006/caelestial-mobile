package com.example.blur_o_matic // Sửa lại cho đúng thư mục của bạn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.blur_o_matic.ui.theme.BlurScreen
import com.example.blur_o_matic.ui.theme.BlurViewModel
import com.example.blur_o_matic.ui.theme.BlurOMaticTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlurOMaticTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        val blurViewModel: BlurViewModel = viewModel()
                        BlurScreen(viewModel = blurViewModel)
                    }
                }
            }
        }
    }
}