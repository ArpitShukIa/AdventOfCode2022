import java.util.LinkedList
import java.util.Queue

fun main() {

    data class State(
        val ore: Int, val clay: Int, val obsidian: Int, val geode: Int,
        val rOre: Int, val rClay: Int, val rObsidian: Int, val rGeode: Int, val time: Int
    )

    fun solve(Co: Int, Cc: Int, Co1: Int, Co2: Int, Cg1: Int, Cg2: Int, time: Int): Int {
        var answer = 0
        val s = State(0, 0, 0, 0, 1, 0, 0, 0, time)
        val q: Queue<State> = LinkedList()
        q.add(s)
        val seen = mutableSetOf<State>()
        while (q.isNotEmpty()) {
            var state = q.poll()
            var (o, c, ob, g, r1, r2, r3, r4, t) = state
            answer = maxOf(answer, g)
            if (t == 0)
                continue
            val maxOreRobotsNeeded = maxOf(Co, Cc, Co1, Cg1)
            // Discard extra resources
            r1 = minOf(r1, maxOreRobotsNeeded)
            r2 = minOf(r2, Co2)
            r3 = minOf(r3, Cg2)
            o = minOf(o, maxOreRobotsNeeded * t - r1 * (t - 1))
            c = minOf(c, Co2 * t - r2 * (t - 1))
            ob = minOf(ob, Cg2 * t - r3 * (t - 1))

            state = State(o, c, ob, g, r1, r2, r3, r4, t)
            if (state in seen)
                continue
            seen += state

            q.add(State(o + r1, c + r2, ob + r3, g + r4, r1, r2, r3, r4, t - 1))
            if (o >= Co)
                q.add(State(o - Co + r1, c + r2, ob + r3, g + r4, r1 + 1, r2, r3, r4, t - 1))
            if (o >= Cc)
                q.add(State(o - Cc + r1, c + r2, ob + r3, g + r4, r1, r2 + 1, r3, r4, t - 1))
            if (o >= Co1 && c >= Co2)
                q.add(State(o - Co1 + r1, c - Co2 + r2, ob + r3, g + r4, r1, r2, r3 + 1, r4, t - 1))
            if (o >= Cg1 && ob >= Cg2)
                q.add(State(o - Cg1 + r1, c + r2, ob - Cg2 + r3, g + r4, r1, r2, r3, r4 + 1, t - 1))
        }
        return answer
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val tokens = Regex("\\d+").findAll(line).map { it.value.toInt() }.toList()
            tokens[0] * solve(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], 24)
        }
    }

    fun part2(input: List<String>): Int {
        return input.take(3).map { line ->
            val tokens = Regex("\\d+").findAll(line).map { it.value.toInt() }.toList()
            solve(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], 32)
        }.reduce(Int::times)
    }

    val input = readInputLines("Day19")
    println(part1(input))
    println(part2(input))
}
