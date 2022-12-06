fun main() {

    fun part1(input: String) = input.indices.find { input.drop(it).take(4).toSet().size == 4 }!! + 4

    fun part2(input: String) = input.indices.find { input.drop(it).take(14).toSet().size == 14 }!! + 14

    val testInput = readInputText("Day06_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInputText("Day06")
    println(part1(input))
    println(part2(input))
}
