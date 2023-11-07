package com.example.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListFriendsActivity : ComponentActivity() {

    private lateinit var friendsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_friends)

        friendsRecyclerView = findViewById(R.id.friendsRecyclerView)
        friendsRecyclerView.layoutManager = LinearLayoutManager(this)

        updateFriendList()
    }

    override fun onResume() {
        super.onResume()
        updateFriendList()
    }

    private fun updateFriendList() {
        val adapter = FriendAdapter(GlobalList.friendList) { friend ->
            val intent = Intent(this, EditFriendActivity::class.java)
            intent.putExtra("friendIndex", GlobalList.friendList.indexOf(friend))
            startActivity(intent)
        }

        friendsRecyclerView.adapter = adapter
    }
}
