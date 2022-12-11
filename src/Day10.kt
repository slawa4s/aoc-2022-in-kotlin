data class Command(val command: String, val value: Int?)

const val ADDX = "addx"
const val NOOP = "noop"

val mapOfTimeOfCommand = mapOf(
    ADDX to 2,
    NOOP to 1
)

fun main() {
    fun isCycleCounted(cycle: Int): Boolean = (cycle - 20) % 40 == 0

    fun prepareInput(input: List<String>): List<Command> = input.map {
        val split = it.split(" ")
        Command(split[0], if (split.size > 1) Integer.parseInt(split[1]) else null)
    }

    fun part1(commands: List<Command>): Int {
        var sumOfCountedCycles = 0
        var cycle = 0
        var x = 1
        for (command in commands) {
            val needCycles = mapOfTimeOfCommand[command.command]!!
            for (executionTime in 0 until needCycles) {
                cycle += 1
                if (isCycleCounted(cycle)) sumOfCountedCycles += x * cycle
            }
            if (command.command == ADDX) x += command.value!!
        }
        return sumOfCountedCycles
    }

    val preparedInput = prepareInput(readInput("Day10"))

    println(part1(preparedInput))
}
