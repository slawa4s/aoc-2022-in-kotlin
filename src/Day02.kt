enum class ResultOfGame(val score: Int) {
    DEFEAT(0),
    DRAW(3),
    WIN(6)
}

val mapOfScores = hashMapOf(
    'X' to 1,
    'Y' to 2,
    'Z' to 3
)

val mapOfPlayersDraw = hashMapOf(
    'X' to 'A',
    'Y' to 'B',
    'Z' to 'C'
)

val mapOfPlayersLose = hashMapOf(
    'X' to 'B',
    'Y' to 'C',
    'Z' to 'A'
)

val mapOfPlayersWin = hashMapOf(
    'X' to 'C',
    'Y' to 'A',
    'Z' to 'B'
)

val mapOfGameEnding = hashMapOf(
    'X' to ResultOfGame.DEFEAT,
    'Y' to ResultOfGame.DRAW,
    'Z' to ResultOfGame.WIN
)

fun main() {
    fun getRoundResult(first: Char, second: Char): ResultOfGame =
        when (first) {
            mapOfPlayersDraw[second] -> ResultOfGame.DRAW
            mapOfPlayersWin[second] -> ResultOfGame.WIN
            else -> ResultOfGame.DEFEAT
        }

    fun getPredictByResult(first: Char, gameResult: ResultOfGame): Int =
        when(gameResult) {
            ResultOfGame.DEFEAT -> mapOfScores[mapOfPlayersLose.entries.associate{ (k, v)-> v to k }[first]!!]!!
            ResultOfGame.DRAW -> mapOfScores[mapOfPlayersDraw.entries.associate{ (k, v)-> v to k }[first]!!]!!
            else -> mapOfScores[mapOfPlayersWin.entries.associate{ (k, v)-> v to k }[first]!!]!!
        }


    fun getRoundScorePart1(first: Char, second: Char): Int = mapOfScores[second]!! + getRoundResult(first, second).score

    fun getRoundScorePart2(first: Char, second: Char): Int = mapOfGameEnding[second]!!.score +
                                                             getPredictByResult(first, mapOfGameEnding[second]!!)

    fun part1(input: List<String>): Int = input.sumOf { getRoundScorePart1(it[0], it[2]) }

    fun part2(input: List<String>): Int = input.sumOf { getRoundScorePart2(it[0], it[2]) }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}