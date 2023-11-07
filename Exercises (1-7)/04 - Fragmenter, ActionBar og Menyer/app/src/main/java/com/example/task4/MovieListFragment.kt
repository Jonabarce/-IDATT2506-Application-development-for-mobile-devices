package com.example.task4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment

class MovieListFragment : Fragment() {

    private lateinit var listView: ListView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false)
        listView = view.findViewById(R.id.movie_list)
        val movieTitles = GlobalList.movieList.toList().map { it.title }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, movieTitles)
        listView.adapter = adapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val movie = GlobalList.movieList.toList()[position]
            val detailFragment = requireFragmentManager().findFragmentById(R.id.detail_fragment) as MovieDetailFragment
            detailFragment.displayMovieDetails(movie)
        }
        return view
    }
}
