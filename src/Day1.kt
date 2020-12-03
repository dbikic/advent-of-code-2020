import java.util.*
import kotlin.collections.HashSet

fun main() {
    Day1()
}

class Day1 {

    init {
        part1()
        part2()
    }

    private fun part1() {
        val set = HashSet<Int>()
        readFileAsLinesUsingUseLines("day1.txt").forEach {
            set.add(it.toInt())
        }
        set.forEach {
            if (set.contains(2020 - it)) {
                val result = it * (2020 - it)
                println("Part 1: $result")
                return
            }
        }
    }

    private fun part2() {
        val set = HashSet<Int>()
        val list = mutableListOf<Int>()
        readFileAsLinesUsingUseLines("day1.txt").forEach {
            val value = it.toInt()
            set.add(value)
            list.add(value)
        }
        val array = list.toIntArray()
        Arrays.sort(array)
        for (i in 0 until array.size - 1) {
            for (j in i until array.size) {
                if (set.contains(2020 - array[i] - array[j])) {
                    val result = (2020 - array[i] - array[j]) * array[i] * array[j]
                    println("Part 2: $result")
                    return
                }
            }
        }
    }
}