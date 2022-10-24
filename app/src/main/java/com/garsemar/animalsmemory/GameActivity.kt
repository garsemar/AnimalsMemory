package com.garsemar.animalsmemory

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.*
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
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun game(){
        val images = listOf<ImageView>(findViewById(R.id.imageView1), findViewById(R.id.imageView2), findViewById(R.id.imageView3), findViewById(R.id.imageView4), findViewById(R.id.imageView5), findViewById(R.id.imageView6))
        val drawable = listOf(R.drawable.mono, R.drawable.capybara, R.drawable.foca)
        val numList = randomNums()
        val map = mapOf(
            images[numList[0]] to drawable[0],
            images[numList[1]] to drawable[1],
            images[numList[2]] to drawable[2],
            images[numList[3]] to drawable[0],
            images[numList[4]] to drawable[1],
            images[numList[5]] to drawable[2],
        )
        var num = 0
        val selected = mutableListOf<Drawable>()
        map.forEach { it ->
            it.key.setOnClickListener { lis ->
                if(it.key.drawable.constantState == resources.getDrawable( R.drawable._1z_x7ojlvl).constantState){
                    it.key.setImageResource(it.value)
                    //selected.add()
                    num += 1
                    if(num == 2){

                    }
                }
                else{
                    it.key.setImageResource(R.drawable._1z_x7ojlvl)
                }
            }
        }
    }
}

fun randomNums() = List(6){it}.shuffled()