import java.util.LinkedList
import java.util.Queue

fun main() {

    fun List<Int>.neighbors() = listOf(-1, 1).flatMap {
        listOf(
            listOf(get(0) + it, get(1), get(2)),
            listOf(get(0), get(1) + it, get(2)),
            listOf(get(0), get(1), get(2) + it)
        )
    }

    fun part1(input: List<String>): Int {
        val cubes = input.map { line -> line.split(",").map { it.toInt() } }
        return cubes.sumOf { cube -> cube.neighbors().count { it !in cubes } }
    }

    fun part2(input: List<String>): Int {
        val cubes = input.map { line -> line.split(",").map { it.toInt() } }
        val inside = mutableSetOf<List<Int>>()
        val outside = mutableSetOf<List<Int>>()
        return cubes.sumOf {
            it.neighbors().count { cube ->
                if (cube in outside)
                    return@count true
                if (cube in inside)
                    return@count false
                val seen = mutableSetOf<List<Int>>()
                val q: Queue<List<Int>> = LinkedList()
                q.add(cube)
                while (q.isNotEmpty()) {
                    val c = q.poll()
                    if (c in cubes || c in seen)
                        continue
                    seen += c
                    if (seen.size > 2000) {
                        outside += seen
                        return@count true
                    }
                    q.addAll(c.neighbors())
                }
                inside += seen
                false
            }
        }
    }

    val testInput = readInputLines("Day18_test")
    check(part1(testInput) == 64)
    check(part2(testInput) == 58)

    val input = readInputLines("Day18")
    println(part1(input))
    println(part2(input))
}
