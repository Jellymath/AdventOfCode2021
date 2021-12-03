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
    val modes = countZero.zip(countOne) { zeroes, ones -> if (ones >= zeroes) '1' else '0' }
    val antiModes = countZero.zip(countOne) { zeroes, ones -> if (zeroes <= ones) '0' else '1' }
    val gammaRate = modes.binaryToInt()
    val epsilonRate = antiModes.binaryToInt()
    val powerConsumption = gammaRate * epsilonRate
    println(powerConsumption)
}

fun secondPart(lines: List<String>) {
    fun List<String>.filterMode(i: Int): List<String> {
        val mode = if (onesAt(i) >= zeroesAt(i)) '1' else '0'
        return filter { it[i] == mode }
    }
    fun List<String>.filterAntiMode(i: Int): List<String> {
        val antiMode = if (zeroesAt(i) <= onesAt(i)) '0' else '1'
        return filter { it[i] == antiMode }
    }
    val oxygenGeneratorRating =
            generateSequence(lines to -1) { (input, i) -> input.filterMode(i + 1) to i + 1 }
                    .map { it.first }
                    .first { it.size == 1 }
                    .single()
                    .toInt(2)
    val carbonDioxideScrubberRating =
            generateSequence(lines to -1) { (input, i) -> input.filterAntiMode(i + 1) to i + 1 }
                    .map { it.first }
                    .first { it.size == 1 }
                    .single()
                    .toInt(2)
    val lifeSupportRating = oxygenGeneratorRating * carbonDioxideScrubberRating
    println(lifeSupportRating)
}

private fun List<Char>.binaryToInt() = joinToString("").toInt(2)

private fun List<String>.zeroesAt(i: Int) = count { it[i] == '0' }

private fun List<String>.onesAt(i: Int) = count { it[i] == '1' }
