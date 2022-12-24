fun main() {
    val input = readInputLines("Day23")
    var elves = input.indices.flatMap { i -> input[0].indices.map { j -> i to j } }
        .filter { (i, j) -> input[i][j] == '#' }.toSet()
    val directions = listOf(
        listOf(-1 to 0, -1 to 1, -1 to -1), // NORTH
        listOf(1 to 0, 1 to 1, 1 to -1),    // SOUTH
        listOf(0 to -1, 1 to -1, -1 to -1), // WEST
        listOf(0 to 1, 1 to 1, -1 to 1),    // EAST
    )
    for (i in 0..Int.MAX_VALUE) {
        val moves = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()
        for (elf in elves) {
            if (directions.flatten().any { elf + it in elves }) {
                val d = (0..3).find { d -> directions[(i + d) % 4].none { elf + it in elves } } ?: continue
                moves[elf] = elf + directions[(i + d) % 4][0]
            }
        }
        val posCountMap = mutableMapOf<Pair<Int, Int>, Int>()
        moves.forEach { (_, pos) -> posCountMap[pos] = (posCountMap[pos] ?: 0) + 1 }
        val canMove = moves.filter { (_, pos) -> posCountMap[pos] == 1 }
        if (canMove.isEmpty()) {
            println("Part 2: ${i + 1}")
            break
        }
        elves = (elves - canMove.keys + canMove.values).toMutableSet()
        if (i == 9) {
            val height = elves.maxOf { it.first } - elves.minOf { it.first } + 1
            val width = elves.maxOf { it.second } - elves.minOf { it.second } + 1
            println("Part 1: ${height * width - elves.size}")
        }
    }
}

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = first + other.first to second + other.second
