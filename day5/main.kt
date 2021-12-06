import java.io.File
import kotlin.math.sign

data class Point(val x: Int, val y: Int)

// https://adventofcode.com/2021/day/5
fun main() {
    val text = File("input.txt")
    val lines = text.readLines()
    val lineRegex = "(\\d+),(\\d+) -> (\\d+),(\\d+)".toRegex()
    val entries = lines.map {
        val (_, fromX, fromY, toX, toY) = lineRegex.matchEntire(it)!!.groupValues
        Point(fromX.toInt(), fromY.toInt()) to Point(toX.toInt(), toY.toInt())
    }
    firstPart(entries)
    secondPart(entries)
}

fun firstPart(entries: List<Pair<Point, Point>>) {
    val withoutDiagonals = entries.filter { (from, to) -> from.x == to.x || from.y == to.y }
    val xMax = withoutDiagonals.flatMap { it.toList() }.map { it.x }.reduce(::maxOf)
    val yMax = withoutDiagonals.flatMap { it.toList() }.map { it.y }.reduce(::maxOf)
    val board = Array(xMax + 1) { IntArray(yMax + 1) }
    for ((from, to) in withoutDiagonals) {
        if (from.x == to.x) { // vertical
            val x = from.x
            for (y in minOf(from.y, to.y)..maxOf(from.y, to.y)) board[x][y]++
        } else if (from.y == to.y) { // horizontal
            val y = from.y
            for (x in minOf(from.x, to.x)..maxOf(from.x, to.x)) board[x][y]++
        }
    }
    val overlaps = board.map { it.count { it >= 2 } }.sum()
    println(overlaps)
}

fun secondPart(entries: List<Pair<Point, Point>>) {
    val xMax = entries.flatMap { it.toList() }.map { it.x }.reduce(::maxOf)
    val yMax = entries.flatMap { it.toList() }.map { it.y }.reduce(::maxOf)
    val board = Array(xMax + 1) { IntArray(yMax + 1) }
    for ((from, to) in entries) {
        if (from.x == to.x) { // vertical
            val x = from.x
            for (y in minOf(from.y, to.y)..maxOf(from.y, to.y)) board[x][y]++
        } else if (from.y == to.y) { // horizontal
            val y = from.y
            for (x in minOf(from.x, to.x)..maxOf(from.x, to.x)) board[x][y]++
        } else {
            var x = from.x
            var y = from.y
            val stepX = (to.x - from.x).sign
            val stepY = (to.y - from.y).sign
            board[x][y]++
            while (x != to.x) {
                x += stepX
                y += stepY
                board[x][y]++
            }
        }
    }
    val overlaps = board.map { it.count { it >= 2 } }.sum()
    println(overlaps)
}