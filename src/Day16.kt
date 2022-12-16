fun main() {

    fun solve(input: List<String>, totalTime: Int, players: Int): Int {
        val graph = mutableMapOf<String, Pair<Int, List<String>>>()
        input.forEach { line ->
            val tokens = Regex("[\\dA-Z]+").findAll(line).map { it.value }.toList()
            graph[tokens[1]] = tokens[2].toInt() to tokens.drop(3)
        }
        val valves = graph.keys.sortedBy { if (it == "AA") -1 else if (graph[it]!!.first > 0) 0 else 1 }
        val rates = valves.map { graph[it]!!.first }
        val connections = valves.map { graph[it]!!.second.map { valves.indexOf(it) } }
        val count = rates.count { it > 0 } + 1
        val dp = MutableList((1 shl count) * valves.size * 31 * 2) { -1 }

        fun solve(currValve: Int, openValves: Int, timeLeft: Int, players: Int): Int {
            if (timeLeft == 0)
                return if (players > 1) solve(0, openValves, totalTime, players - 1) else 0
            val key = openValves * valves.size * 31 * 2 + currValve * 31 * 2 + timeLeft * 2 + players
            if (dp[key] != -1)
                return dp[key]
            if (rates[currValve] > 0 && ((1 shl currValve) and openValves) == 0) {
                dp[key] = (timeLeft - 1) * rates[currValve] +
                        solve(currValve, (1 shl currValve) or openValves, timeLeft - 1, players)
            }
            connections[currValve].forEach { valve ->
                dp[key] = maxOf(dp[key], solve(valve, openValves, timeLeft - 1, players))
            }
            return dp[key]
        }
        return solve(0, 0, totalTime, players)
    }

    fun part1(input: List<String>) = solve(input, totalTime = 30, players = 1)

    fun part2(input: List<String>) = solve(input, totalTime = 26, players = 2)

    val testInput = readInputLines("Day16_test")
    check(part1(testInput) == 1651)
    check(part2(testInput) == 1707)

    val input = readInputLines("Day16")
    println(part1(input))
    println(part2(input))
}
