fun main() {
    Day3()
}

class Day3 {
    var height = 0
    var width = 0
    lateinit var array: Array<Array<Boolean>>

    init {
        height = getLinesCount("day3.txt")
        width = 0
        var i = 0
        readFileAsLinesUsingUseLines("day3.txt").forEach { line ->
            if (width == 0) {
                width = line.length
                array = Array(height) { emptyArray<Boolean>() }
            }
            booleanArrayOf()
            val lineArray = Array(width) { false }
            line.toCharArray().forEachIndexed { index, c ->
                if (c == '#') {
                    lineArray[index] = true
                }
            }
            array[i] = lineArray
            i++
        }

        println("Part 1 = ${calculateSlope(1, 3)}")
        val part2Result = calculateSlope(1, 1) * calculateSlope(1, 3) *
                calculateSlope(1, 5) * calculateSlope(1, 7) * calculateSlope(2, 1)
        println("Part 2 = $part2Result")
    }

    private fun calculateSlope(rowStep: Int, collumnStep: Int): Int {
        var treeCount = 0
        var row = 0
        var collumn = 0
        while (true) {
            row += rowStep
            collumn += collumnStep
            if (row >= height) {
                break
            }
            if (collumn > width) {
                collumn %= width
            } else if (collumn == width) {
                collumn = 0
            }
            if (array[row][collumn]) {
                treeCount++
            }
        }
        return treeCount
    }
}
