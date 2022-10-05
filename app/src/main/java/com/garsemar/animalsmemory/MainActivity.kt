package com.garsemar.animalsmemory

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
        val difficulty = resources.getStringArray(R.array.Difficulty)
        val intent = Intent(this, GameActivity::class.java)
        var selectedDiff: String? = null

        val spinner = findViewById<Spinner>(R.id.difficulty)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, difficulty
            )
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if(position != 0){
                    selectedDiff = difficulty[position]
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(this@MainActivity, "Select the difficulty", Toast.LENGTH_SHORT).show()
            }
        }
        play.setOnClickListener {
            if(selectedDiff == null){
                Toast.makeText(this@MainActivity, "Select the difficulty", Toast.LENGTH_SHORT).show()
            }
            else{
                intent.putExtra("selectedDiff", selectedDiff)
                startActivity(intent)
            }
        }
    }
}