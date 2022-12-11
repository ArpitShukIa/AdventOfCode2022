fun main() {

    fun solve(input: List<String>, rounds: Int, divideBy: Int): Long {
        val monkeys = input.chunked(7)
        val inspectionCounts = MutableList(monkeys.size) { 0L }
        val items = monkeys.map { lines ->
            lines[1].substringAfter(": ").split(", ").map { it.toLong() }.toMutableList()
        }
        val mod = monkeys.map { it[3].split(" ").last().toInt() }.reduce(Int::times)
        repeat(rounds) {
            monkeys.forEachIndexed { m, lines ->
                items[m].forEach { old ->
                    inspectionCounts[m]++
                    val (d, m1, m2) = (3..5).map { lines[it].split(" ").last().toInt() }
                    val x = lines[2].split(" ").last().toLongOrNull() ?: old
                    var worry = if ("+" in lines[2]) old + x else old * x
                    worry = if (divideBy == 1) worry % mod else worry / divideBy
                    items[if (worry % d == 0L) m1 else m2] += worry
                }
                items[m].clear()
            }
        }
        return inspectionCounts.sortedDescending().let { it[0] * it[1] }
    }

    fun part1(input: List<String>) = solve(input, rounds = 20, divideBy = 3)

    fun part2(input: List<String>) = solve(input, rounds = 10000, divideBy = 1)

    val testInput = readInputLines("Day11_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInputLines("Day11")
    println(part1(input))
    println(part2(input))
}
