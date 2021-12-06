package day4

import java.io.File

typealias Board = List<List<Int>>

typealias Fill = Pair<Int, Int>

// https://adventofcode.com/2021/day/4
fun main() {
    val text = File("input.txt")
    val lines = text.readLines()
    val numbers = lines.first().split(',').map { it.toInt() }
    val withoutNumbers = lines.drop(1)
    val boardBlocks = withoutNumbers.chunked(6) // include empty line
    val boards =
            boardBlocks.map { boardBlock ->
                boardBlock.dropWhile { it.isBlank() }.map { rowBlock ->
                    rowBlock.split("\\s+".toRegex()).filter { it.isNotBlank() }.map { it.toInt() }
                }
            }

    check(boards.all { it.size == 5 && it.all { it.size == 5 } })
    check(numbers.distinct() == numbers)

    firstPart(numbers, boards)
    secondPart(numbers, boards)
}

fun firstPart(numbers: List<Int>, boards: List<Board>) {
    val boardWithFills = boards.map { it to mutableSetOf<Fill>() }
    for (number in numbers) {
        for ((board, fill) in boardWithFills) {
            val result = checkMarkResult(board, number)
            if (result != null) fill += result
        }
        for ((board, fill) in boardWithFills) {
            val result = checkWinResult(board, fill, number)
            if (result != null) {
                println(result)
                return
            }
        }
    }
}

fun secondPart(numbers: List<Int>, boards: List<Board>) {
    val fills = boards.map { mutableSetOf<Fill>() }
    var boardWins = mutableSetOf<Int>()
    for (number in numbers) {
        for ((i, board) in boards.withIndex()) {
            if (i in boardWins) continue
            val result = checkMarkResult(board, number)
            if (result != null) fills[i] += result
        }
        for ((i, board) in boards.withIndex()) {
            if (i in boardWins) continue
            val result = checkWinResult(board, fills[i], number)
            if (result != null) {
                boardWins += i
                if (boardWins.size == boards.size) {
                    println(result)
                    return
                }
            }
        }
    }
}

private val allFills = (0..4).flatMap { i -> (0..4).map { j -> i to j } }

private fun checkMarkResult(board: Board, number: Int): Fill? =
        allFills.find { (i, j) -> board[i][j] == number }

private fun checkWinResult(board: Board, fills: Set<Fill>, lastNumber: Int): Int? {
    val horizontalWin = fills.groupingBy { it.first }.eachCount().any { it.value == 5 }
    val verticalWin = fills.groupingBy { it.second }.eachCount().any { it.value == 5 }
    if (!horizontalWin && !verticalWin) return null
    val unmarked = allFills - fills
    val unmarkedValues = unmarked.map { (i, j) -> board[i][j] }
    val unmarkedSum = unmarkedValues.sum()
    return unmarkedSum * lastNumber
}
