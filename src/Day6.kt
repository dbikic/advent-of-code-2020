fun main() {
    Day6()
}

class Day6 {

    init {
        var set = hashSetOf<Char>()
        var part1Result = 0
        readFileAsLinesUsingUseLines("day6.txt").forEach { line ->
            if (line.isBlank()) {
                part1Result += set.size
                set = hashSetOf()
            } else {
                line.toCharArray().forEach {
                    set.add(it)
                }
            }
        }
        part1Result += set.size
        println("Part 1: $part1Result")

        var part2Result = 0
        var setsArray = mutableListOf<HashSet<Char>>()
        readFileAsLinesUsingUseLines("day6.txt").forEach { line ->
            if (line.isBlank()) {
                part2Result += getUniqueAnswersCount(setsArray)
                setsArray = mutableListOf()
            } else {
                set = hashSetOf()
                line.toCharArray().forEach {
                    set.add(it)
                }
                setsArray.add(set)
            }
        }
        part2Result += getUniqueAnswersCount(setsArray)
        println("Part 2: $part2Result")
    }

    private fun getUniqueAnswersCount(setsArray: MutableList<HashSet<Char>>): Int {
        var intersect = setsArray.first()
        setsArray.forEach {
            intersect = intersect.intersect(it) as HashSet<Char>
        }
        return intersect.size
    }
}