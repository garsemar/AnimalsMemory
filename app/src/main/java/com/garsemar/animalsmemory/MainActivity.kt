package com.garsemar.animalsmemory

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AnimalsMemory)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val play = findViewById<Button>(R.id.play)
        val help = findViewById<Button>(R.id.help)
        val difficulty = resources.getStringArray(R.array.Difficulty)
        val intent = Intent(this, GameActivity::class.java)
        var selectedDiff: String? = null

        val spinner: Spinner = findViewById(R.id.difficulty)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, difficulty
        )
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@MainActivity, "Select something", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedDiff = if (position != 0) {
                    difficulty[position]
                } else {
                    null
                }
            }
        }
        play.setOnClickListener {
            if (selectedDiff == null) {
                Toast.makeText(this@MainActivity, "Select the difficulty", Toast.LENGTH_SHORT)
                    .show()
            } else {
                intent.putExtra("selectedDiff", selectedDiff)
                startActivity(intent)
            }
        }


        val builder = AlertDialog.Builder(this)
        builder.setTitle("Help")
        builder.setMessage("""
            You have to turn over two cards, if they are the same they stay, but if not, they are turned over again. You win when you flip all the cards.
        """.trimIndent()
        )
        builder.setPositiveButton("Close", null)

        val dialog = builder.create()

        help.setOnClickListener {
            dialog.show()
        }
    }
}