fun main() {
    fun hike(map: List<List<Int>>, position: Pair<Int, Int>, height: Int): List<Pair<Int, Int>> {
        val (x, y) = position
        if (x !in map.indices || y !in map[x].indices || map[x][y] != height)
            return emptyList()
        if (height == 9)
            return listOf(position)
        return listOf(x + 1 to y, x - 1 to y, x to y + 1, x to y - 1)
            .flatMap { hike(map, it, height + 1) }
    }

    fun solve(input: List<String>, count: (List<Pair<Int, Int>>) -> Int): Int =
        input.map { line -> line.map(Char::digitToInt) }.let { map ->
            map.indices.sumOf { i ->
                map[i].indices.sumOf { j ->
                    count(hike(map, i to j, 0))
                }
            }
        }

    fun part1(input: List<String>): Int = solve(input) { it.toSet().size }

    fun part2(input: List<String>): Int = solve(input) { it.size }

    val testInput = listOf(
        "89010123",
        "78121874",
        "87430965",
        "96549874",
        "45678903",
        "32019012",
        "01329801",
        "10456732",
    )
    check(part1(testInput) == 36)
    check(part2(testInput) == 81)

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
