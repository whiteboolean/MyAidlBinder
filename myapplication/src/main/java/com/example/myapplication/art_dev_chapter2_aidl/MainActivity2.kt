package com.example.myapplication.art_dev_chapter2_aidl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)
    }

    fun jump(view: android.view.View) {
        startActivity(Intent(this,MainActivity::class.java))
    }
}