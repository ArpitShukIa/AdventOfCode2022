fun main() {

    fun part1(input: String): Int {
        return input
            .split("\n\n")
            .maxOf { it.split("\n").sumOf(String::toInt) }
    }

    fun part2(input: String): Int {
        return input
            .split("\n\n")
            .map { it.split("\n").sumOf(String::toInt) }
            .sortedDescending()
            .let { it[0] + it[1] + it[2] }
    }

    val testInput = readInputText("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInputText("Day01")
    println(part1(input))
    println(part2(input))
}
