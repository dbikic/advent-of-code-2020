fun main() {
    Day11()
}

class Day11 {

    private var seats = mutableListOf<CharArray>()
    private val occupiedChar = '#'
    private val emptyChar = 'L'
    private val floorChar = '.'

    init {
        //part1()
        part2()
    }

    private fun part1() {
        readFileAsLinesUsingUseLines("day11.txt").forEach { line ->
            seats.add(line.toCharArray())
        }
        var diffs: Int
        do {
            val newSeats = mutableListOf<CharArray>()
            seats.forEachIndexed { rowIndex, row ->
                val newRow = mutableListOf<Char>()
                row.forEachIndexed { columnIndex, c ->
                    newRow.add(getNewSeatValuePart1(rowIndex, columnIndex, c))
                }
                newSeats.add(newRow.toCharArray())
            }
            diffs = calculateDiffs(newSeats)
            seats = newSeats
        } while (diffs != 0)
        val part1 = seats.map { it.count { it == occupiedChar } }.sum()
        println("Part 1 = $part1")
    }

    private fun part2() {
        readFileAsLinesUsingUseLines("day11.txt").forEach { line ->
            seats.add(line.toCharArray())
        }
        var diffs: Int
        do {
            val newSeats = mutableListOf<CharArray>()
            seats.forEachIndexed { rowIndex, row ->
                val newRow = mutableListOf<Char>()
                row.forEachIndexed { columnIndex, c ->
                    newRow.add(getNewSeatValuePart2(rowIndex, columnIndex, c))
                }
                newSeats.add(newRow.toCharArray())
            }
            diffs = calculateDiffs(newSeats)
            seats = newSeats
        } while (diffs != 0)
        val part2 = seats.map { it.count { it == occupiedChar } }.sum()
        println("Part 2 = $part2")
    }

    private fun getNewSeatValuePart1(rowIndex: Int, columnIndex: Int, c: Char): Char {
        if (c == floorChar) return floorChar
        val occupiedAdjacentSeats = getOccupiedAdjacentSeats(rowIndex, columnIndex)
        return if (c == emptyChar && occupiedAdjacentSeats == 0) {
            occupiedChar
        } else if (c == occupiedChar && occupiedAdjacentSeats >= 4) {
            emptyChar
        } else {
            c
        }
    }

    private fun getNewSeatValuePart2(rowIndex: Int, columnIndex: Int, c: Char): Char {
        if (c == floorChar) return floorChar
        val occupiedVisibleSeats = arrayOf(
            isFirstVisibleSeatOccupied(-1, 0, rowIndex, columnIndex),
            isFirstVisibleSeatOccupied(-1, -1, rowIndex, columnIndex),
            isFirstVisibleSeatOccupied(-1, 1, rowIndex, columnIndex),
            isFirstVisibleSeatOccupied(1, 0, rowIndex, columnIndex),
            isFirstVisibleSeatOccupied(1, -1, rowIndex, columnIndex),
            isFirstVisibleSeatOccupied(1, 1, rowIndex, columnIndex),
            isFirstVisibleSeatOccupied(0, 1, rowIndex, columnIndex),
            isFirstVisibleSeatOccupied(0, -1, rowIndex, columnIndex)
        ).count { it }
        return if (c == emptyChar && occupiedVisibleSeats == 0) {
            occupiedChar
        } else if (c == occupiedChar && occupiedVisibleSeats >= 5) {
            emptyChar
        } else {
            c
        }
    }

    private fun getOccupiedAdjacentSeats(rowIndex: Int, columnIndex: Int): Int {
        val rowSize = seats.size
        val columnSize = seats.first().size
        var occupiedAdjacentSeats = 0
        // top
        if (rowIndex > 0) {
            if (seats[rowIndex - 1][columnIndex] == occupiedChar) occupiedAdjacentSeats++
            // left
            if (columnIndex > 0 && seats[rowIndex - 1][columnIndex - 1] == occupiedChar) occupiedAdjacentSeats++
            // right
            if (columnIndex < columnSize - 1 && seats[rowIndex - 1][columnIndex + 1] == occupiedChar) occupiedAdjacentSeats++
        }
        // bottom
        if (rowIndex < rowSize - 1) {
            if (seats[rowIndex + 1][columnIndex] == occupiedChar) occupiedAdjacentSeats++
            // left
            if (columnIndex > 0 && seats[rowIndex + 1][columnIndex - 1] == occupiedChar) occupiedAdjacentSeats++
            // right
            if (columnIndex < columnSize - 1 && seats[rowIndex + 1][columnIndex + 1] == occupiedChar) occupiedAdjacentSeats++
        }
        // left
        if (columnIndex > 0 && seats[rowIndex][columnIndex - 1] == occupiedChar) occupiedAdjacentSeats++
        // right
        if (columnIndex < columnSize - 1 && seats[rowIndex][columnIndex + 1] == occupiedChar) occupiedAdjacentSeats++
        return occupiedAdjacentSeats
    }

    private fun isFirstVisibleSeatOccupied(rowDiff: Int, columnDiff: Int, rowIndex: Int, columnIndex: Int): Boolean {
        var row = rowIndex
        var column = columnIndex
        while (true) {
            row += rowDiff
            column += columnDiff
            if (!areIndexesValid(row, column)) {
                break
            } else {
                val currentChar = seats[row][column]
                if (currentChar == emptyChar) {
                    return false
                } else if (currentChar == occupiedChar) {
                    return true
                }
            }
        }
        return false
    }

    private fun areIndexesValid(rowIndex: Int, columnIndex: Int): Boolean {
        val rowSize = seats.size
        val columnSize = seats.first().size
        return rowIndex >= 0 && rowIndex <= rowSize - 1 && columnIndex >= 0 && columnIndex <= columnSize - 1
    }

    private fun calculateDiffs(newSeats: List<CharArray>): Int {
        println()
        println()
        var diffs = 0
        seats.forEachIndexed { rowIndex, chars ->
            chars.forEachIndexed { columnIndex, c ->
                print(newSeats[rowIndex][columnIndex])
                if (newSeats[rowIndex][columnIndex] != c) {
                    diffs++
                }
            }
            println()
        }
        return diffs
    }
}