import Day8.Instruction.*

fun main() {
    Day8()
}

class Day8 {

    sealed class Instruction(val const: Int) {
        data class Jump(val steps: Int) : Instruction(steps)
        data class Accumulator(val value: Int) : Instruction(value)
        data class NoOperation(val value: Int) : Instruction(value)
    }

    private val operations = mutableListOf<Instruction>()
    private var visitedLinesHistory = mutableListOf<Int>()
    private var accumulator = 0

    init {
        readFileAsLinesUsingUseLines("day8.txt").forEach { line ->
            operations.add(
                when {
                    line.contains("nop") -> NoOperation(getValueFromInput(line))
                    line.contains("jmp") -> Jump(getValueFromInput(line))
                    else -> Accumulator(getValueFromInput(line))
                }
            )
        }
        var i = 0
        while (true) {
            if (visitedLinesHistory.isEmpty()) {
                iterateInstructions()
                i = visitedLinesHistory.last()
            } else {
                val lineToChange = visitedLinesHistory[i]
                changeOperation(lineToChange)
                visitedLinesHistory = mutableListOf()
                if (iterateInstructions()) {
                    println("Part 2: $accumulator")
                    break
                }
                i--
            }
        }
    }

    private fun iterateInstructions(): Boolean {
        accumulator = 0
        var i = 0
        val visitedLines = hashSetOf<Int>()
        while (i < operations.size) {
            if (visitedLines.contains(i)) {
                break
            }
            visitedLinesHistory.add(i)
            visitedLines.add(i)
            val currentOperation = operations[i]
            when (currentOperation) {
                is Jump -> i += currentOperation.steps
                is Accumulator -> {
                    accumulator += currentOperation.value
                    i++
                }
                is NoOperation -> i++
            }
        }
        println("Part 1: $accumulator -> visited: ${visitedLinesHistory.size}")
        return i >= operations.size
    }

    private lateinit var tempInstruction: Instruction

    private fun changeOperation(line: Int) {
        if (operations[line] is Jump) {
            tempInstruction = NoOperation(operations[line].const)
            operations[line] = tempInstruction
        } else if (operations[line] is NoOperation) {
            tempInstruction = Jump(operations[line].const)
            operations[line] = tempInstruction
        }
    }

    private fun getValueFromInput(input: String): Int {
        return if (input.contains("+")) {
            input.substring(input.indexOf("+") + 1).toInt()
        } else {
            input.substring(input.indexOf("-") + 1).toInt() * -1
        }
    }
}