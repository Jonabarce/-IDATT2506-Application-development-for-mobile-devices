package com.example.a05_httpogcoroutines

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.Request

class StartActivity : Activity() {


    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)

        val startGameButton = findViewById<Button>(R.id.startGameButton)
        val nameInput = findViewById<EditText>(R.id.nameInput)
        val cardNumberInput = findViewById<EditText>(R.id.cardNumberInput)

        startGameButton.setOnClickListener {
            val name = nameInput.text.toString()
            val cardNumber = cardNumberInput.text.toString()

            GlobalScope.launch(Dispatchers.IO) {
                val (isValidResponse, serverMessage) = validateUserData(name, cardNumber)
                withContext(Dispatchers.Main) {
                    if (isValidResponse) {
                        val intent = Intent(this@StartActivity, MainActivity::class.java)
                        intent.putExtra("SERVER_MESSAGE", serverMessage)
                        startActivity(intent)
                    } else {
                        showToast(serverMessage)
                    }
                }
            }
        }
    }

    private fun validateUserData(name: String, cardNumber: String): Pair<Boolean, String> {
        val client = NetworkUtils.client
        Log.d("StartActivity", "Validating user data: $name, $cardNumber")
        val requestBody = FormBody.Builder()
            .add("navn", name)
            .add("kortnummer", cardNumber)
            .build()

        val request = Request.Builder()
            .url("https://bigdata.idi.ntnu.no/mobil/tallspill.jsp")
            .header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8")
            .post(requestBody)
            .build()

        val response = client.newCall(request).execute()

        val responseString = response.body?.string()?.trim() ?: return Pair(false, "Serveren svarte ikke.")
        Log.d("StartActivity", "Server response: $responseString")
        return if (responseString.startsWith("Oppgi et tall mellom")) {
            Log.d("StartActivity", "den ble true")
            Pair(true, responseString)
        } else {
            Log.d("StartActivity", "den ble false")
            Pair(false, responseString)
        }
    }

    private fun showToast(message: String) {
        Log.d("StartActivity", "Showing toast: $message")
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}




