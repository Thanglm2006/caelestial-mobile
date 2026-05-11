package com.example.blur_o_matic.workers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import java.io.File
import java.io.FileOutputStream
import java.util.*

fun blurBitmap(bitmap: Bitmap, context: Context): Bitmap {
    val outBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config ?: Bitmap.Config.ARGB_8888)
    val rs = RenderScript.create(context)
    val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
    val allIn = Allocation.createFromBitmap(rs, bitmap)
    val allOut = Allocation.createFromBitmap(rs, outBitmap)

    blurScript.setRadius(25f) // Độ mờ (1f - 25f)
    blurScript.setInput(allIn)
    blurScript.forEach(allOut)
    allOut.copyTo(outBitmap)
    rs.destroy()
    return outBitmap
}

fun writeBitmapToFile(context: Context, bitmap: Bitmap): Uri {
    val name = "blur-filter-output-${UUID.randomUUID()}.png"
    val outputDir = File(context.cacheDir, "blur_outputs")
    if (!outputDir.exists()) outputDir.mkdirs()
    val outputFile = File(outputDir, name)
    var out: FileOutputStream? = null
    try {
        out = FileOutputStream(outputFile)
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, out)
    } finally {
        out?.close()
    }
    return Uri.fromFile(outputFile)
}