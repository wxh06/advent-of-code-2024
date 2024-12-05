fun main() {
    fun solve(input: List<String>): Pair<Int, Int> {
        val divider = input.indexOf("")
        val rules = input.take(divider)
            .map { it.split("|").map(String::toInt).let { (a, b) -> a to b } }
        val updates = input.drop(divider + 1)
            .map { it.split(",").map(String::toInt) }

        var part1 = 0
        var part2 = 0
        for (update in updates.map(List<Int>::toMutableList)) {
            var correct = true
            for (i in update.indices)
                for (j in update.indices.drop(i + 1))
                    if ((update[j] to update[i]) in rules) {
                        correct = false
                        update.add(i, update[j])
                        update.removeAt(j + 1)
                    }
            val mid = update[update.size / 2]
            if (correct) part1 += mid else part2 += mid
        }
        return part1 to part2
    }

    val testInput = listOf(
        "47|53",
        "97|13",
        "97|61",
        "97|47",
        "75|29",
        "61|13",
        "75|53",
        "29|13",
        "97|29",
        "53|29",
        "61|53",
        "97|53",
        "61|29",
        "47|13",
        "75|47",
        "97|75",
        "47|61",
        "75|61",
        "47|29",
        "75|13",
        "53|13",
        "",
        "75,47,61,53,29",
        "97,61,53,29,13",
        "75,29,13",
        "75,97,47,61,53",
        "61,13,29",
        "97,13,75,29,47"
    )
    check(solve(testInput) == 143 to 123)

    val input = readInput("Day05")
    solve(input).toList().forEach(::println)
}
