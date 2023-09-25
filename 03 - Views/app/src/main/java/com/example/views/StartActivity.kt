package com.example.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle

import android.widget.Button

class StartActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)

        val startAddingFriendsButton: Button = findViewById(R.id.startAddingFriendsButton)
        startAddingFriendsButton.setOnClickListener {
            startActivity(Intent(this, AddFriendActivity::class.java))
        }


        val listFriendsButton: Button = findViewById(R.id.listFriendsButton)
        listFriendsButton.setOnClickListener {
            startActivity(Intent(this, EditFriendActivity::class.java))
        }

    }


}