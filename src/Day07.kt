fun main() {
    fun generateOperatorSequences(
        n: Int, operations: List<String>
    ): List<List<String>> {
        if (n == 0) return listOf(emptyList())
        return operations.flatMap { op ->
            generateOperatorSequences(n - 1, operations).map {
                it.toMutableList().apply { plusAssign(op) }
            }
        }
    }

    fun solve(input: List<String>, check: (Long, List<Long>) -> Boolean): Long =
        input.sumOf { line ->
            line.split(": ").let { (v, nums) ->
                v.toLong() to nums.split(" ").map(String::toLong)
            }.let { (v, nums) -> if (check(v, nums)) v else 0 }
        }

    fun part1(input: List<String>): Long =
        solve(input) { v, nums ->
            (0..<(1 shl (nums.size - 1))).any { s ->
                nums.reduceIndexed { i, acc, n ->
                    if (s and (1 shl (i - 1)) == 0) acc + n
                    else acc * n
                } == v
            }
        }

    fun part2(input: List<String>): Long =
        solve(input) { v, nums ->
            generateOperatorSequences(
                nums.size - 1, listOf("+", "*", "||")
            ).any { s ->
                nums.reduceIndexed { i, acc, n ->
                    when (s[i - 1]) {
                        "+" -> acc + n
                        "*" -> acc * n
                        "||" -> (acc.toString() + n).toLong()
                        else -> error("Unknown operation")
                    }
                } == v
            }
        }

    val testInput = listOf(
        "190: 10 19",
        "3267: 81 40 27",
        "83: 17 5",
        "156: 15 6",
        "7290: 6 8 6 15",
        "161011: 16 10 13",
        "192: 17 8 14",
        "21037: 9 7 18 13",
        "292: 11 6 16 20",
    )
    check(part1(testInput) == 3749L)
    check(part2(testInput) == 11387L)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
