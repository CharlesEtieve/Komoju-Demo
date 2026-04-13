package com.komoju.demo.presentation.helpers

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

class Base64ToBitmapHelper {
    fun invoke(base64: String): Bitmap? {
        return try {
            val bytes = Base64.decode(base64, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        } catch (e: Exception) {
            null
        }
    }
}
