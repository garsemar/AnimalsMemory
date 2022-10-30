package com.garsemar.animalsmemory

import android.annotation.SuppressLint
import android.content.Intent
import android.os.*
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class GameActivity : AppCompatActivity() {

    lateinit var viewModel: GameViewModel
    lateinit var images: MutableList<ImageView>
    lateinit var meter: Chronometer
    lateinit var movements: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AnimalsMemory)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        val bundle: Bundle? = intent.extras
        val selectedDiff = bundle?.getString("selectedDiff")
        val hard = listOf<ImageView>(findViewById(R.id.imageView7), findViewById(R.id.imageView8))
        meter = findViewById(R.id.c_meter)
        movements = findViewById(R.id.textView9)

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        viewModel.init(selectedDiff!!)

        images = mutableListOf(
            findViewById(R.id.imageView1),
            findViewById(R.id.imageView2),
            findViewById(R.id.imageView3),
            findViewById(R.id.imageView4),
            findViewById(R.id.imageView5),
            findViewById(R.id.imageView6)
        )

        if(selectedDiff == "Hard"){
            hard.forEach {
                it.visibility = View.VISIBLE
                images.add(it)
            }
        }
        else{
            hard.forEach {
                it.visibility = View.GONE
            }
        }

        updateUI()
        meter.start()

        for(i in images.indices){
            images[i].setOnClickListener{
                rotate(i, images[i])
                checkSame()
                checkWin(selectedDiff, meter)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.saveTime(meter)
    }

    private fun rotate(id: Int, image: ImageView){
        val draw = viewModel.rotate(id)
        if(draw != null){
            image.setImageResource(draw)
        }
    }

    private fun checkSame(){
        if(viewModel.checkSame()) {
            images.forEach {
                it.isClickable = false
            }
        }
        Handler(Looper.getMainLooper()).postDelayed({
            updateUI()
            images.forEach {
                it.isClickable = true
            }
        }, 1000)
    }

    private fun checkWin(selectedDiff: String, meter: Chronometer){
        val difficulty: Int = if(selectedDiff == "Normal"){
            3
        } else{
            4
        }
        if(viewModel.checkWin(difficulty)){
            meter.stop()
            val intent = Intent(this, ResultsActivity::class.java)

            val time = (SystemClock.elapsedRealtime() - meter.base) * 0.001

            val points = 1000/(time+viewModel.movements)
            intent.putExtra("points", points.toInt())
            intent.putExtra("selectedDiff", selectedDiff)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(){
        for (i in images.indices){
            images[i].setImageResource(viewModel.imageState(i))
        }
        viewModel.setChrono(meter)
        movements.text = "Movements: ${viewModel.movements}"
    }
}