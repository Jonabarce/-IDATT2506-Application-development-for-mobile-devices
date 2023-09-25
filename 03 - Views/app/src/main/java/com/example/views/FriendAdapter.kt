package com.example.views

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FriendAdapter(private val friends: List<Friend>,
                    private val onClickListener: (Friend) -> Unit) : RecyclerView.Adapter<FriendAdapter.FriendViewHolder>() {

    inner class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val birthdateTextView: TextView = itemView.findViewById(R.id.birthdateTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friend_list_item, parent, false)
        return FriendViewHolder(view)
    }

    override fun getItemCount(): Int = friends.size

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val friend = friends[position]
        holder.nameTextView.text = friend.name
        holder.birthdateTextView.text = friend.birthdate

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, EditFriendActivity::class.java)
            intent.putExtra("name", friend.name)
            intent.putExtra("birthdate", friend.birthdate)
            it.context.startActivity(intent)
            onClickListener(friend)
        }
    }
}
