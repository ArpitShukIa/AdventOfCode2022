typealias Rock = Set<Pair<Int, Int>>

fun main() {

    val input = readInputText("Day17")
    val grid = (0..6).map { it to 0 }.toMutableSet()
    var i = 0
    var top = 0
    var added = 0L
    var rocksDropped = 0L
    val totalRocksToDrop = 1000000000000
    val seen = mutableMapOf<Any, Pair<Long, Int>>()

    fun Rock.moveLeft() = if (any { it.first == 0 }) this else map { it.first - 1 to it.second }.toSet()
    fun Rock.moveRight() = if (any { it.first == 6 }) this else map { it.first + 1 to it.second }.toSet()
    fun Rock.moveUp() = map { it.first to it.second + 1 }.toSet()
    fun Rock.moveDown() = map { it.first to it.second - 1 }.toSet()

    fun getRock(i: Int, y: Int) = listOf(
        setOf(2 to y, 3 to y, 4 to y, 5 to y),
        setOf(3 to y + 2, 2 to y + 1, 3 to y + 1, 4 to y + 1, 3 to y),
        setOf(2 to y, 3 to y, 4 to y, 4 to y + 1, 4 to y + 2),
        setOf(2 to y, 2 to y + 1, 2 to y + 2, 2 to y + 3),
        setOf(2 to y + 1, 2 to y, 3 to y + 1, 3 to y)
    )[i]

    while (rocksDropped < totalRocksToDrop) {
        var rock = getRock((rocksDropped % 5).toInt(), top + 4)
        while (true) {
            if (input[i] == '<') {
                rock = rock.moveLeft()
                if (rock.intersect(grid).isNotEmpty())
                    rock = rock.moveRight()
            } else {
                rock = rock.moveRight()
                if (rock.intersect(grid).isNotEmpty())
                    rock = rock.moveLeft()
            }
            i = (i + 1) % input.length
            rock = rock.moveDown()
            if (rock.intersect(grid).isNotEmpty()) {
                rock = rock.moveUp()
                grid += rock
                top = grid.maxOf { it.second }

                val maxY = grid.maxOf { it.second }
                val topGrid = grid.map { it.first to maxY - it.second }.filter { it.second <= 30 }.toSet()
                val key = Triple(i, rocksDropped % 5, topGrid)
                if (key in seen && rocksDropped >= 2022) {
                    val (oldRocksDropped, oldTop) = seen[key]!!
                    val heightDiff = top - oldTop
                    val dropped = rocksDropped - oldRocksDropped
                    val cyclesCount = (totalRocksToDrop - rocksDropped) / dropped
                    added += cyclesCount * heightDiff
                    rocksDropped += dropped * cyclesCount
                    assert(rocksDropped <= totalRocksToDrop)
                }
                seen[key] = rocksDropped to top
                break
            }
        }
        rocksDropped++
        if (rocksDropped == 2022L)
            println("Part 1: $top")
    }
    println("Part 2: ${top + added}")
}
