fun main() {
    Day13().all()
}

class Day13 : Day {

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
