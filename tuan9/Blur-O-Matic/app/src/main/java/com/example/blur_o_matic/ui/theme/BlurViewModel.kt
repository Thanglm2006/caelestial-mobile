package com.example.blur_o_matic.ui.theme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.work.*
import com.example.blur_o_matic.workers.BlurWorker
import kotlinx.coroutines.flow.Flow

class BlurViewModel(application: Application) : AndroidViewModel(application) {
    private val workManager = WorkManager.getInstance(application)

    // Flow để UI lắng nghe trạng thái của Worker
    val outputWorkInfo: Flow<List<WorkInfo>> =
        workManager.getWorkInfosByTagFlow("BLUR_TAG")

    fun applyBlur() {
        // Tạo yêu cầu làm mờ ảnh (One-time request)
        val blurRequest = OneTimeWorkRequestBuilder<BlurWorker>()
            .addTag("BLUR_TAG")
            .setInputData(workDataOf("KEY_IMAGE_URI" to "android.resource://com.example.bluromatic/drawable/android_cupcake"))
            .build()

        // Đưa vào hàng đợi xử lý duy nhất (Unique Work)
        workManager.enqueueUniqueWork(
            "IMAGE_BLUR_WORK",
            ExistingWorkPolicy.REPLACE,
            blurRequest
        )
    }

    fun cancelWork() {
        workManager.cancelUniqueWork("IMAGE_BLUR_WORK")
    }
}