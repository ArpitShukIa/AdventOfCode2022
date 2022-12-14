import kotlin.math.*

fun main() {
    val input = readInputLines("Day14")
    val grid = mutableSetOf<Pair<Int, Int>>()
    var floor = 0
    input.forEach { path ->
        path.split(" -> ")
            .map { point -> point.split(",").map { it.toInt() } }
            .windowed(2)
            .forEach { (a, b) ->
                floor = maxOf(floor, a[1], b[1])
                val (dx, dy) = b[0] - a[0] to b[1] - a[1]
                repeat(abs(dx + dy) + 1) {
                    grid += (a[0] + it * dx.sign) to (a[1] + it * dy.sign)
                }
            }
    }

    repeat(1000) { grid += it to floor + 2 } // Add floor rocks

    var part1Done = false
    for (i in 0..Int.MAX_VALUE) {
        var sand = 500 to 0
        while (true) {
            if (sand.second == floor && !part1Done) {
                println("Part 1: $i")
                part1Done = true
            }
            listOf(0, -1, 1)
                .map { sand.first + it to sand.second + 1 }
                .find { it !in grid }?.let { sand = it } ?: break
        }
        if (sand == 500 to 0) {
            println("Part 2: ${i + 1}")
            return
        }
        grid += sand.first to sand.second
    }
}
