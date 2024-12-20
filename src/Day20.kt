import kotlin.math.abs

class Day20(val input: List<String>) {
    private val time: Array<Array<Int?>> = Array(input.size) { i ->
        Array(input[i].length) { j -> if (input[i][j] == 'S') 0 else null }
    }
    private val route = mutableSetOf<Pair<Int, Int>>()

    init {
        fun surrounding(x: Int, y: Int): List<Pair<Int, Int>> = listOf(
            x - 1 to y,
            x + 1 to y,
            x to y - 1,
            x to y + 1,
        ).filter { (x, y) ->
            x in input.indices && y in input[x].indices && input[x][y] != '#'
        }

        repeat(input.sumOf { line -> line.count { it != '#' } }) {
            for (i in input.indices)
                for (j in input[i].indices)
                    if (input[i][j] != '#' && time[i][j] != null)
                        for ((nx, ny) in surrounding(i, j))
                            if (time[nx][ny] == null || time[nx][ny]!! > time[i][j]!!)
                                time[nx][ny] = time[i][j]!! + 1
        }

        val stack = mutableListOf<Pair<Int, Int>>()
        for (i in time.indices)
            for (j in time[i].indices)
                if (input[i][j] == 'E')
                    stack.add(i to j)
        while (stack.isNotEmpty()) {
            val (x, y) = stack.removeLast()
            route.add(x to y)
            for ((px, py) in surrounding(x, y))
                if (time[px][py]!! == time[x][y]!! - 1)
                    stack.add(px to py)
        }
    }

    fun cheat(lasts: Int): List<Int> = route.flatMap { (x, y) ->
        (-lasts..lasts).flatMap { dx ->
            (abs(dx) - lasts..lasts - abs(dx)).map { dy ->
                x + dx to y + dy
            }
        }.filter { (px, py) ->
            px in input.indices && py in input[px].indices && time[px][py] != null
        }.map { (px, py) ->
            time[x][y]!! - time[px][py]!! - (abs(x - px) + abs(y - py))
        }.filter { it > 0 }
    }

    fun part1(): Int = cheat(2).filter { it >= 100 }.size
    fun part2(): Int = cheat(20).filter { it >= 100 }.size
}

fun main() {
    val testInput = listOf(
        "###############",
        "#...#...#.....#",
        "#.#.#.#.#.###.#",
        "#S#...#.#.#...#",
        "#######.#.#.###",
        "#######.#.#...#",
        "#######.#.###.#",
        "###..E#...#...#",
        "###.#######.###",
        "#...###...#...#",
        "#.#####.#.###.#",
        "#.#...#.#.#...#",
        "#.#.#.#.#.#.###",
        "#...#...#...###",
        "###############",
    )
    val test = Day20(testInput)
    check(
        test.cheat(2).groupingBy { it }.eachCount() == mapOf(
            2 to 14,
            4 to 14,
            6 to 2,
            8 to 4,
            10 to 2,
            12 to 3,
            20 to 1,
            36 to 1,
            38 to 1,
            40 to 1,
            64 to 1,
        ) && test.cheat(20).filter { it >= 50 }.groupingBy { it }.eachCount() == mapOf(
            50 to 32,
            52 to 31,
            54 to 29,
            56 to 39,
            58 to 25,
            60 to 23,
            62 to 20,
            64 to 19,
            66 to 12,
            68 to 14,
            70 to 12,
            72 to 22,
            74 to 4,
            76 to 3,
        )
    )

    val day20 = Day20(readInput("Day20"))
    day20.part1().println()
    day20.part2().println()
}
