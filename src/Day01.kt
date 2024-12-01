import kotlin.math.abs

fun main() {
    fun parseInput(input: List<String>): Pair<List<Int>, List<Int>> =
        input
            .map { line ->
                line.split(Regex("\\s+")).map { it.toInt() }
                    .let { (l, r) -> l to r }
            }
            .unzip()

    fun part1(input: Pair<List<Int>, List<Int>>): Int =
        input
            .let { (l, r) -> l.sorted().zip(r.sorted()) }
            .sumOf { (l, r) -> abs(r - l) }

    fun part2(input: Pair<List<Int>, List<Int>>): Int =
        input
            .let { (l, r) ->
                r.groupingBy { it }.eachCount()
                    .let { c -> l.map { it * (c[it] ?: 0) } }
            }
            .sum()

    // Test if implementation meets criteria from the description
    val testInput = parseInput(
        listOf("3   4", "4   3", "2   5", "1   3", "3   9", "3   3")
    )
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = parseInput(readInput("Day01"))
    part1(input).println()
    part2(input).println()
}
