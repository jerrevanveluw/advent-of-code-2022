fun main() {
    Day06().all()
}

class Day06 : Day {

    override fun all() = solve {
        part1(1100)
        part2(2421)
    }

    private val part1 = report(4L) { this }

    private val part2 = report(14L) { this }

    private fun <R : Any> report(startOfMarker: Long, block: Long.() -> R) = setup(block) {
        var count = startOfMarker
        first().windowed(startOfMarker.toInt())
            .firstNotNullOf {
                if (it.chars().distinct().count() == startOfMarker) count
                else null.also { count++ }
            }
    }

}
