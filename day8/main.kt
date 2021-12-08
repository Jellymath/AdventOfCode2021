package day8

import java.io.File

typealias ReferenceInput = List<String>

typealias Output = List<String>

// https://adventofcode.com/2021/day/8
fun main() {
    val lines = File("input.txt").readLines()
    val input =
            lines.map {
                val (referenceInput, output) = it.split(" | ")
                referenceInput.split(' ') to output.trim().split(' ')
            }
    firstPart(input)
    secondPart(input)
}

fun firstPart(input: List<Pair<ReferenceInput, Output>>) {
    val ones = input.map { it.second.count { it.length == 2 } }.sum()
    val fours = input.map { it.second.count { it.length == 4 } }.sum()
    val sevens = input.map { it.second.count { it.length == 3 } }.sum()
    val eights = input.map { it.second.count { it.length == 7 } }.sum()
    println(ones + fours + sevens + eights)
}

fun secondPart(input: List<Pair<ReferenceInput, Output>>) {
    val deducted = input.map { (referenceInput, output) -> 
        val mapping = deduct(referenceInput)
        val (first, second, third, fourth) = output.map { mapping.getValue(it.sorted()) }
        first * 1000 + second * 100 + third * 10 + fourth
    }
    println(deducted.sum())
}


private fun deduct(input: ReferenceInput): Map<String, Int> {
    @Suppress("NAME_SHADOWING")
    val input = input.map { it.sorted() }
    val frequencies = input.flatMap { it.toSet() }.groupingBy { it }.eachCount()
    fun digitByLetters(vararg letters: Char) = letters.sorted().joinToString("")
    fun digitByLength(length: Int) = input.single { it.length == length }
    fun letterByFrequency(frequency: Int) = frequencies.entries.first { (_, v) -> v == frequency }.key

    val one = digitByLength(2)
    val four = digitByLength(4)
    val seven = digitByLength(3)
    val eight = digitByLength(7)

    val topMid = (seven.toSet() - one.toSet()).single()
    val bottomRight = letterByFrequency(9)
    val bottomLeft = letterByFrequency(4)
    val topLeft = letterByFrequency(6)
    val midMid = (four.toSet() - one.toSet() - topLeft).single()
    val bottomMid = frequencies.entries.single { (k, v) -> v == 7 && k != midMid }.key
    val topRight = (one.toSet() - bottomRight).single()

    val two = digitByLetters(topMid, topRight, midMid, bottomLeft, bottomMid)
    val three = digitByLetters(topMid, midMid, bottomMid, topRight, bottomRight)
    val five = digitByLetters(topMid, topLeft, midMid, bottomRight, bottomMid)
    val six = digitByLetters(topMid, topLeft, midMid, bottomLeft, bottomMid, bottomRight)
    val nine = digitByLetters(topMid, topLeft, topRight, midMid, bottomRight, bottomMid)
    val zero = digitByLetters(topMid, bottomMid, topLeft, bottomLeft, topRight, bottomRight)

    return mapOf(one to 1, two to 2, three to 3, four to 4, five to 5, six to 6, seven to 7, eight to 8, nine to 9, zero to 0)
}

private fun String.sorted() = toList().sorted().joinToString("")