package com.garsemar.animalsmemory

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

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
        val hard = listOf<ImageView>(findViewById(R.id.imageView7), findViewById(R.id.imageView8))

        // 1000÷(120(time)+25(intents))

        if(selectedDiff == "Hard"){
            hard.forEach {
                it.visibility = View.VISIBLE
            }
        }
        else{
            hard.forEach {
                it.visibility = View.GONE
            }
        }

        game(selectedDiff)

        Toast.makeText(this@GameActivity, selectedDiff, Toast.LENGTH_SHORT).show()

        val sig = findViewById<Button>(R.id.siguiente)

        sig.setOnClickListener{
            intent.putExtra("points", points)
            intent.putExtra("selectedDiff", selectedDiff)
            startActivity(intent)
        }
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun game(selectedDiff: String?){
        val images = mutableListOf<ImageView>(findViewById(R.id.imageView1), findViewById(R.id.imageView2), findViewById(R.id.imageView3), findViewById(R.id.imageView4), findViewById(R.id.imageView5), findViewById(R.id.imageView6))
        val drawable = listOf(R.drawable.mono, R.drawable.capybara, R.drawable.foca, R.drawable.lemur)
        lateinit var map: MutableMap<ImageView, Int>
        if(selectedDiff == "Normal"){
            val numList = randomNums(6)
            map = mutableMapOf(
                images[numList[0]] to drawable[0],
                images[numList[1]] to drawable[1],
                images[numList[2]] to drawable[2],
                images[numList[3]] to drawable[0],
                images[numList[4]] to drawable[1],
                images[numList[5]] to drawable[2]
            )
        }
        else if(selectedDiff == "Hard"){
            images.add(findViewById(R.id.imageView7))
            images.add(findViewById(R.id.imageView8))
            val numList = randomNums(8)
            map = mutableMapOf(
                images[numList[0]] to drawable[0],
                images[numList[1]] to drawable[1],
                images[numList[2]] to drawable[2],
                images[numList[3]] to drawable[3],
                images[numList[4]] to drawable[0],
                images[numList[5]] to drawable[1],
                images[numList[6]] to drawable[2],
                images[numList[7]] to drawable[3]
            )
        }
        var right = 0
        val difficulty: Int = if(selectedDiff == "Normal"){
            3
        } else{
            4
        }
        val selected = mutableListOf<Map.Entry<ImageView, Int>>()

        map.forEach { it ->
            it.key.setOnClickListener { lis ->
                if(it.key.drawable.constantState == resources.getDrawable( R.drawable._1z_x7ojlvl).constantState){
                    it.key.setImageResource(it.value)
                    selected.add(it)
                    if(selected.size == 2){
                        if(selected[0].value != selected[1].value){
                            map.forEach {
                                it.key.isClickable = false
                            }
                            Handler(Looper.getMainLooper()).postDelayed({
                                selected.forEach {
                                    it.key.setImageResource(R.drawable._1z_x7ojlvl)
                                }
                                selected.clear()
                                map.forEach {
                                    it.key.isClickable = true
                                }
                            }, 500)
                        }
                        else{
                            selected.clear()
                            right += 1
                        }
                    }
                    if(right == difficulty){
                        Toast.makeText(this@GameActivity, "You win", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    private fun randomNums(size: Int) = List(size){it}.shuffled()
}