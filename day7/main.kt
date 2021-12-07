package day7

import java.io.File
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor

// https://adventofcode.com/2021/day/7
fun main() {
    val text = File("input.txt").readText()
    val submarinePositions = text.split(',').map { it.toInt() }
    // note: actually just checking every single position between lowest and highest submarine position works fast enough for this input so not that much need to guess why median/mean are working. Did median/mean approaches anyway :shrug:
    firstPart(submarinePositions)
    secondPart(submarinePositions)
}

fun firstPart(submarinePositions: List<Int>) {
    val medianPosition = submarinePositions.sorted()[submarinePositions.size / 2]
    val fuels = submarinePositions.map { abs(it - medianPosition) }
    val totalFuel = fuels.sum()
    println(totalFuel)
}

fun secondPart(submarinePositions: List<Int>) {
    val meanPosition = (submarinePositions.sum().toDouble() / submarinePositions.size)
    val meanLow = meanPosition.let(::floor).toInt()
    val meanHigh = meanPosition.let(::ceil).toInt()
    fun fuel(from: Int, to: Int) = (abs(from - to) + 1) * abs(from - to) / 2

    val fuelLower = submarinePositions.map { fuel(meanLow, it) }
    val fuelHigher = submarinePositions.map { fuel(meanHigh, it) }

    val totalFuelLow = fuelLower.sum()
    val totalFuelHigh = fuelHigher.sum()
    println(minOf(totalFuelLow, totalFuelHigh))
}