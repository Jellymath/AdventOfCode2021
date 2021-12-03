import java.io.File

enum class Direction {
    forward,
    down,
    up
}

data class Command(val direction: Direction, val units: Int)

fun command(input: String): Command {
    val (directionString, unitsString) = input.split(' ')
    return Command(Direction.valueOf(directionString), unitsString.toInt())
}

// https://adventofcode.com/2021/day/2
fun main() {
    val text = File("input.txt")
    val commands = text.readLines().map(::command)
    firstPart(commands)
    secondPart(commands)
}

fun firstPart(commands: List<Command>) {
    data class Total(val horizontal: Int = 0, val depth: Int = 0)

    val result =
            commands.fold(Total()) { acc, (direction, units) ->
                when (direction) {
                    Direction.forward -> acc.copy(horizontal = acc.horizontal + units)
                    Direction.down -> acc.copy(depth = acc.depth + units)
                    Direction.up -> acc.copy(depth = acc.depth - units)
                }
            }
    println(result.horizontal * result.depth)
}

fun secondPart(commands: List<Command>) {
    data class Total(val horizontal: Int = 0, val depth: Int = 0, val aim: Int = 0)
    val result =
            commands.fold(Total()) { acc, (direction, units) ->
                when (direction) {
                    Direction.forward ->
                            acc.copy(
                                    horizontal = acc.horizontal + units,
                                    depth = acc.depth + (acc.aim * units)
                            )
                    Direction.down -> acc.copy(aim = acc.aim + units)
                    Direction.up -> acc.copy(aim = acc.aim - units)
                }
            }
    println(result.horizontal * result.depth)
}
