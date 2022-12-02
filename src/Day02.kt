fun main() {

    fun part1(input: List<String>): Int {
        return input.sumOf {
            val (a, b) = it.split(" ")
            mapOf(
                "A" to mapOf("X" to 1 + 3, "Y" to 2 + 6, "Z" to 3 + 0),
                "B" to mapOf("X" to 1 + 0, "Y" to 2 + 3, "Z" to 3 + 6),
                "C" to mapOf("X" to 1 + 6, "Y" to 2 + 0, "Z" to 3 + 3),
            )[a]!![b]!!
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            val (a, b) = it.split(" ")
            mapOf(
                "A" to mapOf("X" to 0 + 3, "Y" to 3 + 1, "Z" to 6 + 2),
                "B" to mapOf("X" to 0 + 1, "Y" to 3 + 2, "Z" to 6 + 3),
                "C" to mapOf("X" to 0 + 2, "Y" to 3 + 3, "Z" to 6 + 1),
            )[a]!![b]!!
        }
    }

    val testInput = readInputLines("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInputLines("Day02")
    println(part1(input))
    println(part2(input))
}
