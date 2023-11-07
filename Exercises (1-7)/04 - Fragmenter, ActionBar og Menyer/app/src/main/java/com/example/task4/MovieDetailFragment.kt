package com.example.task4

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class MovieDetailFragment : Fragment() {

    private lateinit var movieImageView: ImageView
    private lateinit var movieTitleTextView: TextView
    private lateinit var movieDescriptionTextView: TextView
    private var currentDisplayedMovie: Movie? = null

    fun getCurrentDisplayedMovie(): Movie? {
        return currentDisplayedMovie
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_detail, container, false)
        movieImageView = view.findViewById(R.id.movie_image)
        movieTitleTextView = view.findViewById(R.id.movie_title)
        movieDescriptionTextView = view.findViewById(R.id.movie_description)
        return view
    }

    fun displayMovieDetails(movie: Movie) {
        currentDisplayedMovie = movie
        movieImageView.setImageResource(movie.image)
        movieTitleTextView.text = movie.title
        movieDescriptionTextView.text = movie.description
    }
}
