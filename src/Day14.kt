fun main() {
    Day14()
}

class Day14 {

    class Instruction(line: String) {
        val mask: String? = if (line.startsWith("mask")) {
            line.split(" ").last()
        } else {
            null
        }
        val address: Long? = if (line.startsWith("mem")) {
            line.split("]").first().split("[").last().toLong()
        } else {
            null
        }
        val value: Long? = if (line.startsWith("mem")) {
            line.split(" ").last().toLong()
        } else {
            null
        }
    }

    init {
//        part1()
        part2()
    }

    var mask = ""

    private fun part1() {
        val memory: MutableMap<Long, Long> = mutableMapOf()
        readFileAsLinesUsingUseLines("day14.txt").forEachIndexed { index, s ->
            val instruction = Instruction(s)
            instruction.mask?.let {
                mask = it
            } ?: let {
                memory[instruction.address!!] = calculateValue(instruction.value!!)
            }
        }
        println(memory.values.sum())
    }

    private fun part2() {
        val memory: MutableMap<Long, Long> = mutableMapOf()
        readFileAsLinesUsingUseLines("day14.txt").forEachIndexed { index, s ->
            val instruction = Instruction(s)
            instruction.mask?.let {
                mask = it
            } ?: let {
                applyBitmaskToAddress(mask, instruction.address!!).forEach {
                    memory[it] = instruction.value!!
                }
            }
        }
        println(memory.values.sum())
    }

    private fun calculateValue(value: Long): Long {
        val binaryValue = value.toString(2)
        var binaryResult = ""
        mask.forEachIndexed { index, bit ->
            binaryResult += (
                    if (bit == 'X')
                        binaryValue.getOrElse(binaryValue.length - mask.length + index) { '0' }
                    else
                        bit
                    )
        }
        return binaryResult.toLong(2)
    }

    private fun applyBitmaskToAddress(mask: String, address: Long): List<Long> {
        val givenBinaryAddress = address.toString(2)
        var result = listOf("")
        mask.forEachIndexed { index, bit ->
            result = if (bit == 'X') {
                result.flatMap { listOf(it + 0, it + 1) }
            } else {
                result.map {
                    if (bit == '0') {
                        it + givenBinaryAddress.getOrElse(givenBinaryAddress.length - mask.length + index) { '0' }
                    } else {
                        it + bit
                    }
                }
            }
        }
        return result.map { it.toLong(2) }
    }
}