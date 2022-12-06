const val PART_ONE_SEQUENCE_SIZE = 4
const val PART_TWO_SEQUENCE_SIZE = 14

fun main() {
    fun findIndexOfFirstVariousChars(stringToFind: String, length: Int) =
        stringToFind.toList().windowed(size = length, step = 1).indexOfFirst {
            sublist -> sublist.distinct().size == length
        } + length

    fun part1(input: List<String>): Int  = input.sumOf { findIndexOfFirstVariousChars(it, PART_ONE_SEQUENCE_SIZE) }

    fun part2(input: List<String>): Int  = input.sumOf { findIndexOfFirstVariousChars(it, PART_TWO_SEQUENCE_SIZE) }

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}