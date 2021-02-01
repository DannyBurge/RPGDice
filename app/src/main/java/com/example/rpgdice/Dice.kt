package com.example.rpgdice

import kotlin.random.Random

class Dice(private val type: Int = 4) {

    val sidesCount = type

    fun roll(): Int {
        return Random.nextInt(from = 1, until = type + 1)
    }
}