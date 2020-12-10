fun main() {
    Day10()
}

class Day10 {

    init {
        val adapters = mutableListOf<Int>()
        adapters.add(0)
        readFileAsLinesUsingUseLines("day10.txt").forEach { line ->
            adapters.add(line.toInt())
        }
        adapters.sort()

        val part1Solution = adapters
            .asSequence()
            .zipWithNext()
            .map { it.second - it.first }
            .groupingBy { it }
            .eachCount()
            .run {
                getOrDefault(1, 1) * getOrDefault(3, 1)
            }
        println("Part 1 solution: $part1Solution")

        val pathsByAdapter: MutableMap<Int, Long> = mutableMapOf(0 to 1L)
        adapters.drop(1).forEach { adapter ->
            pathsByAdapter[adapter] = (1..3).map { lookBack ->
                pathsByAdapter.getOrDefault(adapter - lookBack, 0)
            }.sum()
        }

        val part2Solution = pathsByAdapter.getValue(adapters.last())
        println("Part 2 solution: $part2Solution")
    }
}