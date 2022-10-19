package com.garsemar.animalsmemory

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

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
        val hard = findViewById<LinearLayout>(R.id.hard)

        // 1000÷(120(time)+25(intents))

        if(selectedDiff == "Hard"){
            hard.visibility = View.VISIBLE
        }
        else{
            hard.visibility = View.GONE
        }

        game()

        Toast.makeText(this@GameActivity, selectedDiff, Toast.LENGTH_SHORT).show()

        val sig = findViewById<Button>(R.id.siguiente)

        sig.setOnClickListener{
            intent.putExtra("points", points)
            intent.putExtra("selectedDiff", selectedDiff)
            startActivity(intent)
        }
    }
    fun game(){
        /*val images = listOf<ImageView>(
            findViewById(R.id.imageView1),
            findViewById(R.id.imageView2),
            findViewById(R.id.imageView3),
            findViewById(R.id.imageView4),
            findViewById(R.id.imageView5),
            findViewById(R.id.imageView6)
        )*/
        val pos = generateSequence { Random.nextInt(1,7) }.distinct().toList()
        println(pos)
        /*images[pos[0]].setImageResource(R.drawable.mono)
        images[pos[1]].setImageResource(R.drawable.foca)
        images[pos[2]].setImageResource(R.drawable.mono)
        images[pos[3]].setImageResource(R.drawable.capybara)
        images[pos[4]].setImageResource(R.drawable.foca)
        images[pos[5]].setImageResource(R.drawable.capybara)*/

//        images.random()

        /*images[0].setOnClickListener {
            images.setImageResource(R.drawable.mono)
        }*/
    }
}