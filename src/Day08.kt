import Day08.Direction.Down
import Day08.Direction.Left
import Day08.Direction.Right
import Day08.Direction.Up

fun main() {
    Day08().all()
}

class Day08 : Day {

    override fun all() = solve {
        part1(1803)
        part2(268912)
    }

    private val part1 = report {
        val indices = toIndices().toMutableList()

        indices.forEach {
            it.isVisible = it.isVisible || it.height > it.maxHeight(Up, indices, it.height - 1)
            it.isVisible = it.isVisible || it.height > it.maxHeight(Down, indices, it.height - 1)
            it.isVisible = it.isVisible || it.height > it.maxHeight(Left, indices, it.height - 1)
            it.isVisible = it.isVisible || it.height > it.maxHeight(Right, indices, it.height - 1)
        }

        indices.count { it.isVisible }
    }

    private val part2 = report {
        val indices = toIndices().toMutableList()

        indices.forEach {
            it.lineOfSight.up = it.maxLineOfSight(it.height, Up, indices)
            it.lineOfSight.down = it.maxLineOfSight(it.height, Down, indices)
            it.lineOfSight.left = it.maxLineOfSight(it.height, Left, indices)
            it.lineOfSight.right = it.maxLineOfSight(it.height, Right, indices)
        }

        indices.maxOf { it.lineOfSight.score() }
    }

    private fun <R : Any> report(block: List<List<Int>>.() -> R) = setup(block) {
        map { s -> s.map { it.digitToInt() } }.toList()
    }

    private fun List<List<Int>>.toIndices() = mapIndexed { rowIdx, row ->
        row.mapIndexed { colIdx, int ->
            Tree(colIdx to rowIdx, int)
        }
    }.flatten()

    private tailrec fun Tree.maxHeight(direction: Direction, indices: List<Tree>, testHeight: Int): Int {
        val next = when (direction) {
            Up -> indices[above()]
            Down -> indices[below()]
            Left -> indices[left()]
            Right -> indices[right()]
        }
        return if (next == null) testHeight
        else if (next.height > testHeight) next.height
        else next.maxHeight(direction, indices, testHeight)
    }

    private tailrec fun Tree.maxLineOfSight(
        testHeight: Int,
        direction: Direction,
        indices: List<Tree>,
        count: Int = 0
    ): Int {
        val next = when (direction) {
            Up -> indices[above()]
            Down -> indices[below()]
            Left -> indices[left()]
            Right -> indices[right()]
        }
        return if (next == null) count
        else if (next.height >= testHeight) count + 1
        else next.maxLineOfSight(testHeight, direction, indices, count + 1)
    }

    data class Tree(
        val xy: Pair<Int, Int> = Pair(Int.MAX_VALUE, Int.MAX_VALUE),
        val height: Int = 0,
        var isVisible: Boolean = false,
        val lineOfSight: LineOfSight = LineOfSight()
    ) {
        fun above() = xy.first to xy.second - 1
        fun below() = xy.first to xy.second + 1
        fun left() = xy.first - 1 to xy.second
        fun right() = xy.first + 1 to xy.second
    }

    enum class Direction { Up, Down, Left, Right }

    data class LineOfSight(var up: Int = 0, var down: Int = 0, var left: Int = 0, var right: Int = 0) {
        fun score() = up.toLong() * down * left * right
    }

    operator fun List<Tree>.get(xy: Pair<Int, Int>) = find { it.xy == xy }

}
