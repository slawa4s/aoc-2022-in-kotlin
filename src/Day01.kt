fun main() {
    fun getListOfCaloriesSum(input: List<String>): List<Int> {
        var separator = 0
        return input.map { if (it == "") 0 else it.toInt() }
            .groupBy {
                if (it == 0) separator += 1
                separator
            }
            .values
            .flatMap { listOf(it.sum()) }
    }

    fun part1(input: List<String>): Int  = getListOfCaloriesSum(input).max()

    fun part2(input: List<String>): Int  = getListOfCaloriesSum(input)
        .sorted()
        .takeLast(3)
        .sum()

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
