import kotlin.math.sign

class Coordinates(var x: Int, var y: Int) {
    fun plus(other: Coordinates) {
        x += other.x
        y += other.y
    }

    fun dist(other: Coordinates): Coordinates {
        return Coordinates(this.x - other.x, this.y - other.y)
    }
}

private val allMoves = mapOf(
    "U" to Coordinates(0, 1),
    "D" to Coordinates(0, -1),
    "L" to Coordinates(-1, 0),
    "R" to Coordinates(1, 0)
)

fun main() {
    fun isTouch(headCoordinates: Coordinates, tailCoordinates: Coordinates): Boolean {
        return (tailCoordinates.x in headCoordinates.x - 1 .. headCoordinates.x + 1) and
               (tailCoordinates.y in headCoordinates.y - 1 .. headCoordinates.y + 1)
    }

    fun tailFollow(headCoordinates: Coordinates, tailCoordinates: Coordinates) {
        if (!isTouch(headCoordinates, tailCoordinates)) {
            val dist = headCoordinates.dist(tailCoordinates)
            tailCoordinates.y += dist.y.sign
            tailCoordinates.x += dist.x.sign
        }
    }

    fun prepareInput(input: List<String>): List<Pair<Coordinates, Int>> = input.map {
        val split = it.split(" ")
        Pair(allMoves[split[0]]!!, Integer.parseInt(split[1]))
    }

    fun part1(input: List<Pair<Coordinates, Int>>): Int {
        val tailCoordinates = Coordinates(0, 0)
        val headCoordinates = Coordinates(0, 0)
        val savedCoords = mutableSetOf(Pair(0, 0))
        input.forEach { (move, num) ->
            for (i in 1..num) {
                headCoordinates.plus(move)
                tailFollow(headCoordinates, tailCoordinates)
                savedCoords.add(Pair(tailCoordinates.x, tailCoordinates.y))
            }
        }
        return savedCoords.size
    }

    fun part2(input: List<Pair<Coordinates, Int>>): Int {
        val rope = MutableList(10) { Coordinates(0, 0) }
        val savedCoords = mutableSetOf(Pair(0, 0))
        input.forEach { (move, num) ->
            for (i in 1..num) {
                rope[0].plus(move)
                for (ropeIndex in 0 until rope.lastIndex) {
                    tailFollow(rope[ropeIndex], rope[ropeIndex + 1])
                }
                savedCoords.add(Pair(rope.last().x, rope.last().y))
            }
        }
        return savedCoords.size
    }

    val preparedInput = prepareInput(readInput("Day09"))

    println(part1(preparedInput))
    println(part2(preparedInput))
}
