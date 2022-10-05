package com.garsemar.animalsmemory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AnimalsMemory)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        val bundle: Bundle? = intent.extras
        val selectedDiff = bundle?.getString("selectedDiff")

        println(selectedDiff)
    }
}