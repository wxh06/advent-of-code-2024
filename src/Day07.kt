fun main() {
    fun solve(input: List<String>, check: (Long, List<Long>) -> Boolean): Long =
        input.sumOf { line ->
            line.split(": ").let { (v, nums) ->
                v.toLong() to nums.split(" ").map(String::toLong)
            }.let { (v, nums) -> if (check(v, nums)) v else 0 }
        }

    fun part1(v: Long, nums: List<Long>): Boolean =
        (0..<(1 shl (nums.size - 1))).any { s ->
            nums.reduceIndexed { i, acc, n ->
                if (s and (1 shl (i - 1)) == 0) acc + n
                else acc * n
            } == v
        }

    fun part2(v: Long, nums: List<Long>): Boolean =
        if (nums.size == 1) nums[0] == v
        else nums.let { (a, b) ->
            listOf(a + b, a * b, (a.toString() + b).toLong())
                .any { part2(v, listOf(it) + nums.drop(2)) }
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
    check(solve(testInput, ::part1) == 3749L)
    check(solve(testInput, ::part2) == 11387L)

    val input = readInput("Day07")
    solve(input, ::part1).println()
    solve(input, ::part2).println()
}
