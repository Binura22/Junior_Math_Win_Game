package com.example.juniormathwin.performance

import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.juniormathwin.R
import com.example.juniormathwin.ViewModel.ProblemsViewData
import com.example.juniormathwin.problemsView
import kotlin.random.Random

class HardProblemGenerator(private val activity: problemsView) {

    fun generateAndDisplayProblem(viewModel: ProblemsViewData) {
        val randomNumber1 = generateRandomNumber()
        val randomNumber2 = generateRandomNumber()
        val randomOperator = generateRandomOperator()

        activity.findViewById<TextView>(R.id.number1).text = randomNumber1.toString()
        activity.findViewById<TextView>(R.id.number2).text = randomNumber2.toString()
        activity.findViewById<TextView>(R.id.operationSign).text = randomOperator

        val correctAnswer = when (randomOperator) {
            "+" -> {
                randomNumber1 + randomNumber2
            }

            "-" -> {
                randomNumber1 - randomNumber2
            }

            "×" -> {
                randomNumber1 * randomNumber2
            }

            "÷" -> {
                randomNumber1 / randomNumber2
            }

            else -> {
                throw IllegalArgumentException("Invalid operator: $randomOperator")
            }
        }

        val buttons = listOf<Button>(
            activity.findViewById(R.id.answer1),
            activity.findViewById(R.id.answer2),
            activity.findViewById(R.id.answer3),
            activity.findViewById(R.id.answer4)
        )

        val fakeAnswers = mutableListOf<Int>()
        while (fakeAnswers.size < 3) {
            val fakeAnswer = generateRandomAnswer(correctAnswer)
            if (fakeAnswer != correctAnswer && !fakeAnswers.contains(fakeAnswer)) {
                fakeAnswers.add(fakeAnswer)
            }
        }

        val correctButtonIndex = Random.nextInt(buttons.size)
        buttons[correctButtonIndex].text = correctAnswer.toString()

        for (i in buttons.indices) {
            if (i != correctButtonIndex) {
                val randomFakeAnswerIndex = Random.nextInt(fakeAnswers.size)
                buttons[i].text = fakeAnswers[randomFakeAnswerIndex].toString()
                fakeAnswers.removeAt(randomFakeAnswerIndex)
            }
        }

        startCountdownTimer(viewModel)
    }

    private fun generateRandomNumber(): Int {
        return Random.nextInt(-99, 100)
    }

    private fun generateRandomAnswer(correctAnswer:Int): Int {
        val minRange: Int
        val maxRange: Int
        val answerRange = 10

        minRange = correctAnswer - answerRange
        maxRange = correctAnswer + answerRange

        var fakeAnswer: Int
        do {
            fakeAnswer = Random.nextInt(minRange, maxRange)
        } while (fakeAnswer == correctAnswer)
        return fakeAnswer
    }

    private fun generateRandomOperator(): String {
        val operator = Random.nextInt(4)
        return when (operator) {
            0 -> {
                "+"
            }

            1 -> {
                "-"
            }

            2 -> {
                "×"
            }

            else -> {
                "÷"
            }
        }
    }

    private fun startCountdownTimer(viewModel: ProblemsViewData) {
        activity.timer = object : CountDownTimer(15000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                activity.findViewById<TextView>(R.id.time).text = secondsRemaining.toString()
            }

            override fun onFinish() {
                val currentScore = viewModel.score.value ?: 0
                val newScore = if (currentScore <= 0) 0 else currentScore - 250
                viewModel.setScore(newScore)
                activity.findViewById<TextView>(R.id.score).text = "Score: $newScore"
                Toast.makeText(activity, "Time's up!", Toast.LENGTH_SHORT).show()
                generateAndDisplayProblem(viewModel)
            }
        }
        activity.timer.start()
    }
}