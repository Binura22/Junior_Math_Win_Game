package com.example.juniormathwin.performance

import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.juniormathwin.R
import com.example.juniormathwin.ViewModel.ProblemsViewData
import com.example.juniormathwin.problemsView
import kotlin.random.Random

class EasyProblemGenerator(private val activity: problemsView) {

    fun generateAndDisplayProblem(viewModel: ProblemsViewData) {
        //variables declaration
        val randomNumber1 = generateRandomNumber()
        //check number1
        var randomNumber2: Int
        do {
            randomNumber2 = generateRandomNumber()
        } while (randomNumber2 >= randomNumber1)

        val randomOperator = generateRandomOperator()

        activity.findViewById<TextView>(R.id.number1).text = randomNumber1.toString()
        activity.findViewById<TextView>(R.id.number2).text = randomNumber2.toString()
        activity.findViewById<TextView>(R.id.operationSign).text = randomOperator

        //generate correct answer based on random operator
        val correctAnswer = when (randomOperator) {
            "+" -> {
                randomNumber1 + randomNumber2
            }

            "-" -> {
                randomNumber1 - randomNumber2
            }

            else -> {
                throw IllegalArgumentException("Invalid operator: $randomOperator")
            }
        }
        //buttons declaration
        val buttons = listOf<Button>(
            activity.findViewById(R.id.answer1),
            activity.findViewById(R.id.answer2),
            activity.findViewById(R.id.answer3),
            activity.findViewById(R.id.answer4)
        )
        //assign fake answers to 3 buttons
        val fakeAnswers = mutableListOf<Int>()
        while (fakeAnswers.size < 3) {
            //call generateRandomAnswer function
            val fakeAnswer = generateRandomAnswer(correctAnswer)
            if (fakeAnswer != correctAnswer && !fakeAnswers.contains(fakeAnswer)) {
                fakeAnswers.add(fakeAnswer)
            }
        }
        //take correct button index
        val correctButtonIndex = Random.nextInt(buttons.size)
        buttons[correctButtonIndex].text = correctAnswer.toString()

        for (i in buttons.indices) {
            if (i != correctButtonIndex) {
                val randomFakeAnswerIndex = Random.nextInt(fakeAnswers.size)
                buttons[i].text = fakeAnswers[randomFakeAnswerIndex].toString()
                fakeAnswers.removeAt(randomFakeAnswerIndex)
            }
        }
        //countdown timer call
        startCountdownTimer(viewModel)
    }
    //generateRandomNumber function
    private fun generateRandomNumber(): Int {
        return Random.nextInt(1, 100)
    }

    //generateRandomAnswer function
    private fun generateRandomAnswer(correctAnswer:Int): Int {
        val minRange: Int
        val maxRange: Int
        val answerRange = 10
        //give range to fake answers
        minRange = correctAnswer - answerRange
        maxRange = correctAnswer + answerRange

        var fakeAnswer: Int
        do {
            fakeAnswer = Random.nextInt(minRange, maxRange)
        } while (fakeAnswer == correctAnswer)
        return fakeAnswer
    }
    //function to generate random operator
    private fun generateRandomOperator(): String {
        val operator = Random.nextInt(2)
        return if (operator == 0) {
            "+"
        } else {
            "-"
        }
    }
    //startCountdownTimer function
    private fun startCountdownTimer(viewModel: ProblemsViewData) {
        activity.timer = object : CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                activity.findViewById<TextView>(R.id.time).text = secondsRemaining.toString()
            }

            override fun onFinish() {
                val currentScore = viewModel.score.value ?: 0
                val newScore = if (currentScore <= 0) 0 else currentScore - 200
                viewModel.setScore(newScore)
                activity.findViewById<TextView>(R.id.score).text = "Score: $newScore"
                Toast.makeText(activity, "Time's up!", Toast.LENGTH_SHORT).show()
                generateAndDisplayProblem(viewModel)
            }
        }
        activity.timer.start()
    }
}