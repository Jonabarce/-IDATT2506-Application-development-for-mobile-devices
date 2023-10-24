package com.example.sockets

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var client: Client

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val tvReceived = findViewById<TextView>(R.id.tvReceived)
        val tvSent = findViewById<TextView>(R.id.tvSent)
        val inputEditText = findViewById<EditText>(R.id.editMessage)
        val sendButton = findViewById<Button>(R.id.btnSend)

        client = Client(tvReceived)
        client.start()

        sendButton.setOnClickListener {
            val message = inputEditText.text.toString()
            if (message.isNotEmpty()) {
                tvSent.text = message
                client.sendToServer(message)
                inputEditText.text.clear()
            }
        }
    }
}
