import kotlin.math.abs

fun main() {

    fun getNewKnotPos(head: Pair<Int, Int>, tail: Pair<Int, Int>): Pair<Int, Int> {
        val (hX, hY) = head
        val (tX, tY) = tail
        val dX = abs(hX - tX)
        val dY = abs(hY - tY)
        return when {
            dX == 2 && dY == 2 -> (if (tX < hX) hX - 1 else hX + 1) to (if (tY < hY) hY - 1 else hY + 1)
            dX == 2 -> (if (tX < hX) hX - 1 else hX + 1) to hY
            dY == 2 -> hX to (if (tY < hY) hY - 1 else hY + 1)
            else -> tX to tY
        }
    }

    fun solve(input: List<String>, knotIndex: Int): Int {
        val hor = mapOf("L" to -1, "R" to 1)
        val ver = mapOf("U" to -1, "D" to 1)
        val knots = MutableList(10) { 0 to 0 }
        val points = mutableSetOf(0 to 0)
        input.forEach { line ->
            val (dir, steps) = line.split(" ")
            repeat(steps.toInt()) {
                knots[0] = (knots[0].first + (hor[dir] ?: 0)) to (knots[0].second + (ver[dir] ?: 0))
                repeat(9) {
                    knots[it + 1] = getNewKnotPos(knots[it], knots[it + 1])
                }
                points += knots[knotIndex]
            }
        }
        return points.size
    }

    val input = readInputLines("Day09")
    println(solve(input, knotIndex = 1))
    println(solve(input, knotIndex = 9))
}
