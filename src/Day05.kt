fun main() {

    fun solve(input: List<String>, reversed: Boolean): String {
        val index = input.indexOf("")
        val cratesCount = input[index - 1].count { it.isDigit() }
        val crates = List(cratesCount) { mutableListOf<Char>() }
        input.take(index - 1).reversed().forEach { line ->
            for (i in line.indices step 4)
                if (line[i + 1] != ' ')
                    crates[i / 4] += line[i + 1]
        }
        input.drop(index + 1).forEach { instr ->
            val (count, from, to) = Regex("\\d+").findAll(instr).toList().map { it.value.toInt() }
            crates[to - 1] += crates[from - 1].takeLast(count).let { if (reversed) it.reversed() else it }
            repeat(count) { crates[from - 1].removeLast() }
        }
        return crates.joinToString("") { it.last().toString() }
    }

    fun part1(input: List<String>) = solve(input, reversed = true)

    fun part2(input: List<String>) = solve(input, reversed = false)

    val testInput = readInputLines("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInputLines("Day05")
    println(part1(input))
    println(part2(input))
}
