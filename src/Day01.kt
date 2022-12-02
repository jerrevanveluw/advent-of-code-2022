fun main() {
    Day01().all()
}

class Day01 : Day {

    override fun all() = solve {
        part1(67016)
        part2(200116)
    }

    private val part1 = report {
        max()
    }

    private val part2 = report {
        sortedDescending().take(3).sum()
    }

    private fun <R : Any> report(block: Sequence<Int>.() -> R) = setup(block) {
        fold(mutableListOf(0)) { acc, cur ->
            if (cur == "") acc.apply { add(0) }
            else acc.apply {
                val last = acc.size - 1
                this[last] = get(last) + cur.toInt()
            }
        }.asSequence()
    }

}
