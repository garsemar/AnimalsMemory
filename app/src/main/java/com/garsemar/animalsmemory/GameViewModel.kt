package com.garsemar.animalsmemory

import android.widget.Chronometer
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    var drawable = mutableListOf(
        R.drawable.mono,
        R.drawable.capybara,
        R.drawable.foca,
        R.drawable.mono,
        R.drawable.capybara,
        R.drawable.foca
    )

    var cards = mutableListOf<Cards>()
    var rotated = mutableListOf<Int>()
    var win = mutableListOf<List<Int>>()
    var time: Long = 0
    var movements = 0

    fun init(selectedDiff: String){
        if(selectedDiff == "Hard"){
            drawable.add(R.drawable.lemur)
            drawable.add(R.drawable.lemur)
        }
        drawable = drawable.shuffled().toMutableList()
        for (i in drawable.indices) {
            cards.add(Cards(i, drawable[i]))
        }
    }

    fun rotate(id: Int): Int? {
        if (!cards[id].rotated){
            cards[id].rotated = true
            rotated.add(id)
            return cards[id].drawable
        }
        return null
    }

    fun imageState(id: Int): Int{
        if(cards[id].rotated){
            return cards[id].drawable
        }
        return R.drawable._1z_x7ojlvl
    }

    fun checkSame(): Boolean {
        if(rotated.size == 2){
            movements += 1
            if(cards[rotated[0]].drawable == cards[rotated[1]].drawable){
                win.add(rotated)
            }
            else{
                rotated.forEach {
                    cards[it].rotated = false
                }
                rotated.clear()
                return true
            }
            rotated.clear()
        }
        return false
    }

    fun checkWin(difficulty: Int): Boolean {
        if(win.size == difficulty){
            return true
        }
        return false
    }

    fun saveTime(meter: Chronometer){
        time = meter.base
    }

    fun setChrono(meter: Chronometer){
        if(time.toInt() != 0){
            meter.base = time
        }
    }
}