import java.io.File

fun readFileAsLinesUsingUseLines(fileName: String): List<String> = File("inputs/$fileName").useLines { it.toList() }
fun getLinesCount(fileName: String): Int = File("inputs/$fileName").useLines { it.toList().size }