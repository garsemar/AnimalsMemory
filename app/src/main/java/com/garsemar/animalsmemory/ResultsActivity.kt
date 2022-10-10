package com.garsemar.animalsmemory

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AnimalsMemory)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.results)

        val bundle: Bundle? = intent.extras
        val points = bundle?.getString("points")
        val intentPlay = Intent(this, GameActivity::class.java)
        val intentMenu = Intent(this, MainActivity::class.java)
        val playAgain = findViewById<Button>(R.id.playAgain)
        val menu = findViewById<Button>(R.id.menu)

        playAgain.setOnClickListener {
            startActivity(intentPlay)
        }
        menu.setOnClickListener {
            startActivity(intentMenu)
        }

        Toast.makeText(this@ResultsActivity, points, Toast.LENGTH_SHORT).show()
    }
}