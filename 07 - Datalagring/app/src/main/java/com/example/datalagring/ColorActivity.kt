package com.example.datalagring

import android.app.Activity
import android.os.Bundle
import android.widget.Button

class ColorActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.color_activity)

        findViewById<Button>(R.id.color1Button).setOnClickListener {
            GlobalState.selectedBackgroundColor = R.color.background1
            finish()
        }

        findViewById<Button>(R.id.color2Button).setOnClickListener {
            GlobalState.selectedBackgroundColor = R.color.background2
            finish()
        }

        findViewById<Button>(R.id.color3Button).setOnClickListener {
            GlobalState.selectedBackgroundColor = R.color.background3
            finish()
        }
    }
}