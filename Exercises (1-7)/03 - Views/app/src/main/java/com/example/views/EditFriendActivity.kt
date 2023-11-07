package com.example.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.activity.ComponentActivity

class EditFriendActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_friends)

        val friendIndex = intent.getIntExtra("friendIndex", -1)
        if (friendIndex == -1) {
            finish()
            return
        }

        val friendToEdit = GlobalList.friendList[friendIndex]

        val nameEditText: EditText = findViewById(R.id.name_edit_friends)
        val datePicker: DatePicker = findViewById(R.id.dp_edit_friends)
        val saveButton: Button = findViewById(R.id.btn_save_edit)

        nameEditText.setText(friendToEdit.name)

        val parts = friendToEdit.birthdate.split("/")
        if (parts.size == 3) {
            datePicker.updateDate(parts[2].toInt(), parts[1].toInt() - 1, parts[0].toInt())
        }

        saveButton.setOnClickListener {
            val newName = nameEditText.text.toString()
            val newBirthdate = "${datePicker.dayOfMonth}/${datePicker.month + 1}/${datePicker.year}"

            friendToEdit.name = newName
            friendToEdit.birthdate = newBirthdate

            finish()
        }
    }
}
