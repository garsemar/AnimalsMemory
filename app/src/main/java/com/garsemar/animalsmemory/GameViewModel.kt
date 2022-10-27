package com.garsemar.animalsmemory

import android.widget.ImageView
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    var drawable = listOf(
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

    init {
        drawable = drawable.shuffled()
        println(drawable)
        for (i in drawable.indices) {
            cards.add(Cards(i, drawable[i]))
        }
    }

    fun rotate(id: Int): Int? {
        println(cards[id])
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
}