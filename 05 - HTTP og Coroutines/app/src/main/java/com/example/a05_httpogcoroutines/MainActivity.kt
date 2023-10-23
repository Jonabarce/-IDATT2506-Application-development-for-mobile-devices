package com.example.a05_httpogcoroutines

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.Request

class MainActivity : AppCompatActivity() {

    private lateinit var numberInput: EditText
    private lateinit var guessButton: Button
    private lateinit var playAgainButton: Button
    private lateinit var textHeader: TextView

    private val client = NetworkUtils.client

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        numberInput = findViewById(R.id.numberInput)
        guessButton = findViewById(R.id.guessButton)
        playAgainButton = findViewById(R.id.playAgainButton)
        textHeader = findViewById(R.id.textViewHeader)

        val serverMessage = intent.getStringExtra("SERVER_MESSAGE")

        textHeader.text = serverMessage

        guessButton.setOnClickListener {
            val guessedNumber = numberInput.text.toString()
            GlobalScope.launch(Dispatchers.IO) {
                val response = sendGuessToServer(guessedNumber)

                withContext(Dispatchers.Main) {
                    Log.d("MainActivity", response)
                    textHeader.text = response
                    if (response.contains("vunnet") || response.contains("Beklager")) {
                        playAgainButton.visibility = View.VISIBLE
                    }
                }
            }
        }

        playAgainButton.setOnClickListener {
            goToStartActivity()
        }
    }

    private fun sendGuessToServer(guess: String): String {
        val requestBody = FormBody.Builder()
            .add("tall", guess)
            .build()

        val request = Request.Builder()
            .url("https://bigdata.idi.ntnu.no/mobil/tallspill.jsp")
            .post(requestBody)
            .build()

        val response = client.newCall(request).execute()

        val originalResponse = response.body?.string()?.trim()
        val fixedString = String(originalResponse?.toByteArray(Charsets.ISO_8859_1) ?: byteArrayOf(), Charsets.UTF_8)
        Log.d("MainActivity", "Server response: $fixedString")
        response.body?.close()
        return fixedString ?: "Serveren svarte ikke."
    }

    private fun goToStartActivity() {
        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
        finish()
    }

}
