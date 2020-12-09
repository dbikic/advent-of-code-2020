fun main() {
    Day9()
}

class Day9 {

    private val preambleSize = 25

    init {
        var i = 0
        val numbers = mutableListOf<Long>()
        var part1Solution: Long = 0
        readFileAsLinesUsingUseLines("day9.txt").forEach { line ->
            i++
            numbers.add(line.toLong())
            if (i > preambleSize) {
                val startIndex = numbers.size - preambleSize - 1
                val endIndex = numbers.size - 1
                val isValid = isNumberValid(numbers.last(), numbers.subList(startIndex, endIndex))
                if (!isValid) {
                    part1Solution = numbers.last()
                }
            }
        }
        println("Part 1: $part1Solution")
        var part2Solution: Long = 0
        val sumSet = mutableListOf<Long>()
        i = 0
        var startIndex = 0
        while (i < numbers.size) {
            sumSet.add(numbers[i])
            if (sumSet.size == 1) {
                startIndex = i
            }
            i++
            val sum = sumSet.sum()
            if (sumSet.size > 1 && sum == part1Solution) {
                sumSet.sort()
                part2Solution = sumSet.first() + sumSet.last()
            } else if (sum > part1Solution) {
                sumSet.clear()
                i = startIndex + 1
            }
        }
        println("Part 2: $part2Solution")
    }

    private fun isNumberValid(number: Long, previousNumbers: List<Long>): Boolean {
        val set = hashSetOf<Long>()
        previousNumbers.forEach {
            set.add(it)
        }
        previousNumbers.forEach {
            if (set.contains(number - it) && (number - it) != it) {
                return true
            }
        }
        return false
    }
}