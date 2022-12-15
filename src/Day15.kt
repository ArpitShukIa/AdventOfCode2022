import kotlin.math.abs

fun main() {

    fun part1(input: List<String>, y: Int): Int {
        val beacons = mutableSetOf<Pair<Int, Int>>()
        val possibleX = mutableSetOf<Int>()
        input.forEach { line ->
            val (sX, sY, bX, bY) = Regex("[-\\d]+").findAll(line).map { it.value.toInt() }.toList()
            val dx = abs(sY - bY) + abs(sX - bX) - abs(sY - y)
            beacons += bX to bY
            possibleX += sX - dx..sX + dx
        }
        return possibleX.count { it to y !in beacons }
    }

    fun part2(input: List<String>, maxCoord: Int): Long {
        val sensors = mutableListOf<Triple<Long, Long, Long>>()
        input.forEach { line ->
            val (sX, sY, bX, bY) = Regex("[-\\d]+").findAll(line).map { it.value.toLong() }.toList()
            val d = abs(sY - bY) + abs(sX - bX)
            sensors += Triple(sX, sY, d)
        }
        sensors.forEach { (x, y, d) ->
            // Find valid positions at distance d+1 from sensor
            for (dx in 0..(d + 1)) {
                val dy = (d + 1) - dx
                for ((dirX, dirY) in listOf(-1 to -1, -1 to 1, 1 to -1, 1 to 1)) {
                    val (bX, bY) = x + dx * dirX to y + dy * dirY
                    if (bX !in 0..maxCoord || bY !in 0..maxCoord)
                        continue
                    if (sensors.none { (sX, sY, d) -> abs(sY - bY) + abs(sX - bX) <= d })
                        return bX * 4000000 + bY
                }
            }
        }
        return -1
    }

    val testInput = readInputLines("Day15_test")
    check(part1(testInput, y = 10) == 26)
    check(part2(testInput, maxCoord = 20) == 56000011L)

    val input = readInputLines("Day15")
    println(part1(input, y = 2000000))
    println(part2(input, maxCoord = 4000000))
}
