package com.example.datalagring

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private lateinit var resultsListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        resultsListView = findViewById(R.id.lv_results)

        val database = Database(this)

        findViewById<Button>(R.id.btn_all_movies).setOnClickListener {
            val movies = database.allMovies
            updateListView(movies)
            Log.d("MainActivity", "All movies: $movies")
        }

        findViewById<Button>(R.id.btn_movies_by_director).setOnClickListener {
            val moviesByDirector = database.getMoviesByDirector("Frank Darabont")
            updateListView(moviesByDirector)
            Log.d("MainActivity", "Movies by director: $moviesByDirector")
        }

        findViewById<Button>(R.id.btn_actors_for_movie).setOnClickListener {
            val actorsForMovie = database.getActorsForMovie("The Godfather")
            updateListView(actorsForMovie)
            Log.d("MainActivity", "Actors for movie: $actorsForMovie")
        }

        findViewById<Button>(R.id.changeColorButton).setOnClickListener {
            val intent = Intent(this, ColorActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val backgroundColor = resources.getColor(GlobalState.selectedBackgroundColor, null)
        findViewById<LinearLayout>(R.id.mainLinearLayout).setBackgroundColor(backgroundColor)
    }

    private fun updateListView(data: List<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        resultsListView.adapter = adapter
    }
}
