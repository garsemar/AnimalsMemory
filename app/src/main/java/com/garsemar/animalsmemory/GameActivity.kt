package com.garsemar.animalsmemory

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AnimalsMemory)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        val bundle: Bundle? = intent.extras
        val selectedDiff = bundle?.getString("selectedDiff")
        val intent = Intent(this, ResultsActivity::class.java)
        val points = "0"
        val time = findViewById<TextView>(R.id.tiempo)

        val form = "1000÷(120+25)"

        Toast.makeText(this@GameActivity, selectedDiff, Toast.LENGTH_SHORT).show()

        val sig = findViewById<Button>(R.id.siguiente)

        sig.setOnClickListener{
            intent.putExtra("points", points)
            intent.putExtra("selectedDiff", selectedDiff)
            startActivity(intent)
        }

        class UpdateTime : TimerTask() {
            var myTimer = time
            override fun run() {
                //calculate the new position of myBall
            }
        }

        val timer = Timer()

        val fps = 40
        val updateBall: TimerTask = UpdateTime()
        timer.scheduleAtFixedRate(updateBall, 0, (1000 / fps).toLong())
    }
}