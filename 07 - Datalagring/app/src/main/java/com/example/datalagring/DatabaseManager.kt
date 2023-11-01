package com.example.datalagring

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class DatabaseManager(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "movies.db"
        const val DATABASE_VERSION = 1

        const val MOVIES_TABLE_NAME = "movies"
        const val MOVIE_ID = "movie_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DIRECTOR_ID = "director_id"

        const val DIRECTORS_TABLE_NAME = "directors"
        const val DIRECTOR_ID = "director_id"
        const val COLUMN_DIRECTOR_NAME = "name"

        const val ACTORS_TABLE_NAME = "actors"
        const val ACTOR_ID = "actor_id"
        const val COLUMN_ACTOR_NAME = "name"

        const val MOVIE_ACTORS_TABLE_NAME = "movie_actors"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createMoviesTable = "CREATE TABLE $MOVIES_TABLE_NAME (" +
                "$MOVIE_ID INTEGER PRIMARY KEY," +
                "$COLUMN_TITLE TEXT," +
                "$COLUMN_DIRECTOR_ID INTEGER," +
                "FOREIGN KEY($COLUMN_DIRECTOR_ID) REFERENCES $DIRECTORS_TABLE_NAME($DIRECTOR_ID))"

        val createDirectorsTable = "CREATE TABLE $DIRECTORS_TABLE_NAME (" +
                "$DIRECTOR_ID INTEGER PRIMARY KEY," +
                "$COLUMN_DIRECTOR_NAME TEXT)"

        val createActorsTable = "CREATE TABLE $ACTORS_TABLE_NAME (" +
                "$ACTOR_ID INTEGER PRIMARY KEY," +
                "$COLUMN_ACTOR_NAME TEXT)"

        val createMovieActorsTable = "CREATE TABLE $MOVIE_ACTORS_TABLE_NAME (" +
                "$MOVIE_ID INTEGER," +
                "$ACTOR_ID INTEGER," +
                "FOREIGN KEY($MOVIE_ID) REFERENCES $MOVIES_TABLE_NAME($MOVIE_ID)," +
                "FOREIGN KEY($ACTOR_ID) REFERENCES $ACTORS_TABLE_NAME($ACTOR_ID))"

        db?.execSQL(createMoviesTable)
        db?.execSQL(createDirectorsTable)
        db?.execSQL(createActorsTable)
        db?.execSQL(createMovieActorsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $MOVIES_TABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $DIRECTORS_TABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $ACTORS_TABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $MOVIE_ACTORS_TABLE_NAME")
        onCreate(db)    }

    fun clearAllData() {
        writableDatabase.use { db ->
            db.execSQL("DELETE FROM $MOVIES_TABLE_NAME")
            db.execSQL("DELETE FROM $DIRECTORS_TABLE_NAME")
            db.execSQL("DELETE FROM $ACTORS_TABLE_NAME")
            db.execSQL("DELETE FROM $MOVIE_ACTORS_TABLE_NAME")
        }
    }
    private fun insertValue(
        database: SQLiteDatabase,
        table: String,
        field: String,
        value: String ): Long {
        val values = ContentValues()
        values.put(field, value.trim())
        return database.insert(table, null, values)
    }

    private fun insertDirector(database: SQLiteDatabase, directorName: String): Long {
        val values = ContentValues()
        values.put(COLUMN_DIRECTOR_NAME, directorName.trim())
        return database.insert(DIRECTORS_TABLE_NAME, null, values)
    }

    private fun insertActor(database: SQLiteDatabase, actorName: String): Long {
        val values = ContentValues()
        values.put(COLUMN_ACTOR_NAME, actorName.trim())
        return database.insert(ACTORS_TABLE_NAME, null, values)
    }

    private fun insertMovieActor(database: SQLiteDatabase, movieId: Long, actorId: Long) {
        val values = ContentValues()
        values.put(MOVIE_ID, movieId)
        values.put(ACTOR_ID, actorId)
        database.insert(MOVIE_ACTORS_TABLE_NAME, null, values)
    }

    fun insertMovie(title: String, director: String, actors: List<String>): Long {
        val database = this.writableDatabase

        val directorId: Long = getDirectorId(director) ?: insertDirector(database, director)

        val values = ContentValues()
        values.put(COLUMN_TITLE, title)
        values.put(COLUMN_DIRECTOR_ID, directorId)
        val movieId = database.insert(MOVIES_TABLE_NAME, null, values)

        actors.forEach { actor ->
            val actorId: Long = getActorId(actor) ?: insertActor(database, actor)
            insertMovieActor(database, movieId, actorId)
        }

        return movieId
    }

    @SuppressLint("Range")
    fun getDirectorId(directorName: String): Long? {
        val database = this.readableDatabase
        val cursor = database.query(
            DIRECTORS_TABLE_NAME,
            arrayOf(DIRECTOR_ID),
            "$COLUMN_DIRECTOR_NAME = ?",
            arrayOf(directorName),
            null, null, null)

        cursor?.use {
            if (cursor.moveToFirst()) {
                return cursor.getLong(cursor.getColumnIndex(DIRECTOR_ID))
            }
        }
        return null
    }

    @SuppressLint("Range")
    fun getActorId(actorName: String): Long? {
        val database = this.readableDatabase
        val cursor = database.query(
            ACTORS_TABLE_NAME,
            arrayOf(ACTOR_ID),
            "$COLUMN_ACTOR_NAME = ?",
            arrayOf(actorName),
            null, null, null)

        cursor?.use {
            if (cursor.moveToFirst()) {
                return cursor.getLong(cursor.getColumnIndex(ACTOR_ID))
            }
        }
        return null
    }

    private fun readFromCursor(cursor: Cursor, numberOfColumns: Int ):
            ArrayList<String> {
        val result = ArrayList<String>()
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val item = StringBuilder("")
            for (i in 0 until numberOfColumns) {
                item.append("${cursor.getString(i)} ")
            }
            result.add("$item")
            cursor.moveToNext()
        }
        return result
    }



    fun performRawQuery(
        select: Array<String>,
        from: Array<String>,
        join: Array<String> = emptyArray(),
        where: String? = null
    ): ArrayList<String> {
        val queryBuilder = StringBuilder().apply {
            append("SELECT ")
            append(select.joinToString(", "))
            append(" FROM ")
            append(from.joinToString(", "))

            if (join.isNotEmpty()) {
                append(" ")
                append(join.joinToString(" "))
            }

            if (where != null) {
                append(" WHERE $where")
            }
        }

        readableDatabase.use { db ->
            db.rawQuery(queryBuilder.toString(), null).use { cursor ->
                return readFromCursor(cursor, select.size)
            }
        }
    }



}



