import java.awt.Desktop
import java.io.File
import java.net.URL
import java.net.URLEncoder

fun main() {
    Day13()
}

class Day13 {

    init {
        part1()
//        part2()
    }

    var waitTime = 0

    private fun part1() {
        readFileAsLinesUsingUseLines("day13.txt").forEach {
            if (waitTime == 0) {
                waitTime = it.toInt()
            } else {
                it.split(",")
                    .filter { it != "x" }
                    .map { it.toInt() }
                    .map { Pair(waitTime + it - waitTime % it, it) }
                    .minBy { it.first }
                    ?.let {
                        println("${(it.first - waitTime) * it.second}")
                    }
            }
        }
    }

    private fun part2() {
        File("inputs/day13.txt").readLines()[1].split(",").withIndex().filter { it.value != "x" }
            .joinToString(" && ") { (i, v) -> "(x+$i) mod $v = 0" }.let { URLEncoder.encode(it) }
            .let { eq -> Desktop.getDesktop().browse(URL("https://www.wolframalpha.com/input/?i=$eq").toURI()) }
    }
}