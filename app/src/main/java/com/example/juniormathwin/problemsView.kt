package com.example.juniormathwin


import ProblemChecker
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.juniormathwin.ViewModel.ProblemsViewData
import com.example.juniormathwin.performance.EasyProblemGenerator

import com.example.juniormathwin.performance.HardProblemGenerator
import com.example.juniormathwin.performance.MediumProblemGenerator

class problemsView : AppCompatActivity() {

    private lateinit var number1: TextView
    private lateinit var number2: TextView
    private lateinit var operationSign: TextView
    private lateinit var time: TextView
    private lateinit var score: TextView
    lateinit var timer: CountDownTimer
    private lateinit var viewModel: ProblemsViewData
    var problemsCounter = 0
//    var scoreValue: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problems_view)

        viewModel = ViewModelProvider(this).get(ProblemsViewData::class.java)

        number1 = findViewById(R.id.number1)
        number2 = findViewById(R.id.number2)
        operationSign = findViewById(R.id.operationSign)
        time = findViewById(R.id.time)
        score = findViewById(R.id.score)

        //generate problems based on level
        val level = intent.getStringExtra("level")
        if (level == "Easy") {
            val problemGenerator = EasyProblemGenerator(this)
            problemGenerator.generateAndDisplayProblem(viewModel)
        } else if (level == "Medium") {
            val problemGenerator = MediumProblemGenerator(this)
            problemGenerator.generateAndDisplayProblem(viewModel)
        } else {
            val problemGenerator = HardProblemGenerator(this)
            problemGenerator.generateAndDisplayProblem(viewModel)
        }

        val buttons = listOf<Button>(
            findViewById(R.id.answer1),
            findViewById(R.id.answer2),
            findViewById(R.id.answer3),
            findViewById(R.id.answer4)
        )

        for (button in buttons) {
            button.setOnClickListener {
                val selectedAnswer = button.text.toString().toInt()
                ProblemChecker().checkAnswer(selectedAnswer,this,viewModel)
            }
        }
    }
    //cancel the timer when user navigate to scoreView
    override fun onDestroy() {
        super.onDestroy()
        if (::timer.isInitialized) {
            timer.cancel()
        }
    }
}
