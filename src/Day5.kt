fun main() {
    Day5()
}

class Day5 {

    init {
        var maxSeatId = Int.MIN_VALUE
        val seatsSet = hashSetOf<Int>()
        readFileAsLinesUsingUseLines("day5.txt").forEach { line ->
            val seat = getSeat(line.toCharArray())
            val seatId = seat.first * 8 + seat.second
            seatsSet.add(seatId)
            maxSeatId = Math.max(maxSeatId, seatId)
        }
        println("Part 1 = $maxSeatId")
        for (i in (seatsSet.min()!! + 1) until maxSeatId) {
            if (!seatsSet.contains(i) && seatsSet.contains(i - 1) && seatsSet.contains(i + 1)) {
                println("Part 2 = $i")
            }
        }
    }

    private fun getSeat(input: CharArray): Pair<Int, Int> {
        var min = 0
        var max = 127
        for (i in 0..5) {
            val diff = (max - min + 1) / 2
            if (input[i] == 'F') {
                max -= diff
            } else {
                min += diff
            }
        }
        val row = if (input[6] == 'F') {
            min
        } else {
            max
        }
        min = 0
        max = 7
        for (i in 7..9) {
            val diff = (max - min + 1) / 2
            if (input[i] == 'L') {
                max -= diff
            } else {
                min += diff
            }
        }
        val column = if (input[9] == 'L') {
            min
        } else {
            max
        }
        return Pair(row, column)
    }
}
