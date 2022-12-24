typealias State = Pair<Int, Pair<Int, Int>>

fun main() {

    fun Pair<Int, Int>.move(direction: Int) = when (direction.mod(4)) {
        0 -> first + 1 to second
        1 -> first to second + 1
        2 -> first - 1 to second
        3 -> first to second - 1
        else -> throw AssertionError()
    }

    operator fun List<String>.get(pos: Pair<Int, Int>): Char = getOrNull(pos.second)?.getOrNull(pos.first) ?: ' '

    fun getPerimeter(board: List<String>): List<State> {
        val initialPos = board[0].indexOf('.') to 0
        return buildList {
            var pos = initialPos
            var dir = 0
            do {
                add(dir to pos)
                val forward = pos.move(dir)
                if (board[forward] == ' ') {
                    dir = (dir + 1).mod(4)
                } else {
                    val left = forward.move(dir - 1)
                    if (board[left] == ' ') {
                        pos = forward
                    } else {
                        pos = left
                        dir = (dir - 1).mod(4)
                    }
                }
            } while (pos != initialPos || dir != 0)
        }
    }

    fun solve(input: List<String>, adjacencyMap: Map<State, State>): Int {
        val moves = input.last().split(Regex("(?<=[RL])|(?=[RL])"))
        val board = input.dropLast(2)
        var pos = board[0].indexOf('.') to 0
        var dir = 0
        for (move in moves) {
            when (move) {
                "L" -> dir = (dir - 1).mod(4)
                "R" -> dir = (dir + 1).mod(4)
                else -> repeat(move.toInt()) {
                    val (newDir, newPos) = adjacencyMap[dir to pos] ?: (dir to pos.move(dir))
                    if (board[newPos] == '#') return@repeat
                    dir = newDir
                    pos = newPos
                }
            }
        }
        return 1000 * (pos.second + 1) + 4 * (pos.first + 1) + dir
    }

    fun part1(input: List<String>): Int {
        val board = input.dropLast(2)
        val perimeter = getPerimeter(board)
        val adjacencyMap = mutableMapOf<State, State>()
        for ((dir, pos) in perimeter) {
            val direction = (dir - 1).mod(4)
            var (x, y) = pos
            when (direction) {
                0 -> x = board[y].indexOfFirst { it != ' ' }
                1 -> y = board.indexOfFirst { x in it.indices && it[x] != ' ' }
                2 -> x = board[y].indexOfLast { it != ' ' }
                3 -> y = board.indexOfLast { x in it.indices && it[x] != ' ' }
            }
            adjacencyMap[direction to pos] = direction to (x to y)
        }
        return solve(input, adjacencyMap)
    }

    fun part2(input: List<String>, sideLength: Int): Int {
        val adjacencyMap = mutableMapOf<State, State>()
        val perimeter = getPerimeter(board = input.dropLast(2))
        val edges = perimeter.chunked(sideLength).map { it[0].first to it }.toMutableList()
        while (edges.isNotEmpty()) {
            var i = 0
            while (i < edges.lastIndex) {
                val a = edges[i]
                val b = edges[i + 1]
                if ((a.first - b.first).mod(4) == 1) {
                    edges.subList(i, i + 2).clear()
                    for (j in i..edges.lastIndex) {
                        val edge = edges[j]
                        edges[j] = (edge.first - 1).mod(4) to edge.second
                    }
                    for (j in 0 until sideLength) {
                        val (dir1, pos1) = a.second[j]
                        val (dir2, pos2) = b.second[sideLength - j - 1]
                        adjacencyMap[(dir1 - 1).mod(4) to pos1] = (dir2 + 1).mod(4) to pos2
                        adjacencyMap[(dir2 - 1).mod(4) to pos2] = (dir1 + 1).mod(4) to pos1
                    }
                } else {
                    i++
                }
            }
        }
        return solve(input, adjacencyMap)
    }

    val testInput = readInputLines("Day22_test")
    check(part1(testInput) == 6032)
    check(part2(testInput, sideLength = 4) == 5031)

    val input = readInputLines("Day22")
    println(part1(input))
    println(part2(input, sideLength = 50))
}
