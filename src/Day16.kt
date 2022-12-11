fun main() {
    Day16()
}

class Day16 {

    init {
//        part1()
        part2()
    }

    private fun part1() {
        val ranges = mutableListOf<Pair<Int, Int>>()
        val nearbyTickets = mutableListOf<Int>()
        var nearbyTicketsStarted = false
        readFileAsLinesUsingUseLines("day16.txt").forEach {
            if (it.contains(" or ")) {
                ranges.addAll(
                    it.split(" ")
                        .filter { it.toCharArray().first().isDigit() }
                        .map { it.split("-") }
                        .map { Pair(it.first().toInt(), it.last().toInt()) }
                )
            } else if (it == "nearby tickets:") {
                nearbyTicketsStarted = true
            } else if (nearbyTicketsStarted) {
                nearbyTickets.addAll(it.split(",").map { it.toInt() })
            }
        }
        var sum = 0
        nearbyTickets.forEach { ticket ->
            var contained = false
            ranges.forEach { range ->
                if ((range.first..range.second).contains(ticket)) {
                    contained = true
                    return@forEach
                }
            }
            if (!contained) {
                sum += ticket
            }
        }
        println(sum)
    }

    private fun part2() {
        val ranges = linkedMapOf<String, Pair<IntRange, IntRange>>()
        val nearbyTickets = mutableListOf<List<Int>>()
        val myTickets = mutableListOf<Int>()
        var myTicketStarted = false
        var nearbyTicketsStarted = false
        readFileAsLinesUsingUseLines("day16.txt").forEach {
            if (it.contains(" or ")) {
                it.split(": ")
                    .let {
                        ranges.put(
                            it.first(),
                            it.last()
                                .split(" or ")
                                .map { it.split("-") }
                                .map { IntRange(it.first().toInt(), it.last().toInt()) }
                                .let { Pair(it.first(), it.last()) }
                        )
                    }
            } else if (it == "your ticket:") {
                myTicketStarted = true
            } else if (myTicketStarted) {
                myTickets.addAll(it.split(",").map { it.toInt() })
                myTicketStarted = false
            } else if (it == "nearby tickets:") {
                nearbyTicketsStarted = true
            } else if (nearbyTicketsStarted) {
                nearbyTickets.add(it.split(",").map { it.toInt() })
            }
        }
        val validNearbyTickets = mutableListOf<List<Int>>()
        nearbyTickets.forEach { ticketList ->
            var contains = true
            ticketList.forEach {
                if (!ranges.hasTicket(it)) {
                    contains = false
                    return@forEach
                }
            }
            if (contains) {
                validNearbyTickets.add(ticketList)
            }
        }
        validNearbyTickets.add(myTickets)
        val possibleMapping = mutableListOf<MutableList<String>>()
        val ticketSize = validNearbyTickets.first().size
        for (i in 0 until ticketSize) {
            val list = validNearbyTickets.map { it[i] }
            val possible = mutableListOf<String>()
            ranges.forEach {
                var contains = true
                list.forEach { n ->
                    if (!(it.value.first.contains(n) || it.value.second.contains(n))) {
                        contains = false
                        return@forEach
                    }
                }
                if (contains) {
                    possible.add(it.key)
                }
            }
            possibleMapping.add(possible)
        }
        val trueMapping = hashMapOf<Int, String>()
        do {
            possibleMapping
                .forEachIndexed { index, it ->
                    if (it.size == 1) {
                        val toRemove = it.first()
                        trueMapping.put(index, toRemove)
                        it.remove(toRemove)
                        possibleMapping.forEach {
                            it.remove(toRemove)
                        }
                    }
                }
        } while (trueMapping.size != possibleMapping.map { it.isNotEmpty() }.size)
        val product = myTickets.mapIndexed { index, i -> Pair(trueMapping[index]!!, i) }
            .filter { it.first.startsWith("departure") }
            .map { it.second.toLong() }
            .reduce { accumulator, element ->
                accumulator * element
            }

        println(product)
    }
}

private fun HashMap<String, Pair<IntRange, IntRange>>.hasTicket(value: Int): Boolean {
    values.forEach {
        if (it.contains(value)) {
            return true
        }
    }
    return false
}

private fun Pair<IntRange, IntRange>.contains(value: Int): Boolean {
    return first.contains(value) || second.contains(value)
}