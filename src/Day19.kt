fun main() {
    val cache: MutableMap<Pair<Set<String>, String>, Long> = mutableMapOf()
    fun ways(design: String, towels: Set<String>): Long =
        towels.sumOf { towel ->
            if (design.startsWith(towel)) design.substring(towel.length).let { next ->
                if (next.isEmpty()) 1L
                else cache.getOrPut(towels to next) { ways(next, towels) }
            } else 0L
        }

    fun solve(input: List<String>): Pair<Int, Long> {
        val towels = input.first().split(", ").toSet()
        val designs = input.drop(2)
        val ways = designs.map { design -> ways(design, towels) }
        return ways.filter { it > 0L }.size to ways.sum()
    }

    val testInput = listOf(
        "r, wr, b, g, bwu, rb, gb, br",
        "",
        "brwrr",
        "bggr",
        "gbbr",
        "rrbgbr",
        "ubwu",
        "bwurrg",
        "brgr",
        "bbrgwb",
    )
    check(solve(testInput) == 6 to 16L)

    val input = readInput("Day19")
    solve(input).toList().forEach(::println)
}
