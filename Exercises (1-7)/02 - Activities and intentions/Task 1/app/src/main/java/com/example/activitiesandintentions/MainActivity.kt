package com.example.activitiesandintentions

import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.widget.Button
import android.widget.Toast


class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val upperBound = intent.getIntExtra("UPPER_BOUND", 100)
        generateNumber(upperBound)

    }

    private fun generateNumber(upperBound: Int) {
        val buttonToGetNumber: Button = findViewById(R.id.buttonNumber)
        buttonToGetNumber.setOnClickListener() {
            val value = (0..upperBound).random()
            showToast("Your number is $value")

            val resultIntent = Intent()
            resultIntent.putExtra("GENERATED_NUMBER", value)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
