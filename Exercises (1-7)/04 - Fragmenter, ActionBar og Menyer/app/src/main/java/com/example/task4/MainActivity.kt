package com.example.task4

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main)
        } else {
            setContentView(R.layout.activity_main_land)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main)
        } else {
            Log.d("MainActivity", "onConfigurationChanged: Landscape")
            setContentView(R.layout.activity_main_land)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_previous -> {
                val currentFragment = supportFragmentManager.findFragmentById(R.id.detail_fragment) as? MovieDetailFragment
                val movieList = GlobalList.movieList.toList()
                val currentMovie = currentFragment?.getCurrentDisplayedMovie() ?: return true

                val currentIndex = movieList.indexOf(currentMovie)

                val previousIndex = if (currentIndex > 0) currentIndex - 1 else movieList.size - 1
                val previousMovie = movieList[previousIndex]

                currentFragment?.displayMovieDetails(previousMovie)

                return true
            }
            R.id.action_next -> {
                val currentFragment = supportFragmentManager.findFragmentById(R.id.detail_fragment) as? MovieDetailFragment
                val movieList = GlobalList.movieList.toList()
                val currentMovie = currentFragment?.getCurrentDisplayedMovie() ?: return true

                val currentIndex = movieList.indexOf(currentMovie)

                val nextIndex = if (currentIndex < movieList.size - 1) currentIndex + 1 else 0
                val nextMovie = movieList[nextIndex]

                currentFragment?.displayMovieDetails(nextMovie)

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
