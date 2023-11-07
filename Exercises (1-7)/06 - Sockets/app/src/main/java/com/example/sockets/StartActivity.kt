package com.example.sockets

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class StartActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)



        findViewById<Button>(R.id.btn_start_chat).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}