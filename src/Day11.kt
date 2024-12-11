fun main() {
    val cache = mutableMapOf<Pair<Long, Int>, Long>()

    fun count(input: List<Long>, blinks: Int): Long =
        if (blinks == 0) input.size.toLong()
        else input.sumOf { stone ->
            cache.getOrPut(stone to blinks) {
                val s = stone.toString()
                val l = s.length
                count(
                    when {
                        stone == 0L -> listOf(1)
                        l % 2 == 0 -> listOf(s.take(l / 2), s.drop(l / 2)).map(String::toLong)
                        else -> listOf(stone * 2024)
                    }, blinks - 1
                )
            }
        }

    check(count(listOf(125, 17), 25) == 55312L)

    val input = readInput("Day11").first().split(" ").map(String::toLong)
    count(input, 25).println()
    count(input, 75).println()
}
