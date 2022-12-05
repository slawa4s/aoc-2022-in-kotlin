import java.util.Stack

fun main() {
    fun getInitialStacks(input: List<String>): List<ArrayDeque<Char>> {
        val filteredInput = input.map { it.toList().filterIndexed { index, _ -> index % 4 == 1 } }
        val stacks: List<ArrayDeque<Char>> = List(filteredInput[0].size) { ArrayDeque() }
        for (indexOfLine in filteredInput.lastIndex - 1 downTo 0) {
            for (indexOfStack in filteredInput[indexOfLine].indices) {
                if (filteredInput[indexOfLine][indexOfStack] != ' ') stacks[indexOfStack].add(filteredInput[indexOfLine][indexOfStack])
            }
        }
        return stacks
    }

    fun makeOneMoveOldVersion(howMany: Int, stackFromIndex: Int, stackToIndex: Int, stacks: List<ArrayDeque<Char>>) {
        for (i in 1..howMany) {
            stacks[stackToIndex].addLast(stacks[stackFromIndex].removeLastOrNull()!!)
        }
    }

    fun makeOneMoveNewVersion(howMany: Int, stackFromIndex: Int, stackToIndex: Int, stacks: List<ArrayDeque<Char>>) {
        val optionalStack: ArrayDeque<Char> = ArrayDeque()
        for (i in 1..howMany) {
            optionalStack.addLast(stacks[stackFromIndex].removeLastOrNull()!!)
        }
        for (i in 1..howMany) {
            stacks[stackToIndex].addLast(optionalStack.removeLastOrNull()!!)
        }
    }

    fun getAnswer(stacks: List<ArrayDeque<Char>>): String {
        val answer = mutableListOf<Char>()
        for (stack in stacks) {
            answer.add(stack.last())
        }
        return answer.joinToString(separator = "")
    }

    fun splitInput(realInput: List<String>): Pair<List<String>, List<String>> {
        var separator = 0
        val groupedInput = realInput.groupBy {
                if (it == "") separator += 1
                separator
            }
            .values.toList()
        return Pair(groupedInput[0], groupedInput[1].slice(1..groupedInput[1].lastIndex))
    }

    fun part1(stackInput: List<String>, moveInput: List<String>): String {
        val stacks: List<ArrayDeque<Char>> = getInitialStacks(stackInput)
        moveInput.forEach {
            val args = it.split(" ")
            makeOneMoveOldVersion(Integer.parseInt(args[1]), Integer.parseInt(args[3]) - 1, Integer.parseInt(args[5]) - 1, stacks)
        }
        return getAnswer(stacks)
    }

    fun part2(stackInput: List<String>, moveInput: List<String>): String {
        val stacks: List<ArrayDeque<Char>> = getInitialStacks(stackInput)
        moveInput.forEach {
            val args = it.split(" ")
            makeOneMoveNewVersion(Integer.parseInt(args[1]), Integer.parseInt(args[3]) - 1, Integer.parseInt(args[5]) - 1, stacks)
        }
        return getAnswer(stacks)
    }

    val (stackInput, moveInput) = splitInput(readInput("Day05"))
    println(part1(stackInput, moveInput))
    println(part2(stackInput, moveInput))
}
