package com.example.juniormathwin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.juniormathwin.dataBase.GameScore

class scoreView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_view)
        //store score and level into the variables
        val score = intent.getIntExtra("score", 0)
        val level = intent.getStringExtra("level") ?: ""

        //display score and level on textView
        val scoreTextView = findViewById<TextView>(R.id.currentScore)
        scoreTextView.text = "$score"

        val levelTextView = findViewById<TextView>(R.id.levelTextView)
        levelTextView.text = level
        //pass current score to store the database
        val dbHelper = GameScore(this)
        dbHelper.addScore(score, level)

        //take most 5 highest scores from database
        val highestScores = dbHelper.getScores(level)
        val highestScoresTextView = findViewById<TextView>(R.id.highestScoreslist)
        //view highest score on by one
        highestScoresTextView.text = highestScores.joinToString(separator = "\n") { it.toString() }

        //return button to navigate to mainActivity
        val returnButton = findViewById<Button>(R.id.returnButton)
        returnButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        //to avoid go back to previous screen
    }

}