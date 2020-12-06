fun main() {
    Day4()
}

class Day4 {

    class Passport(buffer: List<String>) {

        private var ecl: String? = null
        private var pid: String? = null
        private var eyr: String? = null
        private var hcl: String? = null
        private var byr: String? = null
        private var iyr: String? = null
        private var cid: String? = null
        private var hgt: String? = null

        init {
            buffer.forEach {
                when {
                    it.contains("ecl") -> ecl = getLineValue(it)
                    it.contains("pid") -> pid = getLineValue(it)
                    it.contains("eyr") -> eyr = getLineValue(it)
                    it.contains("hcl") -> hcl = getLineValue(it)
                    it.contains("byr") -> byr = getLineValue(it)
                    it.contains("iyr") -> iyr = getLineValue(it)
                    it.contains("cid") -> cid = getLineValue(it)
                    it.contains("hgt") -> hgt = getLineValue(it)
                }
            }
        }

        private fun getLineValue(line: String): String {
            return line.subSequence(line.indexOf(':') + 1, line.length).toString()
        }

        fun isValidPart1(): Boolean {
            return ecl != null && pid != null && eyr != null && hcl != null && byr != null && iyr != null && hgt != null
        }

        fun isValidPart2(): Boolean {
            return eclValid() && pidValid() && eyrValid() && hclValid() && byrValid() && iyrValid() && hgtValid()
        }

        // hgt (Height) - a number followed by either cm or in:
        // If cm, the number must be at least 150 and at most 193.
        // If in, the number must be at least 59 and at most 76.
        private fun hgtValid(): Boolean {
            return hgt?.let {
                if (it.contains("cm")) {
                    if (it.length != 5 || !it[0].isDigit() || !it[1].isDigit() || !it[2].isDigit() || it[3] != 'c' || it[4] != 'm') {
                        return@let false
                    }
                    it.substring(0..2).toInt() in 150..193
                } else if (it.contains("in")) {
                    if (it.length != 4 || !it[0].isDigit() || !it[1].isDigit() || it[2] != 'i' || it[3] != 'n') {
                        return@let false
                    }
                    it.substring(0..1).toInt() in 59..76
                } else {
                    false
                }
            } ?: false
        }

        // iyr (Issue Year) - four digits; at least 2010 and at most 2020.
        private fun iyrValid(): Boolean {
            return isYearInRange(iyr, 2010..2020)
        }

        // byr (Birth Year) - four digits; at least 1920 and at most 2002.
        private fun byrValid(): Boolean {
            return isYearInRange(byr, 1920..2002)
        }

        // hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
        private fun hclValid(): Boolean {
            return hcl?.let {
                if (it.length == 7) {
                    it.forEachIndexed { index, c ->
                        if (index == 0) {
                            if (c != '#') {
                                return@let false
                            }
                        } else if (!c.isDigit() && !(c.isLetter() && c.isLowerCase())) {
                            return@let false
                        }
                    }
                    true
                } else {
                    false
                }
            } ?: false
        }

        // eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
        private fun eyrValid(): Boolean {
            return isYearInRange(eyr, 2020..2030)
        }

        // ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
        private fun eclValid(): Boolean {
            return ecl?.let {
                it == "amb" || it == "blu" || it == "brn" || it == "gry" || it == "grn" || it == "hzl" || it == "oth"
            } ?: false
        }

        // pid (Passport ID) - a nine-digit number, including leading zeroes.
        private fun pidValid(): Boolean {
            return pid?.let {
                if (it.length == 9) {
                    it.forEach { char ->
                        if (!char.isDigit()) {
                            return@let false
                        }
                    }
                    true
                } else {
                    false
                }
            } ?: false
        }

        private fun isYearInRange(value: String?, range: IntRange): Boolean {
            return value?.let {
                if (it.length == 4) {
                    it.forEach { char ->
                        if (!char.isDigit()) {
                            return@let false
                        }
                    }
                    it.toInt() in range
                } else {
                    false
                }
            } ?: false
        }
    }

    var validPassportsPart1 = 0
    var validPassportsPart2 = 0

    init {
        var buffer = mutableListOf<String>()
        readFileAsLinesUsingUseLines("day4.txt").forEach { line ->
            if (line.isNotEmpty()) {
                buffer.addAll(line.splitToSequence(" ").toList())
            } else {
                processBufferForPart1(buffer)
                processBufferForPart2(buffer)
                buffer = mutableListOf()
            }
        }
        processBufferForPart1(buffer)
        processBufferForPart2(buffer)
        println("Part 1 = $validPassportsPart1")
        println("Part 2 = $validPassportsPart2")
    }

    private fun processBufferForPart1(buffer: List<String>) {
        val passport = Passport(buffer)
        if (passport.isValidPart1()) {
            validPassportsPart1++
        }
    }

    private fun processBufferForPart2(buffer: List<String>) {
        val passport = Passport(buffer)
        if (passport.isValidPart2()) {
            validPassportsPart2++
        }
    }
}
