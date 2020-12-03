fun main() {
    Day2()
}

class Day2 {

    init {
        var validPasswordsPart1 = 0
        var validPasswordsPart2 = 0
        readFileAsLinesUsingUseLines("day2.txt").forEach { line ->
            val lower = line.substring(0, line.indexOf('-')).toInt()
            val higher = line.substring(line.indexOf('-') + 1, line.indexOf(' ')).toInt()
            val char = line.substring(line.indexOf(' ') + 1, line.indexOf(' ') + 2).toCharArray().first()
            val password = line.substring(line.indexOf(':') + 2)
            validPasswordsPart1 += isPasswordValidPart1(lower, higher, char, password)
            validPasswordsPart2 += isPasswordValidPart2(lower, higher, char, password)
        }
        println("Valid passwords part 1: $validPasswordsPart1")
        println("Valid passwords part 2: $validPasswordsPart2")
    }

    private fun isPasswordValidPart1(lower: Int, higher: Int, char: Char, password: String): Int {
        val count = password.count { it == char }
        return if (count in lower..higher) 1 else 0
    }

    private fun isPasswordValidPart2(lower: Int, higher: Int, char: Char, password: String): Int {
        val isLowerValid = password[lower - 1] == char
        val isHigherValid = password[higher - 1] == char
        val isValid = isLowerValid != isHigherValid
        return if (isValid) 1 else 0
    }
}
