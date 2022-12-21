typealias Graph = Map<String, Pair<Long?, List<String>?>>

fun main() {

    fun Graph.solve(curr: String): Long? {
        val (a, sign, b) = this[curr]!!.second ?: return this[curr]!!.first
        val x = solve(a) ?: return null
        val y = solve(b) ?: return null
        return mapOf("+" to x + y, "-" to x - y, "*" to x * y, "/" to x / y)[sign]!!
    }

    fun part1(input: List<String>): Long {
        val graph = mutableMapOf<String, Pair<Long?, List<String>?>>()
        input.forEach {
            val tokens = it.split(Regex("[ :]+"))
            graph[tokens[0]] = if (tokens.size == 2) tokens[1].toLong() to null else null to tokens.drop(1)
        }
        return graph.solve("root")!!
    }

    fun part2(input: List<String>): Long {
        val graph = mutableMapOf<String, Pair<Long?, List<String>?>>()
        input.forEach {
            val tokens = it.split(Regex("[ :]+"))
            graph[tokens[0]] = when {
                tokens.size == 4 -> null to tokens.drop(1)
                tokens[0] == "humn" -> null to null
                else -> tokens[1].toLong() to null
            }
        }
        fun Graph.findHuman(curr: String, ans: Long): Long {
            val (a, sign, b) = this[curr]!!.second ?: return this[curr]!!.first ?: ans
            val (x, y) = solve(a) to solve(b)
            return if (x == null)
                findHuman(a, mapOf("+" to ans - y!!, "-" to ans + y, "*" to ans / y, "/" to ans * y)[sign]!!)
            else
                findHuman(b, mapOf("+" to ans - x, "-" to x - ans, "*" to ans / x, "/" to ans * x)[sign]!!)
        }
        val (a, _, b) = graph["root"]!!.second!!
        return graph.findHuman(a, graph.solve(b)!!)
    }

    val testInput = readInputLines("Day21_test")
    check(part1(testInput) == 152L)
    check(part2(testInput) == 301L)

    val input = readInputLines("Day21")
    println(part1(input))
    println(part2(input))
}
