fun main() {
    Day03().all()
}

class Day03 : Day {

    override fun all() = solve {
        part1(7737)
        part2(2697)
    }

    private val part1 = report {
        map { it.run { substring(0, length / 2).toSet().intersect(it.substring(length / 2).toSet()) } }
            .sumOf { it.first().decode() }
    }

    private val part2 = report {
        chunked(3)
            .map { (a, b, c) -> a.toSet().intersect(b.toSet()).intersect(c.toSet()) }
            .sumOf { it.first().decode() }
    }

    private fun <R : Any> report(block: Sequence<String>.() -> R) = setup(block) {
        this
    }

    private fun Char.decode() = if (isUpperCase()) code - 38 else code - 96

}
