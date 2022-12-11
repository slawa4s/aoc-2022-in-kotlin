import java.io.IOException

class Monkey(
    var items: MutableList<Int>,
    private val operation: List<String>,
    private val derive: Int,
    private val ifTrueThrow: Int,
    private val ifFalseThrow: Int,
) {
    private var inspectedItems = 0

    fun getInspectedItems() = inspectedItems

    private fun makeOperation(item: Int): Int {
        val firstArgument = if (operation[0] == "old") item else Integer.parseInt(operation[0])
        val secondArgument = if (operation[2] == "old") item else Integer.parseInt(operation[2])
        return when (operation[1]) {
            "-" -> firstArgument - secondArgument
            "+" -> firstArgument + secondArgument
            "*" -> firstArgument * secondArgument
            "/" -> firstArgument / secondArgument
            else -> throw IOException("Unknown operation: ${operation[1]}")
        }
    }

    private fun getMonkeyIdToThrow(item: Int, lessWorriedAfterInspection: Boolean): Pair<Int, Int> {
        val operationResult = if (lessWorriedAfterInspection) makeOperation(item) / 3 else makeOperation(item)
        return Pair(if (operationResult % derive == 0) ifTrueThrow else ifFalseThrow, operationResult)
    }

    // monkey index and value
    fun inspectAllItemsAndClear(lessWorriedAfterInspection: Boolean): List<Pair<Int, Int>> {
        inspectedItems += items.size
        val answer = items.map { getMonkeyIdToThrow(it, lessWorriedAfterInspection) }
        items = mutableListOf()
        return answer
    }

    fun addItem(item: Int) {
        items.add(item)
    }
}

fun main() {
    fun prepareInput(input: List<String>): List<Monkey> =
        input.chunked(7).map {
            val items = it[1]
                .substringAfter("Starting items: ")
                .split(", ")
                .map { num -> num.toInt() }
                .toMutableList()
            val operation = it[2]
                .substringAfter("Operation: new = ")
                .split(" ")
            val derive = Integer.parseInt(it[3].substringAfter("Test: divisible by "))
            val ifTrueThrow = Integer.parseInt(it[4].substringAfter("If true: throw to monkey "))
            val ifFalseThrow = Integer.parseInt(it[5].substringAfter("If false: throw to monkey "))
            Monkey(items, operation, derive, ifTrueThrow, ifFalseThrow)
        }

    fun part1(monkeys: List<Monkey>): Int {
        repeat(20) {
            monkeys.forEach {
                val listOfThrows = it.inspectAllItemsAndClear(lessWorriedAfterInspection = true)
                for ((index, value) in listOfThrows) {
                    monkeys[index].addItem(value)
                }
            }
        }
        val twoBiggest = monkeys.map { it.getInspectedItems() }.sorted().takeLast(2)
        return twoBiggest[1] * twoBiggest[0]
    }

    fun part2(monkeys: List<Monkey>): Int {
        repeat(21) {
            monkeys.forEach {
                val listOfThrows = it.inspectAllItemsAndClear(lessWorriedAfterInspection = false    )
                for ((index, value) in listOfThrows) {
                    monkeys[index].addItem(value)
                }
            }
            println("Iteration ${it + 1}")
            for (monkeyIndex in monkeys.indices) {
                println("Monkey $monkeyIndex: ${monkeys[monkeyIndex].items} and inspected: ${monkeys[monkeyIndex].getInspectedItems()}")
            }
        }
        val twoBiggest = monkeys.map { it.getInspectedItems() }.sorted().takeLast(2)
        return twoBiggest[1] * twoBiggest[0]
    }

    println(part1(prepareInput(readInput("Day11"))))
    println(part2(prepareInput(readInput("Day11"))))
}
