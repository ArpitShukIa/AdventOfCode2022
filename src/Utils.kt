import java.io.File

fun readInputText(name: String) = File("src", "$name.txt").readText()

fun readInputLines(name: String) = File("src", "$name.txt").readLines()
