fun main() {
    Day05().all()
}

class Day05 : Day {

    override fun all() = solve {
        part1("RTGWZTHLD")
        part2("STHGRZZFR")
    }

    private val part1 = report {
        let { (stacks, input) ->
            input.forEach { move ->
                repeat(move.crates) { _ ->
                    stacks[move.to]!!.add(stacks[move.from]!!.removeLast())
                }
            }
            stacks.map { (_, v) -> v.removeLast() }
        }.joinToString("")
    }

    private val part2 = report {
        let { (stacks, input) ->
            input.forEach { move ->
                stacks[move.to]!!.addAll(stacks[move.from]!!.takeLast(move.crates))
                repeat(move.crates) { _ ->
                    stacks[move.from]!!.removeLast()
                }
            }
            stacks.map { it.value.removeLast() }
        }.joinToString("")
    }

    private fun <R : Any> report(block: Pair<Map<Int, ArrayDeque<Char>>, List<Move>>.() -> R) = setup(block) {
        mutableMapOf<Int, ArrayDeque<Char>>().let { stacks ->
            take(10)
                .toList()
                .reversed()
                .asSequence()
                .drop(2)
                .map { it.replace("^ {3}".toRegex(), "[ ]") }
                .map { it.replace("     ", " [ ] ") }
                .map { it.replace("     ", " [ ] ") }
                .map { it.replace("    ", " [ ]") }
                .map { it.split("] [").joinToString("],[") }
                .map { it.replace("[", "") }
                .map { it.replace("]", "") }
                .map { it.split(",") }
                .apply { repeat(first().size) { stacks[it + 1] = ArrayDeque(listOf()) } }
                .forEach { line ->
                    line.forEachIndexed { idx, crate ->
                        if (crate.first() != ' ') stacks[idx + 1]!!.add(crate.first())
                    }
                }
            stacks
        } to drop(10)
            .map { it.replace("move ", "") }
            .map { it.replace(" from ", ",") }
            .map { it.replace(" to ", ",") }
            .map { it.split(",").let { (a, b, c) -> Move(a.toInt(), b.toInt(), c.toInt()) } }
            .toList()
    }

    data class Move(val crates: Int, val from: Int, val to: Int)

}
