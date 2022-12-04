import kotlin.math.absoluteValue

fun main() {
    Day12()
}

class Day12 {
    enum class Action {
        N, S, E, W, L, R, F;

        companion object {
            fun fromChar(char: Char): Action {
                return when (char) {
                    'N' -> N
                    'S' -> S
                    'E' -> E
                    'W' -> W
                    'L' -> L
                    'R' -> R
                    else -> F
                }
            }
        }
    }

    enum class Direction { N, E, S, W }

    init {
//        part1()
        part2()
    }

    private fun part1() {
        var position = Pair(0, 0)
        var direction = Direction.E
        readFileAsLinesUsingUseLines("day12.txt").forEach {
            val instruction = parseLine(it)
            when (instruction.first) {
                Action.N -> position = Pair(position.first, position.second + instruction.second)
                Action.S -> position = Pair(position.first, position.second - instruction.second)
                Action.E -> position = Pair(position.first + instruction.second, position.second)
                Action.W -> position = Pair(position.first - instruction.second, position.second)
                Action.L -> direction = Direction.values()[(direction.ordinal + 3 * (instruction.second / 90)) % 4]
                Action.R -> direction = Direction.values()[(direction.ordinal + 1 * (instruction.second / 90)) % 4]
                Action.F -> {
                    position = when (direction) {
                        Direction.N -> Pair(position.first, position.second + instruction.second)
                        Direction.S -> Pair(position.first, position.second - instruction.second)
                        Direction.E -> Pair(position.first + instruction.second, position.second)
                        Direction.W -> Pair(position.first - instruction.second, position.second)
                    }
                }
            }
        }
        println("Manhattan distance: ${position.first.absoluteValue + position.second.absoluteValue}")
    }

    private fun part2() {
        var position = Pair(0, 0)
        var waypoint = Pair(10, 1)
        readFileAsLinesUsingUseLines("day12.txt").forEach {
            val instruction = parseLine(it)
            when (instruction.first) {
                Action.N -> waypoint = Pair(waypoint.first, waypoint.second + instruction.second)
                Action.S -> waypoint = Pair(waypoint.first, waypoint.second - instruction.second)
                Action.E -> waypoint = Pair(waypoint.first + instruction.second, waypoint.second)
                Action.W -> waypoint = Pair(waypoint.first - instruction.second, waypoint.second)
                Action.L -> repeat(instruction.second / 90) { waypoint = Pair(-waypoint.second, waypoint.first) }
                Action.R -> repeat(instruction.second / 90) { waypoint = Pair(waypoint.second, -waypoint.first) }
                Action.F -> position = Pair(
                    position.first + instruction.second * waypoint.first,
                    position.second + instruction.second * waypoint.second
                )
            }
        }
        println("Manhattan distance: ${position.first.absoluteValue + position.second.absoluteValue}")
    }

    private fun parseLine(line: String): Pair<Action, Int> {
        return Pair(
            Action.fromChar(line.first()), line.substring(1, line.length).toInt()
        )
    }
}