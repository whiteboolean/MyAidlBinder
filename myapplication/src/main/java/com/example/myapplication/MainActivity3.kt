package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = ActivityMain3Binding.inflate(layoutInflater)

        setContentView(inflate.root)
        inflate.button.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

    }
}