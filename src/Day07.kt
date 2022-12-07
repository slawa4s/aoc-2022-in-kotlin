const val diskSpace = 70000000
const val unusedSpace = 30000000
const val maxDirectorySize = 100000

fun main() {
    fun getInputGroupedByOperations(input: List<String>): List<List<String>> {
        var indexOperation = 0
        return input.groupBy {
            if (it.startsWith("\$ cd") or it.startsWith("\$ ls")) indexOperation += 1
            indexOperation
        }.values.toList()
    }

    fun initializeFolderTree(inputGrouped: List<List<String>>): FolderTreeImpl {
        val folderTree = FolderTreeImpl()
        inputGrouped.forEach {
            if (it.first().startsWith("\$ cd")) folderTree.go(it.first().split(" ")[2])
            if (it.first().startsWith("\$ ls")) folderTree.readDir(it.subList(1, it.size))
        }
        return folderTree
    }

    val initializedTree = initializeFolderTree(getInputGroupedByOperations(readInput("Day07")))

    println(initializedTree.part1Answer())
    println(initializedTree.part2Answer())
}

class FolderTreeImpl {
    private var treeRoot: FolderNode = FolderNode(null)
    private var currentRoot: FolderNode = treeRoot

    fun go(folderName: String) {
        currentRoot = when (folderName) {
            "/" -> treeRoot
            ".." -> currentRoot.dad ?: treeRoot
            else -> currentRoot.getSubFolderByName(folderName)
        }
    }

    fun readDir(input: List<String>) = input.forEach {
        if (it.startsWith("dir")) currentRoot.appendFolder(it.split(" ")[1])
        else currentRoot.appendFile(it.split(" ")[1], Integer.parseInt(it.split(" ")[0]))
    }

    fun part1Answer(): Int = treeRoot.getAllNodesSize().sumOf { if (it <= maxDirectorySize) it else 0  }

    fun part2Answer(): Int {
        val sortedListOfNodes = treeRoot.getAllNodesSize().sorted()
        return sortedListOfNodes.first { diskSpace - sortedListOfNodes.last() + it >= unusedSpace }
    }
}


class FolderNode(
    val dad: FolderNode?,
    private var fromFileNameToFileSize: MutableMap<String, Int> = mutableMapOf(),
    private var folders: MutableMap<String, FolderNode> = mutableMapOf(),
) {
    private val sizeOfFiles: Int
        get() = fromFileNameToFileSize.values.sum()

    fun appendFile(fileName: String, size: Int) {
        fromFileNameToFileSize[fileName] = size
    }

    fun appendFolder(folderName: String) {
        folders[folderName] = FolderNode(this)
    }

    fun getSubFolderByName(folderName: String): FolderNode = this.folders[folderName]!!

    private fun getAllNodesSizeFold(): MutableList<Pair<Int, Int>> {
        val allKids = folders.values.map { it.getAllNodesSizeFold() }.flatten().toMutableList()
        allKids.add(Pair(allKids.sumOf { it.second } + sizeOfFiles, sizeOfFiles))
        return allKids
    }

    fun getAllNodesSize(): List<Int> = getAllNodesSizeFold().map { it.first }
}
