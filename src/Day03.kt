fun main() {

    fun Char.priority() = (('a'..'z') + ('A'..'Z')).indexOf(this) + 1

    fun part1(input: List<String>): Int {
        return input.sumOf { s ->
            val a = s.take(s.length / 2).toSet()
            val b = s.drop(s.length / 2).toSet()
            a.intersect(b).first().priority()
        }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.toSet() }
            .chunked(3)
            .sumOf { (a, b, c) ->
                a.intersect(b).intersect(c).first().priority()
            }
    }

    val testInput = readInputLines("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInputLines("Day03")
    println(part1(input))
    println(part2(input))
}
