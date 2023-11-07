package com.example.datalagring

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import org.json.JSONArray

class StartActivity : Activity() {
    private lateinit var databaseManager: DatabaseManager
    private lateinit var fileManager: FileManager

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)

        databaseManager = DatabaseManager(this)
        fileManager = FileManager(this)

        initializeDatabaseWithMovies()

        findViewById<Button>(R.id.btn_start).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initializeDatabaseWithMovies() {
        databaseManager.clearAllData()
        fileManager.writeToFile("my_movies.json", "")
        val fileContent = fileManager.readMoviesFile("movies")
        fileManager.writeToFile("my_movies.json", fileContent)
        val fileContentForReadAndWrite = fileManager.readFileFromInternalStorage("my_movies.json")
        Log.d("StartActivity", "File content from read and write: $fileContentForReadAndWrite")
        val moviesJsonArray = JSONArray(fileContent)

        for (i in 0 until moviesJsonArray.length()) {
            val movieJsonObject = moviesJsonArray.getJSONObject(i)
            val title = movieJsonObject.getString("title")
            val director = movieJsonObject.getString("director")
            val actorsJsonArray = movieJsonObject.getJSONArray("actors")

            val actorsList = mutableListOf<String>()
            for (j in 0 until actorsJsonArray.length()) {
                actorsList.add(actorsJsonArray.getString(j))
            }

            databaseManager.insertMovie(title, director, actorsList)
        }
    }
}
