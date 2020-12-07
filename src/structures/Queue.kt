package structures

open class Queue<T> {
    class Node<T>(val data: T) {
        var next: Node<T>? = null

        fun print() {
            println()
            var n: Node<T>? = this
            while (n != null) {
                print(n.data.toString() + " -> ")
                n = n.next
            }
        }
    }

    var first: Node<T>? = null
    var last: Node<T>? = null

    fun remove(): T? {
        if (first == null) throw NoSuchElementException()
        val data = first!!.data
        first = first!!.next
        if (first == null) {
            last = null
        }
        return data
    }

    fun add(data: T) {
        val node: Node<T> = Node<T>(data)
        if (last != null) {
            last!!.next = node
        }
        last = node;
        if (first == null) {
            first = last;
        }
    }

    fun peek(): T? {
        return first?.data
    }

    fun print() {
        if (first == null) throw NoSuchElementException()
        println()
        var n = first
        while (n != null) {
            print(n.data.toString() + " -> ")
            n = n.next
        }
    }
}

fun main() {
    val queue = Queue<String>()
    queue.add("3")
    queue.add("1")
    println(queue.remove())
    queue.add("2")
    queue.add("3")
    println(queue.remove())
    println(queue.remove())
}