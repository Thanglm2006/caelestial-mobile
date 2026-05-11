package com.example.blur_o_matic.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.work.WorkInfo
import coil.compose.AsyncImage
import com.example.blur_o_matic.R // Nhập đúng R của project bạn

@Composable
fun BlurScreen(viewModel: BlurViewModel) {
    val workInfos by viewModel.outputWorkInfo.collectAsState(initial = emptyList())
    val workInfo = workInfos.firstOrNull()

    // Lấy URI ảnh sau khi làm mờ xong (nếu có)
    val outputUri = workInfo?.outputData?.getString("KEY_IMAGE_URI")

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Blur-O-Matic", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // --- KHU VỰC HIỂN THỊ ẢNH ---
        Box(
            modifier = Modifier.size(300.dp).padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            if (outputUri != null && workInfo?.state == WorkInfo.State.SUCCEEDED) {
                // HIỂN THỊ ẢNH SAU KHI BLUR
                AsyncImage(
                    model = outputUri,
                    contentDescription = "Ảnh đã làm mờ",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                // HIỂN THỊ ẢNH GỐC (Lấy từ drawable của bạn)
                AsyncImage(
                    model = R.drawable.android_cupcake, // Đảm bảo bạn đã dán ảnh vào drawable
                    contentDescription = "Ảnh gốc",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // Hiển thị vòng xoay đè lên ảnh khi đang xử lý
            if (workInfo?.state == WorkInfo.State.RUNNING) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- TRẠNG THÁI ---
        Text(
            text = when (workInfo?.state) {
                WorkInfo.State.RUNNING -> "Đang xử lý làm mờ..."
                WorkInfo.State.SUCCEEDED -> "Hoàn tất! ✨"
                WorkInfo.State.FAILED -> "Thất bại rồi ❌"
                else -> "Nhấn Bắt đầu để làm mờ ảnh"
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(
                onClick = { viewModel.applyBlur() },
                enabled = workInfo?.state != WorkInfo.State.RUNNING
            ) {
                Text("BẮT ĐẦU")
            }

            if (workInfo?.state == WorkInfo.State.RUNNING) {
                OutlinedButton(onClick = { viewModel.cancelWork() }) {
                    Text("HỦY")
                }
            }
        }
    }
}