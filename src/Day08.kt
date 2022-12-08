fun main() {

    fun part1(input: List<String>): Int {
        return input.indices.sumOf { i ->
            input[0].indices.count { j ->
                val left = (j - 1 downTo 0).map { i to it }
                val right = (j + 1 until input[0].length).map { i to it }
                val up = (i - 1 downTo 0).map { it to j }
                val down = (i + 1 until input.size).map { it to j }
                listOf(left, right, up, down).any { trees ->
                    trees.all { (x, y) -> input[x][y] < input[i][j] }
                }
            }
        }
    }

    fun part2(input: List<String>): Int {
        return input.indices.maxOf { i ->
            input[0].indices.maxOf { j ->
                val left = (j - 1 downTo 0).map { i to it }
                val right = (j + 1 until input[0].length).map { i to it }
                val up = (i - 1 downTo 0).map { it to j }
                val down = (i + 1 until input.size).map { it to j }
                listOf(left, right, up, down).map { trees ->
                    minOf(trees.takeWhile { (x, y) -> input[x][y] < input[i][j] }.size + 1, trees.size)
                }.reduce(Int::times)
            }
        }
    }

    val testInput = readInputLines("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInputLines("Day08")
    println(part1(input))
    println(part2(input))
}
