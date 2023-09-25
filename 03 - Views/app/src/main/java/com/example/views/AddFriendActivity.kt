package com.example.views

import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.activity.ComponentActivity


class AddFriendActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_friends)

        val nameEditText: EditText = findViewById(R.id.name_add_friends)
        val datePicker: DatePicker = findViewById(R.id.dp_add_friends)
        val addButton: Button = findViewById(R.id.btn_add_friends)

        addButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val birthdate = "${datePicker.dayOfMonth}/${datePicker.month + 1}/${datePicker.year}"
            val friend = Friend(name, birthdate)
            GlobalList.friendList.add(friend)
            nameEditText.text.clear()
        }

    }



}
