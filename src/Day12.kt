import java.util.LinkedList
import java.util.Queue

fun main() {

    fun solve(input: List<String>, part1: Boolean): Int {
        fun value(ch: Char) = when (ch) {
            'S' -> 'a'
            'E' -> 'z'
            else -> ch
        }

        val visited = mutableSetOf<Pair<Int, Int>>()
        val q: Queue<Triple<Int, Int, Int>> = LinkedList()
        for (i in input.indices)
            for (j in input[0].indices)
                if ((part1 && input[i][j] == 'S') || (!part1 && value(input[i][j]) == 'a'))
                    q.add(Triple(i, j, 0))

        while (q.isNotEmpty()) {
            val (x, y, c) = q.poll()
            if (x to y in visited)
                continue
            visited += x to y
            if (input[x][y] == 'E')
                return c
            listOf(-1 to 0, 0 to 1, 1 to 0, 0 to -1).forEach { (dx, dy) ->
                val (a, b) = (x + dx) to (y + dy)
                if (a in input.indices && b in input[0].indices && value(input[a][b]) - value(input[x][y]) < 2)
                    q.add(Triple(a, b, c + 1))
            }
        }
        return -1
    }

    fun part1(input: List<String>) = solve(input, part1 = true)

    fun part2(input: List<String>) = solve(input, part1 = false)

    val testInput = readInputLines("Day12_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = readInputLines("Day12")
    println(part1(input))
    println(part2(input))
}
