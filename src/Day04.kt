fun main() {
    Day04().all()
}

class Day04 : Day {

    override fun all() = solve {
        part1(526)
        part2(886)
    }

    private val part1 = report {
        count { (l, r) -> ((l.first <= r.first) && (l.last >= r.last)) || ((r.first <= l.first) && (r.last >= l.last)) }
    }

    private val part2 = report {
        count { (l, r) -> (l.last >= r.first) && (l.first <= r.last) }
    }

    private fun <R : Any> report(block: Sequence<Pair<IntRange, IntRange>>.() -> R) = setup(block) {
        map { it.split(",").map { range -> range.split("-").toIntRange() } }
            .map { (first, second) -> first to second }
    }

    private fun List<String>.toIntRange() = get(0).toInt()..get(1).toInt()

}
