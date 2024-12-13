/** Equivalent to C++'s `std::unique` in `algorithm` */
fun <T> List<T>.unique() = this.fold(listOf<T>()) { acc, element ->
    if (acc.isEmpty() || acc.last() != element)
        acc + element
    else
        acc
}

fun main() {
    data class Region(var area: Int, var perimeter: Int) {
        operator fun plusAssign(region: Region) {
            area += region.area
            perimeter += region.perimeter
        }
    }

    fun search(
        map: List<String>,
        plot: Pair<Int, Int>,
        unvisited: MutableSet<Pair<Int, Int>>,
        visited: MutableSet<Pair<Int, Int>>,
    ): Region {
        visited += plot
        unvisited -= plot

        val (x, y) = plot
        val region = Region(1, 0)
        for ((dx, dy) in listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0)) {
            val next = x + dx to y + dy
            val (nx, ny) = next
            if (next !in visited) {
                region.perimeter++
                if (nx in map.indices && ny in map[nx].indices && map[nx][ny] == map[x][y]) {
                    region += search(map, next, unvisited, visited)
                    region.perimeter--
                }
            }
        }
        return region
    }

    fun solve(input: List<String>): Pair<Int, Int> {
        val unvisited = input.indices.flatMap { y -> input[0].indices.map { x -> x to y } }.toMutableSet()
        var price1 = 0
        var price2 = 0
        while (unvisited.isNotEmpty()) {
            val visited = mutableSetOf<Pair<Int, Int>>()
            val region = search(input, unvisited.first(), unvisited, visited)
            price1 += region.area * region.perimeter

            // dirty code to calculate number of sides (and perimeter)
            var sides = 0
            val xRange = visited.minOf { it.first }..visited.maxOf { it.first }
            val yRange = visited.minOf { it.second }..visited.maxOf { it.second }
            for (x in xRange) {
                sides += yRange.map { y ->
                    (x to y) in visited && (x - 1 to y) !in visited
                }.unique().count { it }
                sides += yRange.map { y ->
                    (x to y) in visited && (x + 1 to y) !in visited
                }.unique().count { it }
            }
            for (y in yRange) {
                sides += xRange.map { x ->
                    (x to y) in visited && (x to y - 1) !in visited
                }.unique().count { it }
                sides += xRange.map { x ->
                    (x to y) in visited && (x to y + 1) !in visited
                }.unique().count { it }
            }
            price2 += region.area * sides
        }
        return price1 to price2
    }

    val testInput1 = listOf(
        "AAAA",
        "BBCD",
        "BBCC",
        "EEEC",
    )
    val testInput2 = listOf(
        "OOOOO",
        "OXOXO",
        "OOOOO",
        "OXOXO",
        "OOOOO",
    )
    val testInput3 = listOf(
        "RRRRIICCFF",
        "RRRRIICCCF",
        "VVRRRCCFFF",
        "VVRCCCJFFF",
        "VVVVCJJCFE",
        "VVIVCCJJEE",
        "VVIIICJJEE",
        "MIIIIIJJEE",
        "MIIISIJEEE",
        "MMMISSJEEE",
    )
    val testInput4 = listOf(
        "EEEEE",
        "EXXXX",
        "EEEEE",
        "EXXXX",
        "EEEEE",
    )
    val testInput5 = listOf(
        "AAAAAA",
        "AAABBA",
        "AAABBA",
        "ABBAAA",
        "ABBAAA",
        "AAAAAA",
    )
    check(solve(testInput1) == 140 to 80)
    check(solve(testInput2) == 772 to 436)
    check(solve(testInput3) == 1930 to 1206)
    check(solve(testInput4).second == 236)
    check(solve(testInput5).second == 368)

    val input = readInput("Day12")
    solve(input).toList().forEach(::println)
}
