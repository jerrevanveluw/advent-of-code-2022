fun main() {
    Day21().all()
}

class Day21 : Day {

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
