package structures

open class Graph<T> {

    open class Node<T>(val data: T) {
        var visited = false
        var adjacent = ArrayList<Node<T>>()
        fun addAdjacent(node: Node<T>) {
            adjacent.add(node)
        }

        fun removeAdjacent(node: Node<T>) {
            if (adjacent.contains(node)) {
                adjacent.remove(node)
            }
        }
    }

    var nodes = ArrayList<Node<T>>()

    fun addNode(node: Node<T>) {
        nodes.add(node)
    }

    fun findNodeByValue(value: String): Node<T>? {
        nodes.forEach {
            if (it.data == value) return it
        }
        return null
    }

    fun dfs() {
        dfsSearch(nodes.first())
    }

    fun bfs() {
        val root = nodes.first()
        root.visited = true
        val queue = Queue<Node<T>>()
        queue.add(root)
        while (queue.peek() != null) {
            val node = queue.remove()!!
            println(node.data)
            node.adjacent.forEach {
                if (!it.visited) {
                    it.visited = true
                    queue.add(it)
                }
            }
        }
    }

    private fun dfsSearch(node: Node<T>?) {
        if (node == null) return
        println(node.data)
        node.visited = true
        node.adjacent.forEach {
            if (!it.visited) {
                dfsSearch(it)
            }
        }
    }
}