fun main() {
    fun part1(input: List<String>): Int {
        val word = "XMAS"
        val steps = setOf((0 to 1), (1 to 0), (1 to 1), (-1 to 0), (0 to -1), (-1 to -1), (1 to -1), (-1 to 1))
        val xIndices = input.indices
        val yIndices = input[0].indices
        return xIndices.sumOf { i ->
            yIndices.sumOf { j ->
                steps.count { (x, y) ->
                    word.withIndex().all { (k, ch) ->
                        i + k * x in xIndices && j + k * y in yIndices &&
                                input[i + k * x][j + k * y] == ch
                    }
                }
            }
        }
    }

    fun part2(input: List<String>): Int {
        val rules = setOf(
            setOf((0 to 0 to 'M'), (2 to 0 to 'M'), (1 to 1 to 'A'), (0 to 2 to 'S'), (2 to 2 to 'S')),
            setOf((0 to 0 to 'M'), (2 to 0 to 'S'), (1 to 1 to 'A'), (0 to 2 to 'M'), (2 to 2 to 'S')),
            setOf((0 to 0 to 'S'), (2 to 0 to 'S'), (1 to 1 to 'A'), (0 to 2 to 'M'), (2 to 2 to 'M')),
            setOf((0 to 0 to 'S'), (2 to 0 to 'M'), (1 to 1 to 'A'), (0 to 2 to 'S'), (2 to 2 to 'M')),
        )
        return input.indices.toList().dropLast(2).sumOf { i ->
            input[i].indices.toList().dropLast(2).count { j ->
                rules.any {
                    it.all { (offset, ch) ->
                        input[i + offset.first][j + offset.second] == ch
                    }
                }
            }
        }
    }

    val testInput = listOf(
        "MMMSXXMASM",
        "MSAMXMSMSA",
        "AMXSXMAAMM",
        "MSAMASMSMX",
        "XMASAMXAMM",
        "XXAMMXXAMA",
        "SMSMSASXSS",
        "SAXAMASAAA",
        "MAMMMXMMMM",
        "MXMXAXMASX"
    )
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
