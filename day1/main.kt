import java.io.File

// https://adventofcode.com/2021/day/1
fun main() {
    val text = File("input.txt")
    val depths = text.readLines().map { it.toInt() }
    firstPart(depths)
    secondPart(depths)
}

fun firstPart(depths: List<Int>) {
    val changes = depths.zipWithNext()
    val increaseCount = changes.count { (before, after) -> after > before }
    println(increaseCount)
}

fun secondPart(depths: List<Int>) {
    val threeSums = depths.windowed(3) { it.sum() }
    val changes = threeSums.zipWithNext()
    val increaseCount = changes.count { (before, after) -> after > before }
    println(increaseCount)
}