package com.example.datalagring

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class FileManager(private val context: Context) {

    fun readMoviesFile(name : String): String {
        val content = StringBuilder()

        val resourceId = context.resources.getIdentifier(name, "raw", context.packageName)

        try {
            val inputStream: InputStream = context.resources.openRawResource(resourceId)
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line = reader.readLine()
                while (line != null) {
                    content.append(line)
                    content.append("\n")
                    line = reader.readLine()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Log.d("FileManager", "Movies file content:\n$content")
        return content.toString()
    }

    fun readFileFromInternalStorage(filename: String): String {
        val content = StringBuilder()
        try {
            context.openFileInput(filename).use { inputStream ->
                BufferedReader(InputStreamReader(inputStream)).use { reader ->
                    var line = reader.readLine()
                    while (line != null) {
                        content.append(line)
                        content.append("\n")
                        line = reader.readLine()
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Log.d("FileManager", "Internal storage file content:\n$content")
        return content.toString()
    }

    fun writeToFile(filename: String, content: String) {
        try {
            val outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE)
            OutputStreamWriter(outputStream).use { writer ->
                writer.write(content)
            }
            Log.d("FileManager", "Data written to file: $filename")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}
