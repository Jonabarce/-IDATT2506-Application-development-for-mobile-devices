package com.example.sockets

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var client: Client
    private lateinit var serverMessageTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity) // La oss anta at det er en layout-fil for denne aktiviteten.

        serverMessageTextView = findViewById(R.id.serverMessageTextView) // Dette er en TextView i din XML for å vise servermeldinger.

        client = Client(serverMessageTextView)
        client.start()

        // Når klienten mottar en melding fra serveren, vil den oppdatere serverMessageTextView via sin "ui" -eiendom.
    }
}
