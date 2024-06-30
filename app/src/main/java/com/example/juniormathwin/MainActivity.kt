package com.example.juniormathwin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //buttons to choose level
            //easy button
        val buttonEasy = findViewById<Button>(R.id.buttonEasy)
        buttonEasy.setOnClickListener {
            val intent = Intent(this, problemsView::class.java)
            intent.putExtra("level", "Easy")
            startActivity(intent)
        }
            //medium button
        val buttonMedium = findViewById<Button>(R.id.buttonMedium)
        buttonMedium.setOnClickListener {
            val intent = Intent(this, problemsView::class.java)
            intent.putExtra("level", "Medium")
            startActivity(intent)
        }
            //hard button
        val buttonHard = findViewById<Button>(R.id.buttonHard)
        buttonHard.setOnClickListener {
            val intent = Intent(this, problemsView::class.java)
            intent.putExtra("level", "Hard")
            startActivity(intent)
        }
    }

    //exit from the app when the user clicks the in-build back button
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}