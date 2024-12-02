fun main() {
    fun parseInput(input: List<String>): List<List<Int>> =
        input.map { line -> line.split(" ").map { it.toInt() } }

    fun isSafe(report: List<Int>): Boolean =
        report.zipWithNext().let {
            it.all { (a, b) -> b - a in 1..3 } ||
                    it.all { (a, b) -> a - b in 1..3 }
        }

    fun part1(input: List<List<Int>>): Int = input.count(::isSafe)

    fun part2(input: List<List<Int>>): Int =
        input.count { report ->
            report.indices.any { i ->
                isSafe(report.toMutableList().apply { removeAt(i) })
            }
        }

    val testInput = parseInput(
        listOf(
            "7 6 4 2 1",
            "1 2 7 8 9",
            "9 7 6 2 1",
            "1 3 2 4 5",
            "8 6 4 4 1",
            "1 3 6 7 9",
        )
    )
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day02")
    part1(parseInput(input)).println()
    part2(parseInput(input)).println()
}
