package com.example.exam4.util

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

fun Bitmap.toByteArrayConverter(): ByteArray {
    ByteArrayOutputStream().apply {
        compress(Bitmap.CompressFormat.JPEG, 90, this)
        return toByteArray()
    }
}