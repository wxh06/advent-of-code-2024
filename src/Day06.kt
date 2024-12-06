enum class Direction {
    UP {
        override fun turnRight() = RIGHT
    },
    RIGHT {
        override fun turnRight() = DOWN
    },
    DOWN {
        override fun turnRight() = LEFT
    },
    LEFT {
        override fun turnRight() = UP
    };

    abstract fun turnRight(): Direction
}

data class Position(val x: Int, val y: Int) {
    fun forward(direction: Direction) = Position(
        x + when (direction) {
            Direction.UP -> -1
            Direction.DOWN -> 1
            else -> 0
        }, y + when (direction) {
            Direction.LEFT -> -1
            Direction.RIGHT -> 1
            else -> 0
        }
    )
}

fun main() {
    class StuckInALoop(
        val position: Position, val direction: Direction
    ) : Error()

    fun findGuard(input: List<String>): Position {
        for (i in input.indices)
            for (j in input[i].indices)
                if (input[i][j] == '^')
                    return Position(i, j)
        throw IllegalArgumentException("Guard not found")
    }

    fun part1(input: List<String>, obstruction: Position? = null): Int {
        var position: Position = findGuard(input)
        var direction: Direction = Direction.UP

        val visited = mutableSetOf<Position>()
        val visitedDirection = mutableSetOf<Pair<Position, Direction>>()
        while (true) {
            visited.add(position)
            if (position to direction in visitedDirection)
                throw StuckInALoop(position, direction)
            visitedDirection.add(position to direction)

            val next = position.forward(direction)
            val (x, y) = next
            if (x !in input.indices || y !in input[0].indices)
                break
            if (input[x][y] == '#' || next == obstruction)
                direction = direction.turnRight()
            else
                position = next
        }
        return visited.size
    }

    fun part2(input: List<String>): Int {
        var c = 0
        for (i in input.indices)
            for (j in input[i].indices)
                if (input[i][j] == '.')
                    try {
                        part1(input, Position(i, j))
                    } catch (e: StuckInALoop) {
                        c++
                    }
        return c
    }

    val testInput = listOf(
        "....#.....",
        ".........#",
        "..........",
        "..#.......",
        ".......#..",
        "..........",
        ".#..^.....",
        "........#.",
        "#.........",
        "......#..."
    )
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
