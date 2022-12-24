import java.util.LinkedList
import java.util.Queue

fun main() {

    data class State(val x: Int, val y: Int, val t: Int, val reachEnd: Boolean, val reachStart: Boolean)

    val input = readInputLines("Day24")
    val (r, c) = input.size - 2 to input[0].length - 2
    val badCells = (0..r * c).associateWith { t ->
        buildSet {
            for (i in 0 until r)
                for (j in 0 until c)
                    when (input[i + 1][j + 1]) {
                        '>' -> add(i to (j + t) % c)
                        'v' -> add((i + t) % r to j)
                        '<' -> add(i to (j - t).mod(c))
                        '^' -> add((i - t).mod(r) to j)
                    }
        }
    }
    val q: Queue<State> = LinkedList()
    val seen = mutableSetOf<State>()
    q.add(State(x = 0, y = 1, t = 0, reachEnd = false, reachStart = false))
    var part1 = false
    while (q.isNotEmpty()) {
        val state = q.poll()
        var (x, y, t, reachEnd, reachStart) = state
        if (x !in input.indices || y !in input[0].indices || input[x][y] == '#')
            continue
        if (x == input.lastIndex) {
            reachEnd = true
            if (reachStart) {
                println("Part 2: $t")
                return
            } else if (!part1)
                println("Part 1: $t")
            part1 = true
        }
        if (x == 0 && reachEnd)
            reachStart = true
        if (state in seen)
            continue
        seen += state
        listOf(0 to 0, 0 to 1, 0 to -1, 1 to 0, -1 to 0).forEach { (dx, dy) ->
            if (x - 1 + dx to y - 1 + dy !in badCells[t + 1]!!)
                q.add(State(x + dx, y + dy, t + 1, reachEnd, reachStart))
        }
    }
}
