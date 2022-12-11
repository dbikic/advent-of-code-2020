fun main() {
    Day15()
}

class Day15 {

    init {
//        part1()
        part2()
    }

    private fun part1() {
        val list: MutableList<Int> =
            readFileAsLinesUsingUseLines("day15.txt").first().split(",").map { it.toInt() }.toMutableList()
        var n = list.size;
        while (n < 30000000) {
            val last = list.last()
            var spoken = -1
            for (i in n - 2 downTo 0) {
                if (list[i] == last) {
                    spoken = i
                    break
                }
            }
            if (spoken == -1) {
                list.add(0)
            } else {
                list.add(n - spoken - 1)
            }
            n++
        }
        println(list.last())
    }

    private fun part2() {
        val sample = readFileAsLinesUsingUseLines("day15.txt")
            .first()
            .split(",")
            .map { it.toInt() }
            .toMutableList()
        val map = hashMapOf<Int, Int>()
        for (i in sample.indices) map[sample[i]] = i

        var turns = sample.size + 1
        var last = 0
        while (turns < 30_000_000) {
            if (map[last] == null) {
                map[last] = turns - 1
                last = 0
            } else {
                val n = turns - 1 - map[last]!!
                map[last] = turns - 1
                last = n
            }
            turns++
        }
        println(last)
    }
}