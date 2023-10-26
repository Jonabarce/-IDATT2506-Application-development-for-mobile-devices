package com.example.sockets

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var client: Client
    private val receivedMessages = ArrayList<String>()
    private val sentMessages = ArrayList<String>()
    private lateinit var receivedAdapter: ArrayAdapter<String>
    private lateinit var sentAdapter: ArrayAdapter<String>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val tvReceived = findViewById<TextView>(R.id.tvReceived)
        val lvReceived = findViewById<ListView>(R.id.lvReceived)
        val lvSent = findViewById<ListView>(R.id.lvSent)
        val inputEditText = findViewById<EditText>(R.id.editMessage)
        val sendButton = findViewById<Button>(R.id.btnSend)

        receivedAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, receivedMessages)
        sentAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sentMessages)

        lvReceived.adapter = receivedAdapter
        lvSent.adapter = sentAdapter

        client = Client(tvReceived)
        client.start()

        sendButton.setOnClickListener {
            val message = inputEditText.text.toString()
            if (message.isNotEmpty()) {
                sentMessages.add(message)
                sentAdapter.notifyDataSetChanged()
                client.sendToServer(message)
                inputEditText.text.clear()
            }
        }
    }
}

