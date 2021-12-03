import java.io.File

// https://adventofcode.com/2021/day/3
fun main() {
    val text = File("input.txt")
    val lines = text.readLines()
    firstPart(lines)
    secondPart(lines)
}

fun firstPart(lines: List<String>) {
    val indices = lines.first().indices
    val countZero = indices.map(lines::zeroesAt)
    val countOne = indices.map(lines::onesAt)
    val modes = countZero.zip(countOne, ::mode)
    val antiModes = countZero.zip(countOne, ::antiMode)
    val gammaRate = modes.binaryToInt()
    val epsilonRate = antiModes.binaryToInt()
    val powerConsumption = gammaRate * epsilonRate
    println(powerConsumption)
}

fun secondPart(lines: List<String>) {
    fun List<String>.filterMode(i: Int): List<String> {
        val mode = mode(zeroesAt(i), onesAt(i))
        return filter { it[i] == mode }
    }
    fun List<String>.filterAntiMode(i: Int): List<String> {
        val antiMode = antiMode(zeroesAt(i), onesAt(i))
        return filter { it[i] == antiMode }
    }
    fun trial(filterFun: List<String>.(Int) -> List<String>) =
            generateSequence(lines to -1) { (input, i) -> input.filterFun(i + 1) to i + 1 }
                    .map { it.first }
                    .first { it.size == 1 }
                    .single()
                    .toInt(2)
    val oxygenGeneratorRating = trial(List<String>::filterMode)
    val carbonDioxideScrubberRating = trial(List<String>::filterAntiMode)
    val lifeSupportRating = oxygenGeneratorRating * carbonDioxideScrubberRating
    println(lifeSupportRating)
}

private fun List<Char>.binaryToInt() = joinToString("").toInt(2)

private fun List<String>.zeroesAt(i: Int) = count { it[i] == '0' }

private fun List<String>.onesAt(i: Int) = count { it[i] == '1' }

private fun mode(zeroes: Int, ones: Int) = if (ones >= zeroes) '1' else '0'

private fun antiMode(zeroes: Int, ones: Int) = if (zeroes <= ones) '0' else '1'
