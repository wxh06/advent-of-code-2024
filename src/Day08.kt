typealias Location = Pair<Int, Int>

fun main() {
    fun solve(input: List<String>, antinodes: (Location, Location) -> Iterable<Location>): Int =
        input.flatMapIndexed { i, line ->
            line.mapIndexedNotNull { j, c ->
                if (c != '.') i to j to c else null
            }
        }.groupBy({ it.second }, { it.first }).values.flatMap { antennas ->
            antennas.flatMapIndexed { i, a1 ->
                antennas.drop(i + 1).flatMap { a2 ->
                    antinodes(a1, a2)
                }
            }
        }.toSet().size

    fun part1(input: List<String>): Int = solve(input) { (x1, y1), (x2, y2) ->
        val dx = x1 - x2
        val dy = y1 - y2
        val antinodes = mutableSetOf<Location>()
        if (x1 + dx in input.indices && y1 + dy in input[x1 + dx].indices)
            antinodes.add(x1 + dx to y1 + dy)
        if (x2 - dx in input.indices && y2 - dy in input[x2 - dx].indices)
            antinodes.add(x2 - dx to y2 - dy)
        antinodes
    }

    fun part2(input: List<String>): Int = solve(input) { (x1, y1), (x2, y2) ->
        val dx = x1 - x2
        val dy = y1 - y2
        val antinodes = mutableSetOf<Location>()
        var k = 0
        while (x1 + k * dx in input.indices && y1 + k * dy in input[0].indices) {
            antinodes.add(x1 + k * dx to y1 + k * dy)
            k++
        }
        k = 0
        while (x2 - k * dx in input.indices && y2 - k * dy in input[0].indices) {
            antinodes.add(x2 - k * dx to y2 - k * dy)
            k++
        }
        antinodes
    }

    val testInput = listOf(
        "............",
        "........0...",
        ".....0......",
        ".......0....",
        "....0.......",
        "......A.....",
        "............",
        "............",
        "........A...",
        ".........A..",
        "............",
        "............"
    )
    check(part1(testInput) == 14)
    check(part2(testInput) == 34)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
