fun main() {
    fun getPairFromInput(arg: String): Pair<Int, Int> {
        val splitByTwo = arg.split('-')
        return Pair(Integer.parseInt(splitByTwo[0]), Integer.parseInt(splitByTwo[1]))
    }

    fun checkOneRangeInsideAnother(firstRange: Pair<Int, Int>, secondRange: Pair<Int, Int>): Boolean =
        (firstRange.first >= secondRange.first) and (firstRange.second <= secondRange.second) ||
        (secondRange.first >= firstRange.first) and (secondRange.second <= firstRange.second)

    fun checkOneRangeOverlapAnother(firstRange: Pair<Int, Int>, secondRange: Pair<Int, Int>): Boolean =
        (firstRange.first >= secondRange.first) and (firstRange.first <= secondRange.second) ||
        (firstRange.second >= secondRange.first) and (firstRange.second <= secondRange.second) ||
        checkOneRangeInsideAnother(firstRange, secondRange)

    fun part1(input: List<List<String>>): Int  = input
        .count {
            checkOneRangeInsideAnother(getPairFromInput(it[0]), getPairFromInput(it[1]))
        }

    fun part2(input: List<List<String>>): Int  = input
        .count {
            checkOneRangeOverlapAnother(getPairFromInput(it[0]), getPairFromInput(it[1]))
        }

    val input = readInput("Day04").map { it.split(",") }
    println(part1(input))
    println(part2(input))
}
