package com.garsemar.animalsmemory

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.View
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton

class  GameActivity : AppCompatActivity() {

    lateinit var viewModel: GameViewModel
    lateinit var images: MutableList<ImageView>
    lateinit var meter: Chronometer
    private lateinit var movements: TextView
    private lateinit var dialog: AlertDialog
    private var time: Long = 0

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AnimalsMemory)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        val bundle: Bundle? = intent.extras
        val selectedDiff = bundle?.getString("selectedDiff")
        val hard = listOf<ImageView>(findViewById(R.id.imageView7), findViewById(R.id.imageView8))
        val pause = findViewById<FloatingActionButton>(R.id.pause)
        meter = findViewById(R.id.c_meter)
        meter.base = SystemClock.elapsedRealtime()

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

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pause")
        builder.setMessage("""
            Game paused
        """.trimIndent()
        )
        builder.setPositiveButton("Play") {
                _, _ -> resume()
        }
        builder.setOnCancelListener {
            resume()
        }
        dialog = builder.create()

        pause.setOnClickListener {
            pause()
        }

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

        meter.start()
        updateUI()

        for(i in images.indices){
            images[i].setOnClickListener{
                rotate(i, images[i])
                checkSame()
                checkWin(selectedDiff, meter)
            }
        }
    }

    private fun resume(){
        println("--------------------------resume-----------------------------")
        images.forEach { it.isClickable = true }
        meter.base = meter.base + SystemClock.elapsedRealtime() - time
        meter.start()
        viewModel.paused = false
    }

    private fun pause(){
        println("--------------------------pause-----------------------------")
        images.forEach { it.isClickable = false }
        meter.stop()
        time = SystemClock.elapsedRealtime()
        dialog.show()
        viewModel.paused = true
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
        if(viewModel.paused){
            pause()
        }
    }
}