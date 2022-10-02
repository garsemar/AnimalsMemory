package com.garsemar.animalsmemory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.Spinner

class MainActivity : AppCompatActivity() {
    var difficulty: Spinner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        difficulty=findViewById(R.id.difficulty)

        val difficulties = arrayOf("Difficulty", "Normal", "Hard")
        val adaptador: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, difficulties)

        difficulty?.adapter=adaptador
    }


}