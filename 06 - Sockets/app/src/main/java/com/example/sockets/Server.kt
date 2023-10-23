package com.example.sockets

import android.os.Build
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

class Server(private val textView: TextView, private val PORT: Int = 12345) {
    private var ui: String? = ""
        set(str) {
            MainScope().launch { textView.text = str }
            field = str
        }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                ui = "Starter Tjener ..."
                ServerSocket(PORT).use { serverSocket ->
                    ui = "ServerSocket opprettet, venter på at en klient kobler seg til...."
                    Log.d("Server", "ServerSocket opprettet, venter på at en klient kobler seg til....")
                    while (true) {
                        val clientSocket: Socket = serverSocket.accept()
                        CoroutineScope(Dispatchers.IO).launch {
                            handleClient(clientSocket)
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                ui = e.message
            }
        }
    }

    private fun handleClient(clientSocket: Socket) {
        clientSocket.use {
            ui = "En Klient koblet seg til:\n$clientSocket"
            while (true) {
                readFromClient(clientSocket)
            }
        }
    }

    private fun readFromClient(socket: Socket) {
        val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
        val message = reader.readLine()
        ui = "Klienten sier:\n$message"
    }

    private fun sendToClient(socket: Socket, message: String) {
        val writer = PrintWriter(socket.getOutputStream(), true)
        writer.println(message)
        ui = "Sendte følgende til klienten:\n$message"
    }
}
