package com.example.sockets

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class StartActivity : Activity() {
    private lateinit var server: Server

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)

        val tvDummy = TextView(this)
        server = Server(tvDummy)

        findViewById<Button>(R.id.btn_start_chat).setOnClickListener {
            // Start serveren
            server.start()
            // Gå til MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}