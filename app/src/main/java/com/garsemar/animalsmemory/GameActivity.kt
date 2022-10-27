package com.garsemar.animalsmemory

import android.content.Intent
import android.os.*
import android.os.SystemClock
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class GameActivity : AppCompatActivity() {

    lateinit var viewModel: GameViewModel
    lateinit var images: MutableList<ImageView>
    lateinit var meter: Chronometer

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AnimalsMemory)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        val bundle: Bundle? = intent.extras
        val selectedDiff = bundle?.getString("selectedDiff")
        val hard = listOf<ImageView>(findViewById(R.id.imageView7), findViewById(R.id.imageView8))
        meter = findViewById(R.id.c_meter)

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

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

        for(i in images.indices){
            images[i].setOnClickListener{
                rotate(i, images[i])
                checkSame()
                checkWin(selectedDiff!!, meter)
                meter.start()
            }
        }

        updateUI()
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

            val time = 12

            val points = 1000/(time+5)
            intent.putExtra("points", points.toInt())
            intent.putExtra("selectedDiff", selectedDiff)
            startActivity(intent)
        }
    }

    private fun updateUI(){
        for (i in images.indices){
            images[i].setImageResource(viewModel.imageState(i))
        }
    }

    /*private fun game(selectedDiff: String?){
        val drawable = listOf(R.drawable.mono, R.drawable.capybara, R.drawable.foca, R.drawable.lemur)
        lateinit var map: MutableMap<ImageView, Int>
        val move = findViewById<TextView>(R.id.textView9)
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
        val selectedRight = mutableListOf<MutableList<Map.Entry<ImageView, Int>>>()
        val meter = findViewById<Chronometer>(R.id.c_meter)
        meter.start()
        var movements = 0
        map.forEach { it ->
            it.key.setOnClickListener { lis ->
                if(it.key.drawable.constantState == resources.getDrawable( R.drawable._1z_x7ojlvl).constantState){
                    it.key.setImageResource(it.value)
                    selected.add(it)
                    if(selected.size == 2){
                        movements += 1
                        move.text="Movements: $movements"
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
                            selectedRight.add(selected)
                            selected.clear()
                            right += 1
                        }
                    }
                    if(right == difficulty){
                        win(meter, movements, selectedDiff)
                    }
                }
            }
        }
    }*/
    private fun win(meter: Chronometer, movements: Int, selectedDiff: String?){
        val intent = Intent(this, ResultsActivity::class.java)
        meter.stop()

        val time = (SystemClock.elapsedRealtime() - meter.base) * 0.001

        val points = 1000/(time+movements)
        intent.putExtra("points", points.toInt())
        intent.putExtra("selectedDiff", selectedDiff)
        startActivity(intent)
    }
    private fun randomNums(size: Int) = List(size){it}.shuffled()
}