import Day09.Direction.Down
import Day09.Direction.Left
import Day09.Direction.Right
import Day09.Direction.Up

fun main() {
    Day09().all()
}

class Day09 : Day {

    override fun all() = solve {
        part1(6087)
        part2(null)
    }

    private val part1 = report {
        val headCoordinates = mutableListOf(Coordinate(0, 0))
        val tailCoordinates = headCoordinates.toMutableList()

        forEach { headCoordinates.last().move(it, headCoordinates) }

        headCoordinates
            .zipWithNext()
            .map { it.move(tailCoordinates) }

        tailCoordinates.distinct().size
    }

    private val part2 = report { }

    private fun <R : Any> report(block: Sequence<Instruction>.() -> R) = setup(block) {
        map { it.split(" ") }
            .map { (d, s) -> Instruction(d.parse(), s.toInt()) }
    }

    enum class Direction { Up, Down, Left, Right }

    private fun String.parse() = when (this) {
        "U" -> Up
        "D" -> Down
        "L" -> Left
        "R" -> Right
        else -> throw RuntimeException()
    }

    data class Instruction(val direction: Direction, val steps: Int)

    data class Coordinate(val x: Int, val y: Int) {
        override fun toString() = "($x, ${if (y >= 0) " $y" else y})"
    }

    private fun Coordinate.closeBy(other: Coordinate): Boolean {
        val xDiff = if (x > other.x) x - other.x else other.x - x
        val yDiff = if (y > other.y) y - other.y else other.y - y
        return xDiff <= 1 && yDiff <= 1
    }

    private tailrec fun Coordinate.move(instruction: Instruction, coords: MutableList<Coordinate>): List<Coordinate> =
        if (instruction.steps == 0) coords
        else {
            val coord = when (instruction.direction) {
                Up -> copy(y = y - 1)
                Down -> copy(y = y + 1)
                Left -> copy(x = x - 1)
                Right -> copy(x = x + 1)
            }
            coords.add(coord)
            coord.move(instruction.copy(steps = instruction.steps - 1), coords)
        }

    private fun Pair<Coordinate, Coordinate>.move(coords: MutableList<Coordinate>) {
        val tail = coords.last()
        coords.add(if (tail.closeBy(second)) tail else first)
    }

}
