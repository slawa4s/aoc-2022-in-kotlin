fun main() {
    fun getColumn(matrix: List<List<Int>>, col: Int) = List(matrix.size) { matrix[it][col] }

    fun getRow(matrix: List<List<Int>>, row: Int) = matrix[row]

    fun isVisible(treeGrid: List<List<Int>>, i: Int, j: Int): Boolean {
        val element = treeGrid[i][j]
        return i == 0 || j == 0 || i == treeGrid.lastIndex || j == treeGrid.lastIndex ||
               getRow(treeGrid, i).slice(0 until j).max() < element ||
               getRow(treeGrid, i).slice(j + 1 until treeGrid.size).max() < element ||
               getColumn(treeGrid, j).slice(0 until i).max() < element ||
               getColumn(treeGrid, j).slice(i + 1 until treeGrid.size).max() < element
    }

    fun getScorePerLine(element: Int, line: List<Int>): Int {
        val getLessTrees = line.takeWhile { it < element }.count()
        return getLessTrees + if (getLessTrees == line.size) 0 else 1
    }

    fun getScenicScore(treeGrid: List<List<Int>>, i: Int, j: Int): Int {
        val element = treeGrid[i][j]
        return getScorePerLine(element, getRow(treeGrid, i).slice(0 until j).reversed()) *
               getScorePerLine(element, getRow(treeGrid, i).slice(j + 1 until treeGrid.size)) *
               getScorePerLine(element, getColumn(treeGrid, j).slice(0 until i).reversed()) *
               getScorePerLine(element, getColumn(treeGrid, j).slice(i + 1 until treeGrid.size))
    }

    fun getGridOFTrees(input: List<String>): List<List<Int>> = input
        .map { line -> line.map{ Integer.parseInt(it.toString()) } }

    val treeGrid = getGridOFTrees(readInput("Day08"))

    fun part1(treeGrid: List<List<Int>>): Int = treeGrid.mapIndexed { indexRow, line ->
        List(line.size) { indexCol ->
            if (isVisible(treeGrid, indexRow, indexCol)) 1 else 0
        }
    }.flatten().sum()

    fun part2(treeGrid: List<List<Int>>): Int = treeGrid.mapIndexed { indexRow, line ->
        List(line.size) { indexCol ->
            getScenicScore(treeGrid, indexRow, indexCol)
        }
    }.flatten().max()

    println(part1(treeGrid))
    println(part2(treeGrid))
}
