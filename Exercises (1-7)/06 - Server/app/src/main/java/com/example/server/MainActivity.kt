package com.example.server

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {
    private lateinit var server: Server

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tvDummy = TextView(this)
        server = Server(tvDummy)
        server.start()


    }
    }