package com.example.ktgbook.util

import android.util.Log
import com.example.ktgbook.util.Constants.Companion.ERROR_PREFIX
import com.example.ktgbook.util.Constants.Companion.TAG

object DebugLogger {

    fun logError(throwable: Throwable) {
        Log.d(TAG, ERROR_PREFIX + throwable.localizedMessage)
    }

    fun logDebug(message: String) {
        Log.d(TAG, message)
    }


}