fun main() {
    val input = readInputLines("Day25")
    val map = mapOf('1' to 1, '2' to 2, '0' to 0, '-' to -1, '=' to -2)
    var decimal = input.sumOf { it.indices.fold(0L) { acc, i -> acc * 5 + map[it[i]]!! } }
    var ans = ""
    while (decimal > 0) {
        val r = (decimal % 5).toInt()
        val d = listOf(0, 1, 2, -2, -1)[r]
        ans = "012=-"[r] + ans
        decimal = (decimal - d) / 5
    }
    println(ans)
}
