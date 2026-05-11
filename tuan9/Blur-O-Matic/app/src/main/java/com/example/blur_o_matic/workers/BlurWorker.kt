package com.example.blur_o_matic.workers

import android.content.Context
import android.graphics.BitmapFactory
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class BlurWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        val appContext = applicationContext
        // 1. Lấy ảnh từ Resource (cupcake)
        val bitmap = BitmapFactory.decodeResource(
            appContext.resources,
            com.example.blur_o_matic.R.drawable.android_cupcake
        )

        return try {
            // 2. Làm mờ ảnh
            val blurredBitmap = blurBitmap(bitmap, appContext)

            // 3. Lưu ảnh mờ thành file và lấy URI
            val outputUri = writeBitmapToFile(appContext, blurredBitmap)

            // 4. Trả về URI của file đã mờ cho UI
            val outputData = workDataOf("KEY_IMAGE_URI" to outputUri.toString())
            Result.success(outputData)
        } catch (throwable: Throwable) {
            Result.failure()
        }
    }
}