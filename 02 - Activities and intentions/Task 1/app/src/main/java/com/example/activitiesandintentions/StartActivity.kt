package com.example.activitiesandintentions


import android.app.Activity
import android.content.Intent
import android.os.Bundle

import android.widget.Button
import android.widget.TextView


class StartActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val startButton: Button = findViewById(R.id.startMainActivityButton)
        startButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("UPPER_BOUND", 150)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK) {
            val resultNumber = data?.getIntExtra("GENERATED_NUMBER", -1)
            val resultTextView: TextView = findViewById(R.id.resultTextView)
            resultTextView.text = "Generated Number: $resultNumber"
        }
    }
}