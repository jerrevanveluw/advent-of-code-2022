fun main() {
    Day09().all()
}

class Day09 : Day {

    override fun all() = solve {
        part1(null)
        part2(null)
    }

    private val part1 = report { }

    private val part2 = report { }

    private fun <R : Any> report(block: Sequence<String>.() -> R) = setup(block) {
        this
    }

}