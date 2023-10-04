package com.example.task4

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class StartActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)

        setupDummyData()

        println(GlobalList.movieList)

        val startAddingFriendsButton: Button = findViewById(R.id.start_activity_button)
        startAddingFriendsButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    private fun setupDummyData() {
        val dummyImageResId = R.drawable.dummy_image
        val dummyImageResId2 = R.drawable.dummy_image2
        val dummyImageResId3 = R.drawable.dummy_image3

        GlobalList.movieList.apply {
            add(Movie(dummyImageResId, "Avengers", "En film om superhelter."))
            add(Movie(dummyImageResId2, "Inception", "En film om drømmer innen drømmer."))
            add(Movie(dummyImageResId3, "Interstellar", "En film om romfart."))
            add(Movie(dummyImageResId, "The Prestige", "En film om magi."))

        }
    }

}