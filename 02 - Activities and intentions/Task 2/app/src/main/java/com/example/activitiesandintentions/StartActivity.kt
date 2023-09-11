package com.example.activitiesandintentions

import android.app.Activity
import android.content.Intent
import android.os.Bundle

import android.widget.Button

class StartActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val startButton: Button = findViewById(R.id.startMainActivityButton)
        startButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


}