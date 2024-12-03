fun main() {
    fun part1(input: List<String>): Int =
        input.flatMap { line ->
            Regex("mul\\((\\d+),(\\d+)\\)").findAll(line)
                .map { it.groupValues }
                .map { (_, a, b) -> a.toInt() * b.toInt() }
        }.sum()

    fun part2(input: List<String>): Int {
        var enabled = true
        return input.flatMap { line ->
            Regex("mul\\((\\d+),(\\d+)\\)|(do|don't)\\(\\)").findAll(line)
                .map { it.groupValues }
                .map { (_, a, b, i) ->
                    if (i == "" && enabled)
                        a.toInt() * b.toInt()
                    else {
                        enabled = when (i) {
                            "do" -> true
                            "don't" -> false
                            else -> enabled
                        }
                        0
                    }
                }
        }.sum()
    }

    check(part1(listOf("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")) == 161)
    check(part2(listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")) == 48)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
