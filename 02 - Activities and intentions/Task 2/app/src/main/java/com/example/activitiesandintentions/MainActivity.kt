package com.example.activitiesandintentions

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    private lateinit var numberOne: TextView
    private lateinit var numberTwo: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculations)

        numberOne = findViewById(R.id.txtNumberOne)
        numberTwo = findViewById(R.id.txtNumberTwo)
        val multiPlyButton: Button = findViewById(R.id.btnMultiply)
        val addButton: Button = findViewById(R.id.btnAdd)
        val upperBoundEditText = findViewById<EditText>(R.id.edtUpperLimit)
        val upperBound = upperBoundEditText.text.toString().toIntOrNull() ?: 10

        addButton.setOnClickListener {
            checkAndShowToast("ADD")
            val upperBound = upperBoundEditText.text.toString().toIntOrNull() ?: 10
            generateNumbers(upperBound)
        }

        multiPlyButton.setOnClickListener {
            checkAndShowToast("MULTIPLY")
            val upperBound = upperBoundEditText.text.toString().toIntOrNull() ?: 10
            generateNumbers(upperBound)
        }

        generateNumbers(upperBound)

    }

    private fun generateNumbers(upperBound: Int) {
        val randomNum1 = Random.nextInt(0, upperBound)
        val randomNum2 = Random.nextInt(0, upperBound)

        numberOne.text = randomNum1.toString()
        numberTwo.text = randomNum2.toString()
    }


    private fun checkAndShowToast(operation: String) {
        val num1 = numberOne.text.toString().toIntOrNull()
        val num2 = numberTwo.text.toString().toIntOrNull()
        val userAnswer = findViewById<EditText>(R.id.edtAnswer).text.toString().toIntOrNull()

        if (num1 != null && num2 != null && userAnswer != null) {
            val correctAnswer = sumNumbers(num1, num2, operation)

            if (userAnswer == correctAnswer) {
                val rightAnswerMessage = getString(R.string.right_answer)
                showToast(rightAnswerMessage)
            } else {
                val wrongAnswerMessage = "${getString(R.string.wrong_answer)} $correctAnswer"
                showToast(wrongAnswerMessage)
            }
        } else {
            val message = getString(R.string.answer)
            showToast(message)
        }
    }


    private fun sumNumbers(numberOne: Int, numberTwo: Int, whatToDo: String): Int {
        return when(whatToDo) {
            "ADD" -> numberOne + numberTwo
            "MULTIPLY" -> numberOne * numberTwo
            else -> 0
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }



}

