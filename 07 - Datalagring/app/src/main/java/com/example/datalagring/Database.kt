package com.example.datalagring

import android.content.Context

class Database(context: Context) : DatabaseManager(context){

    val allMovies: ArrayList<String>
        get() = performRawQuery(
            select = arrayOf("$MOVIES_TABLE_NAME.$COLUMN_TITLE"),
            from = arrayOf(MOVIES_TABLE_NAME)
        )

    fun getMoviesByDirector(directorName: String): ArrayList<String> {
        return performRawQuery(
            select = arrayOf("$MOVIES_TABLE_NAME.$COLUMN_TITLE"),
            from = arrayOf(MOVIES_TABLE_NAME),
            join = arrayOf(
                "INNER JOIN $DIRECTORS_TABLE_NAME ON $MOVIES_TABLE_NAME.$COLUMN_DIRECTOR_ID = $DIRECTORS_TABLE_NAME.$DIRECTOR_ID"
            ),
            where = "$DIRECTORS_TABLE_NAME.$COLUMN_DIRECTOR_NAME = '$directorName'"
        )
    }

    fun getActorsForMovie(movieTitle: String): ArrayList<String> {
        return performRawQuery(
            select = arrayOf("DISTINCT $ACTORS_TABLE_NAME.$COLUMN_ACTOR_NAME"),
            from = arrayOf(MOVIES_TABLE_NAME),
            join = arrayOf(
                "INNER JOIN $MOVIE_ACTORS_TABLE_NAME ON $MOVIES_TABLE_NAME.$MOVIE_ID = $MOVIE_ACTORS_TABLE_NAME.$MOVIE_ID",
                "INNER JOIN $ACTORS_TABLE_NAME ON $MOVIE_ACTORS_TABLE_NAME.$ACTOR_ID = $ACTORS_TABLE_NAME.$ACTOR_ID"
            ),
            where = "$MOVIES_TABLE_NAME.$COLUMN_TITLE = '$movieTitle'"
        )
    }
}