import structures.Graph

fun main() {
    Day7()
}

class Day7 {

    private val list = mutableListOf<Pair<String, HashMap<String, Int>>>()
    private val bagToFind = "shiny gold"
    private var graph = Graph<String>()

    init {
        val bagsContainingResultBag = hashSetOf<String>()
        readFileAsLinesUsingUseLines("day7.txt").forEach { line ->
            if (line.contains("contain no other bags")) {
                list.add(Pair(getBagName(line), hashMapOf<String, Int>()))
            } else {
                var formattedLine = line.replace("bag,", "bags,")
                formattedLine = formattedLine.replace("bag.", "bags.")
                val bagName = getBagName(formattedLine)
                val restOfTheString = formattedLine.substring(line.indexOf("contain ") + 8)
                val map = hashMapOf<String, Int>()
                var containsResultBag = false
                restOfTheString.split(",").forEach {
                    formattedLine = it.trimStart().replace(".", "")
                    val name = formattedLine.substring(formattedLine.indexOf(" ") + 1).replace(" bags", "")
                    val number = formattedLine.substring(0, formattedLine.indexOf(" ")).replace(" ", "").toInt()
                    if (name.contains(bagToFind)) {
                        containsResultBag = true
                    }
                    map[name] = number
                }
                if (containsResultBag) {
                    bagsContainingResultBag.add(bagName)
                }
                list.add(Pair(bagName, map))
            }
        }
        part1()
        part2()
    }

    private fun part1() {
        var part1Result = 0
        list.forEach {
            if (it.first != bagToFind && findRecursive(it.first)) {
                part1Result++
            }
        }
        println("Part 1: $part1Result")
    }

    private fun findRecursive(key: String): Boolean {
        graph = Graph()
        val rootNode = Graph.Node(key)
        increment(rootNode)
        return graph.findNodeByValue(bagToFind) != null
    }

    private fun increment(rootNode: Graph.Node<String>) {
        graph.addNode(rootNode)
        getAllBags(rootNode.data)?.forEach {
            val node = Graph.Node(it.key)
            rootNode.addAdjacent(node)
            increment(node)
        }
    }

    private fun part2() {
        val hashMap = hashMapOf<String, Int>()
        list.filter { it.second.count() == 0 }.forEach {
            hashMap[it.first] = 0
        }
        while (hashMap.size != list.size) {
            list.forEach { row ->
                if (!hashMap.containsKey(row.first) && valueExistsForAllChildren(row.second, hashMap)) {
                    var childCount = 0
                    row.second.forEach {
                        val value = it.value + (it.value * hashMap[it.key]!!)
                        childCount += value
                    }
                    hashMap[row.first] = childCount
                }
            }
        }
        println("Part 2: ${hashMap[bagToFind]}")
    }

    private fun valueExistsForAllChildren(children: HashMap<String, Int>, hashMap: HashMap<String, Int>): Boolean {
        children.forEach {
            if (!hashMap.containsKey(it.key)) {
                return false
            }
        }
        return true
    }

    private fun getAllBags(key: String): HashMap<String, Int>? {
        list.forEach {
            if (it.first == key) {
                return it.second
            }
        }
        return null
    }

    private fun getBagName(line: String): String {
        return line.substring(0, line.indexOf(" contain")).replace(" bags", "")
    }
}