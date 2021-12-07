package com.example.myapplication

import android.app.Application
import android.util.Log

private const val TAG = "MyApplication"
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "onCreate: ")
    }
}