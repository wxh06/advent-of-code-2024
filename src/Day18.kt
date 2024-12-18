fun main() {
    fun part1(input: List<String>, n: Int): Int? {
        val corrupted = input.map { it.split(",") }.map { (x, y) -> x.toInt() to y.toInt() }.toSet()
        val steps: Array<Array<Int?>> = Array(n + 1) { Array(n + 1) { null } }
        steps[0][0] = 0
        repeat(n * n) {
            for (x in 0..n) {
                for (y in 0..n) {
                    if (x to y !in corrupted)
                        listOf(
                            (x - 1 to y),
                            (x + 1 to y),
                            (x to y - 1),
                            (x to y + 1),
                        ).filter { (nx, ny) -> nx in 0..n && ny in 0..n && x to y !in corrupted }
                            .mapNotNull { (nx, ny) -> steps[nx][ny] }.minOrNull()?.let { it + 1 }
                            ?.let { if (steps[x][y] == null || it < steps[x][y]!!) steps[x][y] = it }
                }
            }
        }
        return steps[n][n]
    }

    fun part2(input: List<String>, n: Int): String =
        input[-1 - input.indices.toList().binarySearch { i ->
            if (part1(input.take(i + 1), n) == null) 1 else -1
        }]

    val testInput = listOf(
        "5,4",
        "4,2",
        "4,5",
        "3,0",
        "2,1",
        "6,3",
        "2,4",
        "1,5",
        "0,6",
        "3,3",
        "2,6",
        "5,1",
        "1,2",
        "5,5",
        "2,5",
        "6,5",
        "1,4",
        "0,4",
        "6,4",
        "1,1",
        "6,1",
        "1,0",
        "0,5",
        "1,6",
        "2,0",
    )
    check(part1(testInput.take(12), 6) == 22)
    check(part2(testInput, 6) == "6,1")

    val input = readInput("Day18")
    part1(input.take(1024), 70).println()
    part2(input, 70).println()
}
