import java.io.File

fun readFileAsLinesUsingUseLines(fileName: String): List<String> = File("inputs/$fileName").useLines { it.toList() }