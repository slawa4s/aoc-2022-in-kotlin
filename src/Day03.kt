const val CHUNK_SIZE = 3

fun main() {
    fun setOfCharsToSumInt(set: Set<Char>) =
        set.sumOf {
            if (it.isUpperCase()) Character.getNumericValue(it) + 17
            else Character.getNumericValue(it) - 9
        }

    fun getChunkToInt(input: List<Set<Char>>): Int =
        setOfCharsToSumInt(input.reduce { first, second -> first.intersect(second) }.toSet())

    fun part1(input: List<String>): Int  = input.sumOf {
        getChunkToInt(listOf(it.take((it.length + 1) / 2).toSet(), it.takeLast((it.length + 1) / 2).toSet()))
    }

    fun part2(input: List<String>): Int  = input
        .map { it.toList() }
        .chunked(CHUNK_SIZE)
        .sumOf { getChunkToInt(it.map { item -> item.toSet() }) }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
