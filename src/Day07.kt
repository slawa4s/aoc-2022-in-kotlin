import java.io.IOException

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

    val inputGrouped = getInputGroupedByOperations(readInput("Day07"))

    fun part1(inputGrouped: List<List<String>>): Int = initializeFolderTree(inputGrouped).part1Answer()

    fun part2(inputGrouped: List<List<String>>): Int = initializeFolderTree(inputGrouped).part2Answer()
    
    println(part1(inputGrouped))
    println(part2(inputGrouped))
}

class FolderTreeImpl {
    var treeRoot: FolderNode = FolderNode(null)
    var currentRoot: FolderNode = treeRoot

    fun go(folderName: String) {
//        println("Go from ${currentRoot.name} to ${folderName}")
        currentRoot =
            if (folderName == "/") treeRoot
            else if (folderName == "..") currentRoot.dad ?: treeRoot
            else {
                if (folderName in currentRoot.folders) currentRoot.folders[folderName]!! else {
                    throw IOException("Didn't find ${folderName} in ${currentRoot.name}")
                }
            }
    }

    fun readDir(input: List<String>) = input.forEach {
        if (it.startsWith("dir")) currentRoot.appendFolder(it.split(" ")[1])
        else currentRoot.appendFile(it.split(" ")[1], Integer.parseInt(it.split(" ")[0]))
    }

    fun part1Answer(): Int = treeRoot.getAllNodesSize().sumOf { if (it <= maxDirectorySize) it else 0  }

    fun part2Answer(): Int {
        val sortedListOfNodes = treeRoot.getAllNodesSize().sorted()
        val directorySize = sortedListOfNodes.last()
        return sortedListOfNodes.first { diskSpace - directorySize + it >= unusedSpace }
    }
}


class FolderNode(
    val dad: FolderNode?,
    val name: String = "/",
    var fromFileNameToFileSize: MutableMap<String, Int> = mutableMapOf(),
    var folders: MutableMap<String, FolderNode> = mutableMapOf(),
) {
    val size: Int
        get() = fromFileNameToFileSize.values.sum() + folders.values.sumOf { it.size }

    fun appendFile(fileName: String, size: Int) {
        fromFileNameToFileSize[fileName] = size
    }

    fun appendFolder(folderName: String) {
        folders[folderName] = FolderNode(this, folderName)
    }

    private fun <T> concatenate(sets: List<Set<T>>): MutableSet<T> {
        val collection: MutableSet<T> = HashSet()
        for (set in sets) {
            collection.addAll(set)
        }
        return collection
    }

    fun getAllNodes(): Set<FolderNode> {
        val allKids = concatenate(folders.values.map { it.getAllNodes() })
        allKids.add(this)
        return allKids
    }

    fun getAllNodesSize(): List<Int> = getAllNodes().map { it.size }
}