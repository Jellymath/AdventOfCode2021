package day6

import java.io.File

// https://adventofcode.com/2021/day/6
fun main() {
    val text = File("input.txt").readText()
    val lanternfishTimers = text.split(',').map { it.toInt() }
    firstPart(lanternfishTimers)
    secondPart(lanternfishTimers)
}

fun firstPart(lanternfishTimers: List<Int>) {
    // note: for day 80 it is fine to use state that accumulate fish count in Int, but as solutions otherwise are exactly the same I decided to reuse code anyway
    val lanternfishCount = lanternfishCountAt(lanternfishTimers, day = 80)
    println(lanternfishCount)
}

fun secondPart(lanternfishTimers: List<Int>) {
    val lanternfishCount = lanternfishCountAt(lanternfishTimers, day = 256)
    println(lanternfishCount)
}

private fun daySequence(lanternfishTimers: List<Int>): Sequence<List<Long>> {
    val initialState = List(9) { i -> lanternfishTimers.count { it == i }.toLong() }
    val daySequence =
            generateSequence(initialState) { current ->
                List(9) { i ->
                    when (i) {
                        8 -> current[0]
                        6 -> current[7] + current[0]
                        else -> current[i + 1]
                    }
                }
            }
    return daySequence.drop(1) // drop day 0
}

private fun lanternfishCountAt(initialLanternfishTimers: List<Int>, day: Int): Long {
    val daySequence = daySequence(initialLanternfishTimers)
    return daySequence.take(day).last().sum()
}
