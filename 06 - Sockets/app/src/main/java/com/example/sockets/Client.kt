package com.example.sockets

import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class Client(
    private val textView: TextView,
    private val SERVER_IP: String = "10.0.2.2",
    private val SERVER_PORT: Int = 12345
) {
    private var ui: String? = ""
        set(str) {
            MainScope().launch { textView.text = str }
            field = str
        }

    private var clientSocket: Socket? = null

    fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            ui = "Kobler til tjener..."
            try {
                clientSocket = Socket(SERVER_IP, SERVER_PORT)
                ui = "Koblet til tjener:\n$clientSocket"
                Log.d("Client", "Koblet til tjener:\n$clientSocket")
                while (true) {
                    readFromServer(clientSocket!!)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                ui = e.message
            }
        }
    }

    private fun readFromServer(socket: Socket) {
        val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
        val message = reader.readLine()
        ui = "Melding fra tjeneren:\n$message"
    }

    fun sendToServer(message: String) {
        CoroutineScope(Dispatchers.IO).launch {
            clientSocket?.let { socket ->
                val writer = PrintWriter(socket.getOutputStream(), true)
                writer.println(message)
                ui = "Sendte følgende til tjeneren: \n\"$message\""
            } ?: run {
                ui = "Kan ikke sende melding. Ikke koblet til serveren."
            }
        }
    }
}
